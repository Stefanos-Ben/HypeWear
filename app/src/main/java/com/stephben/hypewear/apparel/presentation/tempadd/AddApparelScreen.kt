package com.stephben.hypewear.apparel.presentation.tempadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddApparelScreen(
    viewModel: AddApparelViewModel = koinViewModel<AddApparelViewModel>(),
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "ADD AN APPAREL",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        TextField(
            value = state.title,
            onValueChange = { viewModel.onAction(AddApparelAction.OnTitleChange(it)) },
            placeholder = {
                Text(
                    text = "title"
                )
            }
        )
        TextField(
            value = state.description,
            onValueChange = { viewModel.onAction(AddApparelAction.OnDescriptionChange(it)) },
            placeholder = {
                Text(
                    text = "description"
                )
            }
        )
        TextField(
            value = state.price,
            onValueChange = { viewModel.onAction(AddApparelAction.OnPriceChange(it)) },
            placeholder = {
                Text(
                    text = "price"
                )
            }
        )
        TextField(
            value = state.imageUrl,
            onValueChange = { viewModel.onAction(AddApparelAction.OnImageUrlChange(it)) },
            placeholder = {
                Text(
                    text = "imageUrl"
                )
            }
        )
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