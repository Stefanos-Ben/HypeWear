package com.stephben.hypewear.apparel.presentation.apparel_list

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.presentation.apparel_list.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.apparel_list.components.ApparelListLoading
import com.stephben.hypewear.apparel.presentation.apparel_list.components.ApparelSearchBar
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun ApparelListScreen(
    viewModel: ApparelListViewModel = koinViewModel(),
    onApparelClick: () -> Unit,
    modifier: Modifier
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    //val apparels by viewModel.apparels.collectAsState()
    //val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 56.dp)
            .padding(horizontal = 8.dp)
    ) {
        ApparelSearchBar(
            onSearchQueryChange = {
                viewModel.onAction(ApparelListAction.OnSearchQueryChange(it))
            },
            searchQuery = state.searchQuery,
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
        )
        if (state.isLoading) {
            ApparelListLoading()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.searchResults) { apparel ->
                    ApparelItem(
                        apparel = apparel,
                        onClick = onApparelClick,
                    )
                }
            }
        }

    }

}


@Preview(name = "Light Search Bar and Filter", showBackground = true)
@Preview(name = "Light Search Bar and Filter Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0xFF100E07)
@Composable
private fun ApparelListScreenPreview() {
    HypeWearTheme {
        ApparelListScreen(modifier = Modifier, onApparelClick = {})
    }

}