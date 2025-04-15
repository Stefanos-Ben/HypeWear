package com.stephben.hypewear.brand.domain

import com.stephben.hypewear.core.domain.utils.Result

interface BrandRepository {
    suspend fun getAllBrands(): Result<List<Brand>>
}