package com.nagane.table.ui.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_extra
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Preview(showSystemUi = true)
@Composable
fun CustomAppBarPreview() {
    CustomAppBarUI()
}

@Composable
fun CustomAppBarUI(
    title : String = "마이페이지",
    leftButton  :@Composable () -> Unit = {
        Box(modifier = Modifier.width(44.dp))
    },
    rightButton  :@Composable () -> Unit = {
        Box(modifier = Modifier.width(44.dp))
    },
    backgroundColor : Color = nagane_theme_main,
    subColor : Color = nagane_theme_sub
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            leftButton()
            Text(text = title,
                style = NaganeTypography.h1,
                color = subColor)
            rightButton()
        }
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    tint : Color = nagane_theme_sub
) {
    Icon(
        modifier = Modifier
            .size(40.dp)
            .clickable { onClick() },
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Localized description",
        tint = tint
    )
}

@Composable
fun SettingButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Settings,
            tint = nagane_theme_sub,
            contentDescription = "Localized description"
        )
    }
}

@Composable
fun ReportingButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
    ) {
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            tint = nagane_theme_sub,
            contentDescription = "Localized description"
        )
    }
}