package com.stephben.hypewear.order.presentation.order_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.auth.domain.AuthRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.order.domain.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderListViewModel(
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
    private val apparelRepository: ApparelRepository
): ViewModel() {
    private val tag = "ORDER_LIST_VM"

    private val _state: MutableStateFlow<OrderListState> = MutableStateFlow(OrderListState())
    val state: StateFlow<OrderListState> = _state.asStateFlow()

    fun onAction(action: OrderListAction) = when (action) {
        OrderListAction.OnLoadOrders -> {
            loadOrders()
        }
    }

    private fun loadOrders() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val currentUserId = authRepository.currentUserId()

        when (val result = currentUserId?.let { orderRepository.getUserOrders(it) }) {
            is Result.Success -> {
                _state.update { it.copy(orders = result.data) }
            }

            is Result.Failure -> {
                Log.e(tag, "Failed to load your orders: ", result.exception)
            }

            null -> Log.e(tag, "Something went wrong")
        }

        if (_state.value.orders.isNotEmpty()) _state.value.orders.forEach { order ->
            val apparelIds = order.items.map { it.apparelId }
            when (val result = apparelRepository.getFavoriteApparels(apparelIds)) {
                is Result.Success -> _state.update {
                    it.copy(orderApparels = it.orderApparels.plus(Pair(order.orderId, result.data)))
                }
                is Result.Failure -> {
                    Log.e(tag, "Failed to retrieve order apparels")
                }

            }
        }

        _state.update { it.copy(isLoading = false) }
    }
}