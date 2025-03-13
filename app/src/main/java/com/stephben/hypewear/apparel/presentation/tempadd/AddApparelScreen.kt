package com.stephben.hypewear.apparel.presentation.tempadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddApparelScreen(
    modifier: Modifier = Modifier,
    viewModel: AddApparelViewModel = koinViewModel<AddApparelViewModel>(),
    onAddClick: () -> Unit,
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "ADD AN APPAREL",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 32.dp)
        )

        LazyColumn (
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(start = 40.dp, end = 40.dp, top = 16.dp, bottom = 36.dp)
        ) {

            item {
                TextField(
                    value = state.title,
                    maxLines = 3,
                    onValueChange = { viewModel.onAction(AddApparelAction.OnTitleChange(it)) },
                    placeholder = {
                        Text(
                            text = "title"
                        )
                    },
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 20.dp)
                ) }

            item {
                TextField(
                    value = state.description,
                    onValueChange = { viewModel.onAction(AddApparelAction.OnDescriptionChange(it)) },
                    minLines = 10,
                    maxLines = 10,
                    placeholder = {
                        Text(
                            text = "description"
                        )
                    },
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }

            item {
                TextField(
                    value = state.price,
                    onValueChange = { viewModel.onAction(AddApparelAction.OnPriceChange(it)) },
                    maxLines = 3,
                    placeholder = {
                        Text(
                            text = "price"
                        )
                    },
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }

            item {
                TextField(
                    value = state.imageUrl,
                    onValueChange = { viewModel.onAction(AddApparelAction.OnImageUrlChange(it)) },
                    placeholder = {
                        Text(
                            text = "imageUrl"
                        )
                    },
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }

            item {
                Button(
                    onClick = {
                        viewModel.onAction(AddApparelAction.OnAddSubmit)
                        onAddClick()
                    }
                ) {
                    Text("Add +")
                }
            }


        }

    }

}


//@Preview(name = "Light Add Apparel", showBackground = true)
//@Preview(
//    name = "Add apparel Dark",
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    backgroundColor = 0xFF100E07
//)
//@Composable
//private fun AddApparelScreenPreview() {
//    HypeWearTheme {
//        AddApparelScreen()
//    }
//}