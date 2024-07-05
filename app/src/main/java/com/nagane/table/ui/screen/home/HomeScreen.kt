package com.nagane.table.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.MoveAdminButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Composable
fun HomeScreen(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel())
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = menuViewModel.tableName ?: "확인불가",
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
                                text = (menuViewModel.tableNumber ?: "-1") + "번",
                                style = NaganeTypography.h2,
                                color = nagane_theme_main
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeContent()
        }

    }
}

@Composable
private fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp)
    ) {
        CategoryRow()

        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(
                id = R.drawable.nagane_light_b),
                contentDescription = null,
                alpha = 0.3f
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MenuList()
            }
        }

    }
}


@Preview(
    device = Devices.TABLET)
@Composable
fun HomeScreenPreview() {
    NaganeTableTheme {
        Surface {
            HomeScreen(navController = rememberNavController())
        }
    }
}