package com.stephben.hypewear.core.domain.utils

import androidx.compose.ui.graphics.Color

fun Color.toHex(): String =
    "#%02X%02X%02X".format(
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )