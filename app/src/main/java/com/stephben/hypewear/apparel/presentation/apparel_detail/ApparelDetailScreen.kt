package com.stephben.hypewear.apparel.presentation.apparel_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelDetailBottomBar
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelDetailHeader
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelPortrait
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApparelDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ApparelDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCartClick: () -> Unit

) {
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val formattedPrice = "%.${2}f".format(state.apparel?.price)

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
        ) {
            ApparelDetailHeader(
                modifier = Modifier
                    .padding(top = 24.dp),
                onBackClick = onBackClick,
                onFavoriteClick = onFavoriteClick
            )

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp)
            ) {

                item {
                    ApparelPortrait(
                        imageUrl = state.apparel?.imageUrl,
                        onClick = {}
                    )
                }

                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.apparel?.brand?.name ?: "Unknown",
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            fontSize = 27.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = state.apparel?.description ?: "No description for this apparel.",
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }
        }
        ApparelDetailBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            price = "$formattedPrice ${state.apparel?.currency}",
            onCartClick = onCartClick

        )
    }


}


@Preview
@Composable
private fun ApparelDetailScreenPrev() {
    ApparelDetailScreen(
        onFavoriteClick = {},
        onBackClick = {},
        onCartClick = {}
    )
}