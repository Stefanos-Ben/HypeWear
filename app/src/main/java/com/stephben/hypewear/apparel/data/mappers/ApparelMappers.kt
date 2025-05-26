package com.stephben.hypewear.apparel.data.mappers

import com.stephben.hypewear.apparel.data.dtos.ApparelDto
import com.stephben.hypewear.apparel.data.dtos.BrandInfoDto
import com.stephben.hypewear.apparel.data.dtos.EcoMetricsDto
import com.stephben.hypewear.apparel.domain.Apparel
import com.stephben.hypewear.apparel.domain.BrandInfo
import com.stephben.hypewear.apparel.domain.EcoMetrics
import com.stephben.hypewear.core.domain.utils.convertDateFormat
import java.util.Date


fun Apparel.toDto(
    trigrams: List<String>? = null,
    createdAtDate: Date? = null,
    updatedAtDate: Date? = null
): ApparelDto {

    val brandDto = BrandInfoDto(
        id = brand.id.ifBlank { null },
        name = brand.name.ifBlank { null },
        logoUrl = brand.logoUrl.ifBlank { null },
    )

    return ApparelDto(
        docId = this.apparelID.ifBlank { null },

        brand = brandDto,

        description = this.description.ifBlank { null },
        fabric = this.fabric.ifBlank { null },
        color = this.color.ifBlank { null },
        imageUrl = this.imageUrl.ifBlank { null },
        price = this.price.takeIf { it != 0.0 },
        currency = this.currency.ifBlank { null },
        stockPerSize = this.stockPerSize,
        ecoMetrics = EcoMetricsDto(
            materialSustainability = ecoMetrics.materialSustainability.takeIf { it != 0.0 },
            carbonFootprint = ecoMetrics.carbonFootprint.takeIf { it != 0.0 },
            waterFootprint = ecoMetrics.waterFootprint.takeIf { it != 0.0 },
            packagingSustainability = ecoMetrics.packagingSustainability.takeIf { it != 0.0 }
        ),
        ecoScore = this.ecoScore.takeIf { it != 0 },
        ecoBadges = this.ecoBadges.ifEmpty { null },

        sex = this.sex.ifBlank { null },
        category = this.category.ifBlank { null },
        tags = this.tags.ifEmpty { null },

        trigrams = trigrams,
        createdAt = createdAtDate,
        updatedAt = updatedAtDate
    )
}


fun ApparelDto.toApparel(): Apparel {

    val brandInfo = BrandInfo(
        id = this.brand?.id.orEmpty(),
        name = this.brand?.name.orEmpty(),
        logoUrl = this.brand?.logoUrl.orEmpty()
    )

    val domainEcoMetrics = EcoMetrics(
        materialSustainability = this.ecoMetrics?.materialSustainability ?: 0.0,
        carbonFootprint = this.ecoMetrics?.carbonFootprint ?: 0.0,
        waterFootprint = this.ecoMetrics?.waterFootprint ?: 0.0,
        packagingSustainability = this.ecoMetrics?.packagingSustainability ?: 0.0,
    )

    return Apparel(
        apparelID = this.docId.orEmpty(),

        brand = brandInfo,

        description = this.description.orEmpty(),
        fabric = this.fabric.orEmpty(),
        color = this.color.orEmpty(),
        imageUrl = this.imageUrl.orEmpty(),
        price = this.price ?: 0.0,
        currency = this.currency.orEmpty(),

        ecoMetrics = domainEcoMetrics,
        ecoScore = this.ecoScore ?: 0,
        ecoBadges = this.ecoBadges ?: emptyList(),

        sex = this.sex.orEmpty(),
        category = this.category.orEmpty(),
        tags = this.tags.orEmpty(),

        stockPerSize =  this.stockPerSize ?: emptyMap(),

        createdAt = convertDateFormat(this.createdAt),
        updatedAt = convertDateFormat(this.updatedAt)
    )
}