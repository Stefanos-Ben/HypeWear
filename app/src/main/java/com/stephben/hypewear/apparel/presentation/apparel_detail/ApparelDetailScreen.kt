package com.stephben.hypewear.apparel.presentation.apparel_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelDetailBottomBar
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelDetailHeader
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ApparelSpec
import com.stephben.hypewear.apparel.presentation.home_screen.components.ApparelPortrait
import com.stephben.hypewear.core.presentation.ui.theme.components.ProfileCircle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApparelDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ApparelDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCartClick: () -> Unit,
) {
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val formattedPrice = "%.${2}f".format(state.apparel?.price)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
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

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 100.dp)
            ) {

                item {
                    ApparelPortrait(
                        imageUrl = state.apparel?.imageUrl,
                        onClick = {},
                        modifier = Modifier.sizeIn(maxHeight = 450.dp, minHeight = 450.dp)
                    )
                }

                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileCircle(
                                imageUrl = state.apparel?.brand?.logoUrl ?: "",
                                modifier = Modifier.size(50.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = state.apparel?.brand?.name ?: "Unknown",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                fontSize = 27.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = state.apparel?.description ?: "No description for this apparel.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 3
                        )

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }

                item {
                    Text(
                        text = "SPECS",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ApparelSpec(
                        title = R.string.fabric,
                        icon = R.drawable.fabric_material_svgrepo_com_dark,
                        content = state.apparel?.fabric,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ApparelSpec(
                        title = R.string.category,
                        icon = R.drawable.category_svgrepo_com_dark,
                        content = state.apparel?.category,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ApparelSpec(
                        title = R.string.tags,
                        icon = R.drawable.tag_svgrepo_com_dark,
                        content = state.apparel?.tags?.joinToString(",")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ApparelSpec(
                        title = R.string.sex,
                        icon = R.drawable.gender_svgrepo_dark,
                        content = state.apparel?.sex
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ApparelSpec(
                        title = R.string.color,
                        icon = R.drawable.color_wheel_svgrepo_com_dark,
                        content = state.apparel?.color,
                        modifier = Modifier.fillMaxWidth()
                    )
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