package com.stephben.hypewear.apparel.presentation.apparel_form.step_screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormAction
import com.stephben.hypewear.apparel.presentation.apparel_form.ApparelFormState
import com.stephben.hypewear.apparel.presentation.apparel_form.components.StockRow
import com.stephben.hypewear.core.domain.utils.sizeOptions

@Composable
fun PriceStockStep(
    state: ApparelFormState,
    onAction: (ApparelFormAction) -> Unit
) {
    val allowedSizes = sizeOptions(state.sex, state.category)
    Log.i("ALLOWED SIZES", allowedSizes.toString())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 32.dp)
    ){

        item {
            Text(
                text = "STEP 3: PRICE & STOCK",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
        }

        /* — PRICE — */
        item {
            OutlinedTextField(
                value = state.price,
                onValueChange = { onAction(ApparelFormAction.OnFieldChanged("price", it)) },
                label = { Text("Price (€)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.fieldErrors["price"] != null,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                state.fieldErrors["price"].orEmpty(),
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(24.dp))
        }

        /* — STOCK rows — */
        item {
            Text(
                text = "STOCK PER SIZE",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(16.dp))

            state.stockPerSize.forEach { (sizeKey, qty) ->
                StockRow(
                    size = sizeKey,
                    qty = qty,
                    allowedSizes = allowedSizes,
                    onSizeChange = { newSize ->
                        onAction(ApparelFormAction.OnFieldChanged("sizeKey:$sizeKey", newSize))
                    },
                    onQtyChange = {
                        onAction(ApparelFormAction.OnFieldChanged("stock:$sizeKey", it))
                    },
                    onRemove = { onAction(ApparelFormAction.OnRemoveSize(sizeKey)) }
                )
                Spacer(Modifier.height(8.dp))
            }

            TextButton(onClick = { onAction(ApparelFormAction.OnAddSizeRow) }) {
                Text("+ Add size")
            }

            Text(
                state.fieldErrors["stockPerSize"].orEmpty(),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
