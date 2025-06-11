package com.stephben.hypewear.user.data.mappers

import com.stephben.hypewear.user.data.dtos.UserDto
import com.stephben.hypewear.user.domain.User
import java.util.Date

fun User.toDto(
    createdAt: Date? = null,
    updatedAt: Date? = null,
    ): UserDto {

    return UserDto(
        userId = this.userId.ifBlank { null },
        displayName = this.displayName.ifBlank { null },
        email =  this.email.ifBlank { null },
        photoUrl = this.photoUrl.ifBlank { null },
        userType = this.userType.ifBlank { "default" },
        brandId = this.brandId.ifBlank { null },
        isEmailVerified = this.isEmailVerified,
        favorites = this.favorites,
        cart = this.cart.map { it.toDto() },
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun UserDto.toUser(): User {

    return User(
        userId = this.userId.orEmpty(),
        displayName = this.displayName.orEmpty(),
        email = this.email.orEmpty(),
        photoUrl = this.photoUrl.orEmpty(),
        userType = this.userType.orEmpty(),
        brandId = this.brandId.orEmpty(),
        favorites = this.favorites.orEmpty(),
        cart = this.cart?.map { it.toCart() } ?: emptyList(),
        isEmailVerified = this.isEmailVerified ?: false,
    )
}