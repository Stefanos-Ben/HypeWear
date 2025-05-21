package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.stephben.hypewear.apparel.presentation.apparel_detail.components.ColoredCircle
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.components.InfoLine

@Composable
fun ReviewStep(
    state: ApparelFormState,
    onAction: (ApparelFormAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 32.dp)
    ) {
        item {
            Text(
                text = "STEP 5: REVIEW & SUBMIT",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = state.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Color",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ColoredCircle(
                        colorString = state.color,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        /* ---- CATEGORY ---- */
        item {
            InfoLine(label = "Sex", value = state.sex)
            InfoLine(label = "Category", value = state.category)
            if (state.tags.isNotEmpty())
                InfoLine(label = "Tags", value = state.tags.joinToString())

            Spacer(modifier = Modifier.width(16.dp))
        }

        /* ---- PRICE & STOCK ---- */
        item {
            InfoLine(label = "Price", value = "${state.price} €")
            if (state.stockPerSize.isNotEmpty()) {
                Text(
                    text = "Stock:",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
                state.stockPerSize.forEach { (sz, qty) ->
                    Text(
                        text = "• $sz  ×  $qty",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        /* ---- ECO METRICS ---- */
        item {
            InfoLine("Fabric", state.fabric)
            InfoLine("Eco-Score", "${state.ecoScore}/100")
            InfoLine(
                "Carbon",
                "${state.carbonFootprint} kg",
                state.carbonFootprint.isNotBlank()
            )
            InfoLine(
                "Water",
                "${state.waterFootprint} L",
                state.waterFootprint.isNotBlank(),
            )
            InfoLine(
                "Pref. material %",
                "${state.preferredMaterialPct} %",
                state.preferredMaterialPct.isNotBlank()
            )
            InfoLine(
                "Packaging PCR %",
                "${state.packagingPCR} %",
                state.packagingPCR.isNotBlank()
            )
            if (state.packagingRecyclable)
                InfoLine("Packaging", "Recyclable")
            if (state.ecoBadges.isNotEmpty())
                InfoLine("Eco badges", state.ecoBadges.joinToString())
        }
    }
}