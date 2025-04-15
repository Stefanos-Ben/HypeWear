package com.stephben.hypewear.apparel.presentation.tempadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephben.hypewear.apparel.presentation.tempadd.components.BrandDropdown
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddApparelScreen(
    modifier: Modifier = Modifier,
    viewModel: AddApparelViewModel = koinViewModel(),
    onAddClick: () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Fetch brands on first composition
    LaunchedEffect(Unit) {
        viewModel.onAction(AddApparelAction.FetchBrands)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp)
    ) {
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

            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {

                // Brand dropdown
                item {
                    Text("Select Brand:", style = MaterialTheme.typography.bodyLarge)
                    BrandDropdown(
                        brands = state.brands,
                        selectedBrand = state.selectedBrand,
                        onBrandSelected = { brand ->
                            viewModel.onAction(AddApparelAction.OnSelectBrand(brand))
                        }
                    )
                }

                item {
                    TextField(
                        value = state.description,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnDescriptionChange(it)) },
                        label = { Text("Description") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.price,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnPriceChange(it)) },
                        label = { Text("Price") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.discount,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnDiscountChange(it)) },
                        label = { Text("Discount") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.currency,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnCurrencyChange(it)) },
                        label = { Text("Currency") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.imageUrl,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnImageUrlChange(it)) },
                        label = { Text("Image URL") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.color,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnColorChange(it)) },
                        label = { Text("Color") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.fabric,
                        onValueChange = { viewModel.onAction(AddApparelAction.OnFabricChange(it)) },
                        label = { Text("Fabric") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.materialSustainability,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnMaterialSustainabilityChange(it))
                        },
                        label = { Text("Material Sustainability") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.carbonFootprint,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnCarbonFootprintChange(it))
                        },
                        label = { Text("Carbon Footprint") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.waterFootprint,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnWaterFootprintChange(it))
                        },
                        label = { Text("Water Footprint") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.packagingSustainability,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnPackagingSustainabilityChange(it))
                        },
                        label = { Text("Packaging Sustainability") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.ecoScore,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnEcoScoreChange(it))
                        },
                        label = { Text("Eco Score") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    TextField(
                        value = state.ecoBadges,
                        onValueChange = {
                            viewModel.onAction(AddApparelAction.OnEcoBadgesChange(it))
                        },
                        label = { Text("Eco Badges (comma-separated)") },
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    Button(
                        onClick = {
                            viewModel.onAction(AddApparelAction.OnAddSubmit)
                            onAddClick()
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text("Add +")
                    }
                }
            }
        }

        Box(modifier =  Modifier.align(Alignment.BottomStart)){
            bottomBar()
        }
    }

}
