package com.nagane.table.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nagane.table.ui.screen.MoveAdminButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Composable
fun HomeAppBar(
    navController: NavController,
    title: String = "확인불가",
    tableNumber: String = "-99번"
) {
    CustomAppBarUI(
        title = title,
        leftButton = {
            MoveAdminButton(navController = navController)
        },
        rightButton = {
            Card(
                modifier = Modifier
                    .size(80.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                colors = CardDefaults.cardColors(
                    contentColor = nagane_theme_sub
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(nagane_theme_sub),
                ) {
                    Text(
                        text = tableNumber,
                        style = NaganeTypography.h2,
                        color = nagane_theme_main
                    )
                }
            }
        }
    )
}