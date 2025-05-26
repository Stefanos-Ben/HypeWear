package com.stephben.hypewear.brand.presentation.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.R
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.brand.presentation.collection.components.CollectionItem
import com.stephben.hypewear.brand.presentation.collection.components.ConfirmDeleteDialog
import com.stephben.hypewear.core.presentation.components.ApparelListLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = koinViewModel(),
    bottomBar: @Composable () -> Unit,
    onEditClick: (String) -> Unit,
    onAddApparel: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    var apparelToDelete by remember { mutableStateOf<Apparel?>(null) }

    LaunchedEffect(Unit) {
        viewModel.onAction(CollectionAction.GetApparels)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddApparel,
                shape = CircleShape,
                content = {
                    Icon(painter = painterResource(R.drawable.add_24), contentDescription = null)
                },
                modifier = Modifier.padding(bottom = 32.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 4.dp)
                .padding(contentPadding)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp)
                    .padding(top = 32.dp),
            ) {
                if (state.isLoading) {
                    ApparelListLoading(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Text(
                        "My Collection",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    if (state.apparels.isEmpty()) {
                        Spacer(modifier = Modifier.height(64.dp))

                        Image(
                            painter = painterResource(R.drawable.empty_collection),
                            contentDescription = "Empty Collection",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Your collection is empty. Start adding apparels by" +
                                    "pressing the + button below",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        LazyVerticalGrid (
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(state.apparels) { apparel ->
                                CollectionItem(
                                    apparel = apparel,
                                    onEditClick = { onEditClick(apparel.apparelID) },
                                    onDeleteClick = { apparelToDelete = apparel }
                                )
                            }
                        }
                    }

                }
            }
            Box(modifier =  Modifier.align(Alignment.BottomStart)){ bottomBar() }
        }
    }

    if (apparelToDelete != null) {
        ConfirmDeleteDialog(
            item = apparelToDelete!!,
            onConfirm = {
                viewModel.onAction(CollectionAction.OnDelete(apparelToDelete!!.apparelID))
                apparelToDelete = null
                viewModel.onAction(CollectionAction.GetApparels)
            },
            onDismiss = { apparelToDelete = null }
        )
    }

}