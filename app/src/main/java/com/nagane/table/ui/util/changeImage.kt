package com.nagane.table.ui.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayInputStream

/** 서버 측 이미지 데이터 String => byte array => bitmap => image bitmap으로 변환 */
fun decodeBase64ToByteArray(base64String: String): ByteArray {
    return Base64.decode(base64String, Base64.DEFAULT)
}

fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
}

fun bitmapToImageBitmap(bitmap: Bitmap): ImageBitmap {
    return bitmap.asImageBitmap()
}
