package com.stephben.hypewear.apparel.presentation.apparel_detail.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stephben.hypewear.R
import com.stephben.hypewear.core.presentation.ui.theme.HypeWearTheme

@Composable
fun EcoMetricsArc(
    modifier: Modifier = Modifier,
    canvasSize: Dp = 300.dp,
    indicatorCarbonFootprint: Double = 0.0,
    maxCarbonFootprint: Int = 100,
    indicatorWaterFootprint: Double = 0.0,
    maxWaterFootprint: Int = 100,
    indicatorMaterialSustainability: Int = 0,
    maxMaterialSustainability: Int = 100,
    indicatorPackagingSustainability: Int = 0,
    maxPackagingSustainability: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 75f,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    foregroundIndicatorStrokeWidth: Float = 75f,

    ) {

    val animatedIndicatorCarbonFootprint = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(indicatorCarbonFootprint) {
        animatedIndicatorCarbonFootprint.animateTo(indicatorCarbonFootprint.toFloat())
    }
    val carbonFootprintPercentage =
        (animatedIndicatorCarbonFootprint.value / maxCarbonFootprint) * 100
    val carbonFootprintSweepAngle by animateFloatAsState(
        targetValue = (0.7 * carbonFootprintPercentage).toFloat(),
        animationSpec = tween(1000)
    )



    val animatedIndicatorWaterFootprint = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(indicatorWaterFootprint) {
        animatedIndicatorWaterFootprint.animateTo(indicatorWaterFootprint.toFloat())
    }
    val waterFootPrintPercentage =
        (animatedIndicatorWaterFootprint.value / maxWaterFootprint) * 100
    val waterFootprintSweepAngle by animateFloatAsState(
        targetValue = (0.7 * waterFootPrintPercentage).toFloat(),
        animationSpec = tween(1000)
    )


    val animatedIndicatorMaterialSustainability = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(indicatorMaterialSustainability) {
        animatedIndicatorMaterialSustainability.animateTo(indicatorMaterialSustainability.toFloat())
    }
    val materialSustainabilityPercentage =
        (animatedIndicatorMaterialSustainability.value / maxMaterialSustainability) * 100
    val materialSustainabilitySweepAngle by animateFloatAsState(
        targetValue = (0.7 * materialSustainabilityPercentage).toFloat(),
        animationSpec = tween(1000)
    )


    val animatedIndicatorPackagingSustainability = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(indicatorPackagingSustainability) {
        animatedIndicatorPackagingSustainability.animateTo(indicatorPackagingSustainability.toFloat())
    }
    val packagingSustainabilityPercentage =
        (animatedIndicatorPackagingSustainability.value / maxPackagingSustainability) * 100
    val packagingSustainabilitySweepAngle by animateFloatAsState(
        targetValue = (0.7 * packagingSustainabilityPercentage).toFloat(),
        animationSpec = tween(1000)
    )

    
    Column(
        modifier = modifier
            .size(canvasSize)
            .fillMaxWidth()
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWith = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWith = foregroundIndicatorStrokeWidth,
                    carbonFootprintSweepAngle = carbonFootprintSweepAngle,
                    waterFootprintSweepAngle = waterFootprintSweepAngle,
                    materialSustainabilitySweepAngle = materialSustainabilitySweepAngle,
                    packagingSustainabilitySweepAngle = packagingSustainabilitySweepAngle
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            OuterBox(
                modifier = Modifier.align(Alignment.Center)
            )

            InnerBox(
                modifier = Modifier
                    .size(canvasSize / 2f)
                    .align(Alignment.Center)
            )

            IndicatorBox(
                carbonFootprint = indicatorCarbonFootprint,
                waterFootprint = indicatorWaterFootprint,
                materialSustainability = indicatorMaterialSustainability,
                packagingSustainability = indicatorPackagingSustainability,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWith: Float,
){

    // Carbon Footprint
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 10f,
        sweepAngle = 70f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Water Footprint
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 100f,
        sweepAngle = 70f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Material Sustainability
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 190f,
        sweepAngle = 70f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Packaging Sustainability
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 280f,
        sweepAngle = 70f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

}

fun DrawScope.foregroundIndicator(
    carbonFootprintSweepAngle: Float,
    waterFootprintSweepAngle: Float,
    materialSustainabilitySweepAngle: Float,
    packagingSustainabilitySweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWith: Float,
){
    // Carbon Footprint - BottomRight
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 10f + 70f, //Reverse
        sweepAngle = -carbonFootprintSweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Water Footprint - BottomLeft
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 100f,
        sweepAngle = waterFootprintSweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Material Sustainability - TopLeft
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 190f + 70f, //Reverse
        sweepAngle = -materialSustainabilitySweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

    // Packaging sustainability - TopRight
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 280f,
        sweepAngle = packagingSustainabilitySweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWith,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) /2f
        )
    )

}



@Composable
fun IndicatorBox(
    modifier: Modifier = Modifier,
    carbonFootprint: Double,
    waterFootprint: Double,
    materialSustainability: Int,
    packagingSustainability: Int,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(55.dp)
    ){
        Text(
            text = "$carbonFootprint",
            style = MaterialTheme.typography.bodyLarge,
            color = if(carbonFootprint < 45) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Text(
            text = "$waterFootprint",
            style = MaterialTheme.typography.bodyLarge,
            color = if(waterFootprint < 45) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.BottomStart)
        )

        Text(
            text = "$materialSustainability",
            style = MaterialTheme.typography.bodyLarge,
            color = if(materialSustainability < 45) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.TopStart)
        )

        Text(
            text = "$packagingSustainability",
            style = MaterialTheme.typography.bodyLarge,
            color = if(packagingSustainability < 45) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun InnerBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp)

    ) {
        Icon(
            painter = painterResource(R.drawable.carbon_footprint_lm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(40.dp)
        )
        Icon(
            painter = painterResource(R.drawable.water_footprint_lm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(40.dp)
        )
        Icon(
            painter = painterResource(R.drawable.material_sustainability_lm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp)
                .size(28.dp)

        )
        Icon(
            painter = painterResource(
                R.drawable.packaging_sustainability_lm),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp)
                .size(28.dp)

        )

    }
}

@Composable
fun OuterBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Carbon\nFootprint",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.BottomEnd),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
        Text(
            text = "Water\nFootprint",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.BottomStart),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )
        Text(
            text = "Material\nSust.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.TopStart),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )
        Text(
            text = "Packaging\nSust.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.TopEnd),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun EcoMetricsPrev() {
    HypeWearTheme {
        EcoMetricsArc()
    }
}