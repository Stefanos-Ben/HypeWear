package com.stephben.hypewear.core.domain.utils

object FabricLibrary {
    data class Entry(val label: String, val msi: Int)

    val items = linkedMapOf(
        "OrganicCotton" to Entry("100 % organic cotton", 27),
        "ConventionalCotton" to Entry("100 % conventional cotton", 36),
        "RecycledCotton" to Entry("Recycled cotton (≥ 50%)", 6),
        "VirginPolyester" to Entry("Virgin polyester", 43),
        "RecycledPolyester" to Entry("rPET / recycled polyester", 25),
        "TencelLyocell" to Entry("TENCEL™ Lyocell", 16),
        "Hemp" to Entry("Hemp", 15),
        "BambooViscose" to Entry("Bamboo viscose", 30),
        "MerinoWool" to Entry("Merino wool", 50),
        "RecycledNylon" to Entry("Recycled nylon", 26),
        "VirginNylon" to Entry("Virgin nylon", 48),
        "LeatherBovine" to Entry("Bovine leather", 60),
        "Silk" to Entry("Silk", 56),
        "Other" to Entry("Other / blend...", 40)
    )
}