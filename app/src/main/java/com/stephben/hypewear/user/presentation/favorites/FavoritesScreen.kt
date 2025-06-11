package com.stephben.hypewear.user.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.home_screen.components.ApparelItem
import com.stephben.hypewear.user.presentation.favorites.components.FavoritesHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    onApparelClick: (Apparel) -> Unit,
    bottomBar: @Composable () -> Unit,
    modifier: Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.onAction(FavoritesAction.OnLoadFavorites)
        viewModel.onAction(FavoritesAction.OnLoadCart)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)
    ){

        Column(
            modifier = Modifier.fillMaxSize(

            )
        ) {
            FavoritesHeader(
                modifier = Modifier.padding(top = 40.dp)
            )

            if (state.favoriteApparels.isNotEmpty() && !state.isLoading)  {
                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    items(state.favoriteApparels) { apparel ->
                        val isFavorite = state.favoriteIDs.contains(apparel.apparelID)
                        val inCart = state.cart.find { it.apparelId == apparel.apparelID } != null
                        ApparelItem(
                            apparel = apparel,
                            onItemClick = { onApparelClick(apparel) },
                            isFavorite = isFavorite,
                            onFavoriteClick = {
                                viewModel.onAction(
                                    FavoritesAction.OnToggleFavorites(
                                        id = apparel.apparelID,
                                        isFavorite = isFavorite
                                    )
                                )
                            },
                            onCartClick = {
                                viewModel.onAction(
                                    FavoritesAction.OnToggleCart(
                                        apparel.apparelID,
                                        inCart = inCart,
                                        size = apparel.stockPerSize.keys.first().toString()
                                    )
                                )
                            },
                            inCart = inCart,
                        )
                    }
                }
            } else if(!state.isLoading){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {



                    Image(
                        painter = painterResource(R.drawable.no_favorites),
                        contentDescription = stringResource(R.string.no_favorites),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = stringResource(R.string.no_favorites),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }

            }
            else {
                CircularProgressIndicator()
            }
        }


        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }
    }
}