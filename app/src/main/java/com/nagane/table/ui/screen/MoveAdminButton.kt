package com.nagane.table.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun MoveAdminButton(
    navController: NavController = rememberNavController()
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
            navController.navigate("admin")
            resetCounter()
        }
    }

    Image(
        modifier = Modifier
            .width(120.dp)
            .padding(horizontal = 16.dp)
            .clickable { onButtonClick() },
        painter = painterResource(id = R.drawable.nagane_dark_m),
        contentDescription = "Logo of Nagane"
    )
}
