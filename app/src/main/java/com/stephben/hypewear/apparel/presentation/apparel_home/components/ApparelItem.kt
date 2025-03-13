package com.stephben.hypewear.apparel.presentation.apparel_home.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.BrandInfo
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@SuppressLint("DefaultLocale")
@Composable
fun ApparelItem(
    apparel: Apparel,
    onClick: () -> Unit
) {
    val formattedPrice = "%.${2}f".format(apparel.price)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .width(140.dp)
        ){
            ApparelPortrait(
                onClick =  onClick,
                imageUrl = apparel.imageUrl,

                )

            ApparelItemButtons(
                onCartClick = {},
                onFavoriteClick = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(apparel.brand.name, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "$formattedPrice ${apparel.currency}",
            style = MaterialTheme.typography.bodyMedium
        )
    }



}

@Preview(
    name = "Apparel Item Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Apparel Item Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF100E07
)
@Composable
private fun ApparelItemPreview() {
    HypeWearTheme {
        ApparelItem(
            apparel = Apparel(
                brand = BrandInfo(name="WhiteWorks SA"),
                description = "Your go-to white Eco friendly t-shirt",
                price = 24.00
            )
        ) { }
    }
}