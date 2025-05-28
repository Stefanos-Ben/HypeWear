package com.stephben.hypewear.brand.domain

import com.stephben.hypewear.core.domain.utils.Result

interface BrandRepository {
    suspend fun getAllBrands(): Result<List<Brand>>
    suspend fun getCurrentBrand(): Result<Brand>
    suspend fun updateBrand(brand: Brand): Result<Unit>
}