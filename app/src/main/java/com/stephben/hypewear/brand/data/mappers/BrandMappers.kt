package com.stephben.hypewear.brand.data.mappers

import com.stephben.hypewear.brand.data.dtos.BrandDto
import com.stephben.hypewear.brand.domain.Brand
import com.stephben.hypewear.core.domain.utils.convertDateFormat
import java.util.Date

fun Brand.toDto(
    createdAtDate: Date? = null,
    updatedAtDate: Date? = null,
):BrandDto {
    return BrandDto(
      id = this.id.ifBlank { null },

      name = this.name.ifBlank { null },
      description = this.description.ifBlank { null },
      logoUrl = this.logoUrl.ifBlank { null },
      contactEmail = this.contactEmail.ifBlank { null },

      createdAt = createdAtDate,
      updatedAt = updatedAtDate,
    )
}

fun BrandDto.toBrand(): Brand {
    return Brand(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        logoUrl = this.logoUrl.orEmpty(),
        contactEmail = this.contactEmail.orEmpty(),


        createdAt = convertDateFormat(this.createdAt),
        updatedAt = convertDateFormat(this.updatedAt)
    )
}