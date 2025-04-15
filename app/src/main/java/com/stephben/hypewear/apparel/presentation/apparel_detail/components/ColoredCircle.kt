package com.stephben.hypewear.apparel.presentation.apparel_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun ColoredCircle(
    colorString: String,
    modifier: Modifier = Modifier
) {

        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(Color(colorString.toColorInt()))
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary)
        ){}


}


@Preview
@Composable
private fun ColoredCirclePrev() {
    HypeWearTheme {
        ColoredCircle(colorString = "#FFFEF2")
    }

}