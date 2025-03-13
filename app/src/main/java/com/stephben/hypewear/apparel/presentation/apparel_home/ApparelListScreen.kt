package com.stephben.hypewear.apparel.presentation.apparel_home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelHomeHeader
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelItem
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelListLoading
import com.stephben.hypewear.apparel.presentation.apparel_home.components.ApparelSearchBar
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun ApparelListScreen(
    viewModel: ApparelListViewModel = koinViewModel(),
    onApparelClick: (Apparel) -> Unit,
    modifier: Modifier
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    //val apparels by viewModel.apparels.collectAsState()
    //val errorMessage by viewModel.errorMessage.collectAsState()




    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp),
    ) {
//        ApparelSearchBar(
//            onSearchQueryChange = {
//                viewModel.onAction(ApparelListAction.OnSearchQueryChange(it))
//            },
//            searchQuery = state.searchQuery,
//            onImeSearch = {
//                keyboardController?.hide()
//            },
//            modifier = Modifier
//                .widthIn(max = 400.dp)
//                .fillMaxWidth()
//        )

        ApparelHomeHeader(
            //modifier = Modifier.padding(top = 8.dp)
        )

        if (state.isLoading) {
            ApparelListLoading()
        } else {

            Text(
                "New Items", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp)
            )

            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.searchResults) { apparel ->
                    ApparelItem(
                        apparel = apparel,
                        onClick = { onApparelClick(apparel) },
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