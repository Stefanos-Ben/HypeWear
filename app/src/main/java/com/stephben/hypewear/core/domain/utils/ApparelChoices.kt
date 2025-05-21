package com.stephben.hypewear.core.domain.utils

object ApparelChoices {
    val sexes = listOf("Men", "Women", "Unisex", "Kids")

    val categories = listOf(
        "T-shirt", "Hoodie", "Sweatshirt", "Jacket",
        "Jeans", "Trousers", "Shorts", "Dress",
        "Skirt", "Sneakers", "Boots", "Accessories"
    )

    val tags = listOf(
        "Streetwear", "Minimal", "Formal", "Vintage",
        "Techwear", "Sustainable", "Athleisure",
        "Limited Edition", "Luxury"
    )

    val ecoBadges = listOf(
        "bluesign® APPROVED",
        "GOTS Certified",
        "OEKO-TEX® STANDARD 100",
        "Fairtrade Textile",
        "RWS (Responsible Wool)",
        "GRS (Global Recycled Standard)",
        "Carbon Neutral Product",
        "PETA-Approved Vegan",
        "B Corp™ Brand"
    )
}