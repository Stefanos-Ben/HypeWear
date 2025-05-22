package com.stephben.hypewear.core.domain.utils

object SizeCharts {
    /** Alphabetical tops (tees, hoodies, jackets, dresses …) */
    val alphaTops = listOf("XXS","XS","S","M","L","XL","XXL","3XL")

    /** Men’s waist sizes in inches (28–42) */
    val menWaist = (26..50).map(Int::toString)

    /** Women’s waist sizes (24–36) */
    val womenWaist = (23..42).map(Int::toString)

    /** EU shoe sizes */
    val shoesEU = (35..50).map(Int::toString)

    /** Kids alpha + height band */
    val kidsTops = (86..164 step 6).map(Int::toString)
}