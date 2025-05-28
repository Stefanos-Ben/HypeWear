package com.stephben.hypewear.core.domain.utils

import android.net.Uri

interface ImageUploader {
    suspend fun upload(uri: Uri, publicId: String): String
}