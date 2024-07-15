package com.nagane.table.ui.screen.order.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

// 선택한 건 음영 효과에 밑은 main color로 대신 음영 있으니까 대비 가능 굿~
@Preview
@Composable
fun MethodBox(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isDisabled: Boolean = false,
    icon: ImageVector = Icons.Filled.CreditCard,
    text: Int = R.string.payment_card,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(width = 180.dp, height = 140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected || isDisabled) 8.dp else 0.dp
        ),
        border = BorderStroke((2).dp,
            if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
            else if (isSelected) nagane_theme_main
            else nagane_theme_sub
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
                    else if (isSelected) nagane_theme_sub
                    else nagane_theme_main
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        )
                        .width(80.dp),
                    thickness = 4.dp,
                    color = if (isDisabled) nagane_theme_light_0.copy(alpha = 0.5f)
                    else if (isSelected) nagane_theme_main
                    else nagane_theme_sub,
                )
                Icon(
                    icon,
                    contentDescription = stringResource(id = text),
                    tint = if (isDisabled) nagane_theme_light_0.copy(alpha = 0.5f)
                    else if (isSelected) nagane_theme_main
                    else nagane_theme_sub,
                    modifier = Modifier.size(44.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = text),
                    style = NaganeTypography.h2,
                    fontSize = 26.sp,
                    color = if (isDisabled) nagane_theme_light_0.copy(alpha = 0.5f)
                    else if (isSelected) nagane_theme_main
                    else nagane_theme_sub,
                )
            }
        }
    }
}