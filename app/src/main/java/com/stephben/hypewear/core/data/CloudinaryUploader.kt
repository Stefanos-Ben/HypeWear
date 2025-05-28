package com.stephben.hypewear.core.data

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.stephben.hypewear.core.domain.utils.BRAND_PRESET
import com.stephben.hypewear.core.domain.utils.CLOUD_NAME
import com.stephben.hypewear.core.domain.utils.ImageUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resumeWithException

class CloudinaryUploader(
    context: Context
): ImageUploader {
    init {
        val cfg = ConcurrentHashMap<String, Any>().apply {
            this["cloud_name"] = CLOUD_NAME
            this["brand_preset"] = BRAND_PRESET
        }
        MediaManager.init(context, cfg)
    }

    override suspend fun upload(uri: Uri, publicId: String): String =
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { cont ->
                MediaManager.get().upload(uri)
                    .unsigned(BRAND_PRESET)
                    .option("public_id", publicId)
                    .callback(object: UploadCallback {
                        override fun onSuccess(id: String?, data: Map<*, *>) {
                            cont.resume(data["secure_url"] as String) { _, _, _ -> }
                        }

                        override fun onError(id: String?, err: ErrorInfo?) =
                            cont.resumeWithException(
                                RuntimeException(err?.description ?: "Upload failed")
                            )
                        override fun onProgress(id: String?, bytes: Long, total: Long) {}
                        override fun onReschedule(id: String?, err: ErrorInfo?) {}
                        override fun onStart(id: String?) {}
                    })
                    .dispatch()
            }
        }
}