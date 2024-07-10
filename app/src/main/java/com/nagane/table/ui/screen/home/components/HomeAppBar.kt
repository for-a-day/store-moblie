package com.nagane.table.ui.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nagane.table.R
import com.nagane.table.ui.main.Screens
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
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .padding(horizontal = 16.dp),
                painter = painterResource(id = R.drawable.nagane_dark_m),
                contentDescription = "Logo of Nagane"
            )
        },
        rightBu.
        tton = {
            MoveFakeButton(
                navController = navController,
                tableNumber = tableNumber
            )
        }
    )
}