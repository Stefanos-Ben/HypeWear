package com.stephben.hypewear.core.domain.utils

object SizeCharts {
    /** Alphabetical tops (tees, hoodies, jackets, dresses …) */
    val alphaTops = listOf("XXS","XS","S","M","L","XL","XXL","3XL")

    /** Men’s waist sizes in inches (28–42) */
    val menWaist = (28..42 step 2).map(Int::toString)

    /** Women’s waist sizes (24–36) */
    val womenWaist = (24..36 step 2).map(Int::toString)

    /** EU shoe sizes */
    val shoesEU = (36..46).map(Int::toString)

    /** Kids alpha + height band */
    val kidsTops = listOf("98", "104", "110", "116", "122", "128", "134", "140")
}