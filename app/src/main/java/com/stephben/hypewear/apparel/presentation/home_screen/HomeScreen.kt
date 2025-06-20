package com.stephben.hypewear.apparel.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.home_screen.components.ApparelHomeHeader
import com.stephben.hypewear.apparel.presentation.home_screen.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.home_screen.components.CategoryChip
import com.stephben.hypewear.apparel.presentation.home_screen.components.SustainableOfTheDay
import com.stephben.hypewear.core.domain.utils.ApparelChoices
import com.stephben.hypewear.core.presentation.components.ApparelListLoading
import org.koin.androidx.compose.koinViewModel


@Composable
fun ApparelListScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onApparelClick: (Apparel) -> Unit,
    onCategoryClick: (String) -> Unit,
    onOrdersClick: () -> Unit,
    bottomBar: @Composable () -> Unit,
    modifier: Modifier,
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value


    LaunchedEffect(Unit) {
        viewModel.onAction(HomeScreenAction.GetNewApparels)
        viewModel.onAction(HomeScreenAction.OnLoadSustainable)
        viewModel.onAction(HomeScreenAction.OnLoadFavorites)
        viewModel.onAction(HomeScreenAction.OnLoadCart)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)

    ){
        ApparelHomeHeader(
            modifier = Modifier.padding(top = 32.dp),
            onOrdersClick = onOrdersClick
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp, top = 80.dp),
            contentPadding = PaddingValues(top = 70.dp)
        ) {


            if (state.isLoading) {
                item {
                    ApparelListLoading()
                }

            } else {
                item {
                    Text(
                        "Browse Categories",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(ApparelChoices.categories) { category ->
                            CategoryChip(
                                category = category,
                                onCategoryClick = { onCategoryClick(category) }
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                }


                item {
                    Text(
                        "Sustainable Of The Day",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp)
                    )


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SustainableOfTheDay(apparel = state.sustainableOfTheDay)
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                }

                item {
                    Text(
                        "New Items",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    LazyVerticalGrid (
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        items(state.newItems) { apparel ->
                            val isFavorite = state.favorites.contains(apparel.apparelID)
                            val inCart = state.cart.find { it.apparelId == apparel.apparelID } != null
                            ApparelItem(
                                apparel = apparel,
                                onItemClick = { onApparelClick(apparel) },
                                onFavoriteClick = {
                                    viewModel.onAction(
                                        HomeScreenAction.OnToggleFavorites(
                                            apparel.apparelID,
                                            isFavorite
                                        )
                                    )
                                },
                                isFavorite = isFavorite,
                                onCartClick = {
                                    viewModel.onAction(
                                        HomeScreenAction.OnToggleCart(
                                            apparel.apparelID,
                                            inCart = inCart,
                                            size = apparel.stockPerSize.keys.first().toString()
                                        )
                                    )
                                },
                                inCart = inCart
                            )
                        }
                    }
                }
            }

        }

        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }

    }


}