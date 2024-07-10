package com.nagane.table.ui.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun MoveFakeButton(
    navController: NavController = rememberNavController(),
    tableNumber: String = "-99번"
) {
    var count by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    fun resetCounter() {
        timerJob?.cancel()
        count = 0
    }

    fun startTimer() {
        timerJob = scope.launch {
            delay(10000L)
            resetCounter()
        }
    }

    fun onButtonClick() {
        if (count == 0) {
            startTimer()
        }

        count++

        if (count >= 5) {
            navController.navigate(Screens.Fake.route)
            resetCounter()
        }
    }

    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable {
                onButtonClick()
            },
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
