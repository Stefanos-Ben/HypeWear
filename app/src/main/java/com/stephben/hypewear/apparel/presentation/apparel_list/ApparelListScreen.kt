package com.stephben.hypewear.apparel.presentation.apparel_list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.components.ApparelSearchBar
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun ApparelListScreen() {

    val apparels = listOf(
        Apparel(
            title = "WhiteWorks SA",
            description = "Your go-to white Eco friendly t-shirt",
            price = 24.00
        ),
        Apparel(
            title = "BlackWorks",
            description = "Your go-to black Eco friendly t-shirt",
            price = 25.00
        ),
        Apparel(
            title = "BlueWorks",
            description = "Your go-to blue Eco friendly t-shirt",
            price = 26.00
        ),
        Apparel(
            title = "PinkWorks",
            description = "Your go-to pink Eco friendly t-shirt",
            price = 300.00
        ),
        Apparel(
            title = "WhiteWorks SA",
            description = "Your go-to white Eco friendly t-shirt",
            price = 24.00
        ),
        Apparel(
            title = "BlackWorks",
            description = "Your go-to black Eco friendly t-shirt",
            price = 25.00
        ),
        Apparel(
            title = "BlueWorks",
            description = "Your go-to blue Eco friendly t-shirt",
            price = 26.00
        ),
        Apparel(
            title = "PinkWorks",
            description = "Your go-to pink Eco friendly t-shirt",
            price = 300.00
        ),

    )

    Column(
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 24.dp)
            .padding(horizontal = 8.dp)
    ) {
        ApparelSearchBar(
            onSearchQueryChange = {
                // it.Search
            },
            searchQuery = "",
            onImeSearch = {}
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(apparels) { apparel ->
                    ApparelItem(
                        apparel = apparel,
                        onClick = {},
                    )
            }
        }
    }

}


@Preview(name = "Light Search Bar and Filter", showBackground = true)
@Preview(name = "Light Search Bar and Filter Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0xFF100E07)
@Composable
private fun ApparelListScreenPreview() {
    HypeWearTheme {
        ApparelListScreen()
    }

}