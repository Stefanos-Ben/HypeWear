package com.stephben.hypewear.apparel.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.home_screen.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.search.components.FilterOptions
import com.stephben.hypewear.apparel.presentation.search.components.SearchAndFilterBar
import com.stephben.hypewear.core.presentation.components.ApparelListLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onApparelClick: (Apparel) -> Unit,
    bottomBar: @Composable () -> Unit,
    initialCategory: String = "",
    modifier: Modifier,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(initialCategory) {
        if (initialCategory.isNotBlank()) {
            val preset = FilterOptions(category = initialCategory)
            viewModel.onAction(SearchAction.OnFiltersApplied(preset))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onAction(SearchAction.OnLoadFavorites)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 48.dp, top = 32.dp)
        ) {
            SearchAndFilterBar(
                onSearchQueryChange = {
                    viewModel.onAction(SearchAction.OnSearchQueryChange(it))
                },
                searchQuery = state.searchQuery,
                onImeSearch = {
                    keyboardController?.hide()
                },
                currentFilters = state.filterOptions,
                onFiltersApplied = { newFilters ->
                    viewModel.onAction(SearchAction.OnFiltersApplied(newFilters))
                },
                onFiltersCanceled = {
                    viewModel.onAction(SearchAction.OnFiltersCanceled)
                },
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
            )


            if (state.isLoading) {
                ApparelListLoading()
            } else if(state.results.isEmpty() && state.searchQuery.isBlank() && state.filterOptions.activeCount == 0) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.search_motiv),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            } else if (state.results.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.search_fail),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 64.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.results) { apparel ->
                        val isFavorite = state.favorites.contains(apparel.apparelID)
                        ApparelItem(
                            apparel = apparel,
                            onItemClick = { onApparelClick(apparel) },
                            onFavoriteClick = {
                                viewModel.onAction(
                                    SearchAction.OnToggleFavorites(apparel.apparelID, isFavorite)
                                )
                            },
                            isFavorite = isFavorite
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomStart)) {
            bottomBar()
        }
    }

}