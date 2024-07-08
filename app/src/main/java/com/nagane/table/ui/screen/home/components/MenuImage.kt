package com.nagane.table.ui.screen.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_10
import com.nagane.table.ui.util.bitmapToImageBitmap
import com.nagane.table.ui.util.convertImageByteArrayToBitmap
import com.nagane.table.ui.util.decodeBase64ToByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Preview
@Composable
fun MenuImage(
    imageData: String = "",
    imageSize: Dp = 160.dp,
    soldOut: Boolean = false
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageData) {
        try {
            val byteArray = decodeBase64ToByteArray(imageData)
            val bitmap = withContext(Dispatchers.IO) {
                convertImageByteArrayToBitmap(byteArray)
            }
            imageBitmap = bitmapToImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e("DATA CONVERT ERROR", "이미지 변환 과정 중 에러 발생")
        }
    }


    Card(
        modifier = Modifier.size(imageSize),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageData == "" || imageBitmap == null) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.cake_piece),
                    contentDescription = null
                )
            } else {
                imageBitmap?.let {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = it,
                        contentDescription = null
                    )
                }
            }
            if (soldOut) {
                SoldOutInfo()
            }
        }
    }
}

@Composable
fun SoldOutInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(nagane_theme_light_10.copy(alpha = 0.75f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Block,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = nagane_theme_light_0.copy(alpha = 0.75f)
        )
        Text(
            text = stringResource(id = R.string.sold_out),
            style = NaganeTypography.h1,
            color = nagane_theme_light_0.copy(alpha = 0.75f)
        )
    }
}

@Preview
@Composable
fun SoldOutMenuImagePreview() {
    MenuImage(soldOut = true)
}