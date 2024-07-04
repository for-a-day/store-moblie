package com.nagane.table.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.MoveAdminButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTableTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController
) {
    var count by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { CustomAppBarUI(
            title = "",
            leftButton = {
                MoveAdminButton(navController = navController)
            }
        ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NaganeTableTheme {
        Surface {
            HomeScreen(navController = rememberNavController())
        }
    }
}