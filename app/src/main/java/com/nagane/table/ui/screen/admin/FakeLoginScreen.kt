package com.nagane.table.ui.screen.admin

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview(Devices.TABLET)
@Composable
fun FakeLoginScreen(
    navController : NavController = rememberNavController()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = stringResource(id = R.string.admin_fake_title),
                leftButton = {
                    BackButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        tint = nagane_theme_main
                    )
                },
                backgroundColor = nagane_theme_sub,
                subColor = nagane_theme_main
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(nagane_theme_main),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            FakeGameContent()
        }

    }
}

@Preview
@Composable
fun FakeGameContent() {
    var toggled by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var isAnimating by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var randomNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }


    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            randomNumber = Random.nextInt(0, 100)
            delay(100)  // 0.1초마다 랜덤 숫자 변경
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RandomNumberGame(
            onClick = {
                scope.launch {
                    if (isAnimating) {
                        toggled = !toggled
                        // 현재 애니메이션이 실행 중이면 3초 동안 숫자를 변경한 후 멈춤
                        for (i in 0 until 30) {
                            randomNumber = Random.nextInt(0, 100)
                            delay(100)  // 0.1초마다 랜덤 숫자 변경
                        }
                        isAnimating = false
                    } else {
                        // 현재 애니메이션이 실행 중이 아니면 숫자를 계속 변경
                        isAnimating = true
                    }
                }
            },
            isAnimating = isAnimating,
            randomNumber = randomNumber
        )
        Column(
            modifier = Modifier
                .padding(vertical = 160.dp)
                .fillMaxSize()
                .clickable(indication = null, interactionSource = interactionSource) {
                    toggled = !toggled
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val offsetTarget = if (toggled) {
                IntOffset(0, 300)
            } else {
                IntOffset.Zero
            }
            val offset = animateIntOffsetAsState(
                targetValue = offsetTarget, label = "offset"
            )
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val offsetValue = if (isLookingAhead) offsetTarget else offset.value
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width + offsetValue.x, placeable.height + offsetValue.y) {
                            placeable.placeRelative(offsetValue)
                        }
                    }
                    .size(100.dp)
                    .background(nagane_theme_sub)
            )
        }
    }
}

@Composable
fun RandomNumberGame(
    onClick : () -> Unit = {},
    isAnimating : Boolean,
    randomNumber : Int,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = randomNumber.toString(),
                style = NaganeTypography.h1
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onClick) {
                Text(text = if (isAnimating) "Finish" else "Start")
            }
        }
    }
}
