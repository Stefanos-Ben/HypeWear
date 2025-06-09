package com.stephben.hypewear.apparel.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.apparel.domain.ApparelFilters

@Stable
data class FilterOptions(
    val brand: String = "",
    val category: String = "",
    val sex: String = "",
    val minPrice: Float? = null,
    val maxPrice: Float? = null,

    val minEcoScore: Int? = null,
    val maxEcoScore: Int? = null
) {
    val activeCount: Int
        get() {
            var count = 0
            if (brand.isNotBlank()) count++
            if (category.isNotBlank()) count++
            if (sex.isNotBlank()) count++
            // Treat the entire price range as one “price” filter
            if (minPrice != null || maxPrice != null) count++
            // Treat the entire ecoScore range as one “ecoScore” filter
            if (minEcoScore != null || maxEcoScore != null) count++
            return count
        }

    fun toDomain(): ApparelFilters {
        return ApparelFilters(
            brand = brand.ifBlank { null },
            category = category.ifBlank { null },
            sex = sex.ifBlank { null },
            minPrice = minPrice?.toDouble(),
            maxPrice = maxPrice?.toDouble(),
            minEcoScore = minEcoScore,
            maxEcoScore = maxEcoScore
        )
    }
}

@Composable
fun SearchAndFilterBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    currentFilters: FilterOptions = FilterOptions(),
    onFiltersApplied: (FilterOptions) -> Unit = {},
    onFiltersCanceled: () -> Unit = {},
) {
    var showFilterDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApparelSearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onImeSearch = onImeSearch,
            modifier = Modifier
                .weight(1f)
                .background(
                    shape = RoundedCornerShape(100),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .minimumInteractiveComponentSize()
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilterButtonWithBadge(
            activeCount = currentFilters.activeCount,
            onClick = { showFilterDialog = true }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            currentFilters = currentFilters,
            onApply = { newFilters ->
                showFilterDialog = false
                onFiltersApplied(newFilters)
            },
            onDismiss = {
                showFilterDialog = false
                onFiltersCanceled()
            }
        )
    }
}