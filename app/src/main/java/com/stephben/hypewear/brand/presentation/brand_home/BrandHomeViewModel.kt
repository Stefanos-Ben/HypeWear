package com.stephben.hypewear.brand.presentation.brand_home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephben.hypewear.apparel.domain.ApparelRepository
import com.stephben.hypewear.brand.domain.BrandRepository
import com.stephben.hypewear.core.domain.utils.Result
import com.stephben.hypewear.order.domain.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandHomeViewModel(
    private val brandRepository: BrandRepository,
    private val orderRepository: OrderRepository,
    private val apparelRepository: ApparelRepository
): ViewModel() {
    private val tag = "BRAND HOME VM"

    private val _state: MutableStateFlow<BrandHomeState> = MutableStateFlow(BrandHomeState())
    val state: StateFlow<BrandHomeState> = _state.asStateFlow()

    fun onAction(action: BrandHomeAction){
        when(action){
            BrandHomeAction.OnLoadBrand -> {
                loadBrandAndStats()
            }
        }
    }

    private fun loadBrandAndStats() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val brandRes = brandRepository.getCurrentBrand()
            if (brandRes is Result.Failure) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = brandRes.exception.message
                    )
                }
                return@launch
            }
            val brand = (brandRes as Result.Success).data
            _state.update { it.copy(brand = brand) }
            val ordersRes = when (val o = orderRepository.getBrandOrders(brand.id)) {
                is Result.Success -> o
                is Result.Failure -> {
                    Log.e(tag, "Failed to fetch brand orders", o.exception)
                    Result.Success(emptyList())
                }
            }



            val orders = ordersRes.data
            var revenue = 0.0
            var items = 0
            val countByApparel = mutableMapOf<String, Int>()


            orders.forEach { order ->
                revenue += order.total
                order.items.forEach { item ->
                    items += item.quantity
                    if (item.brandId == brand.id) {
                        countByApparel[item.apparelId] = (countByApparel[item.apparelId] ?: 0) + item.quantity
                    }
                }
            }

            val topSelling = countByApparel.maxByOrNull { it.value }?.key ?: "—"
            Log.i("TOP SELLING" ,countByApparel.toString())

            _state.update {
                it.copy(
                    isLoading = false,
                    brand = brandRes.data,
                    totalRevenue = revenue,
                    totalOrders = orders.size,
                    totalItemsSold = items,
                    topSellingApparelId = topSelling
                )
            }

            if (topSelling.isNotBlank()) {
                when (val aRes = apparelRepository.getApparel(topSelling)) {
                    is Result.Success -> {
                        _state.update { it.copy(topSellingApparel = aRes.data) }
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Error finding top selling apparel: ", aRes.exception)
                    }
                }
            }
        }
        _state.update { it.copy(isLoading = false) }
    }
}