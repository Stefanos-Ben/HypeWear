package com.stephben.hypewear.apparel.presentation.apparel_list

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.apparel.presentation.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.components.ApparelSearchBar
import org.koin.androidx.compose.koinViewModel


@Composable
fun ApparelListScreen(
    viewModel: ApparelListViewModel = koinViewModel<ApparelListViewModel>()
) {
    val apparels by viewModel.apparels.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 56.dp)
            .padding(horizontal = 8.dp)
    ) {
        ApparelSearchBar(
            onSearchQueryChange = {
                // it.Search
            },
            searchQuery = "",
            onImeSearch = {}
        )
        if(errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }
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


/*@Preview(name = "Light Search Bar and Filter", showBackground = true)
@Preview(name = "Light Search Bar and Filter Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0xFF100E07)
@Composable
private fun ApparelListScreenPreview() {
    HypeWearTheme {
        ApparelListScreen()
    }

}*/