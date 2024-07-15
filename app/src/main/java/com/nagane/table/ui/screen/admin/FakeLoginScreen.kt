package com.nagane.table.ui.screen.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.screen.home.MenuViewModel
import com.nagane.table.ui.screen.order.components.MethodBox
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@Preview(Devices.TABLET)
@Composable
fun FakeLoginScreen(
    navController : NavController = rememberNavController(),
    menuViewModel: MenuViewModel = viewModel()
) {
    val tableNumber = menuViewModel.tableNumber?.toInt() ?: -1

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = stringResource(id = R.string.admin_fake_title),
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
            RandomNumberGame(
                navController = navController,
                tableNumber = tableNumber
            )
        }

    }
}

@Composable
fun RandomNumberGame(
    navController : NavController,
    tableNumber: Int,
) {

    var isAnimating by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // var randomNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }

    var randomNumber by remember { mutableStateOf(tableNumber) }

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            randomNumber = Random.nextInt(0, 100)
            delay(100)  // 0.1초마다 랜덤 숫자 변경
        }
    }

    var nowStep by remember { mutableStateOf(1) }

    var animateBackgroundColor by remember {
        mutableStateOf(false)
    }

    val animatedColor by animateColorAsState(
        if (animateBackgroundColor) nagane_theme_sub else Color.Transparent,
        label = "color"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (nowStep) {
                1 -> "버튼을 눌러 시작해주세요.\n           "
                2 -> "홀? 짝?\n      "
                3 -> "정답입니다!\n버튼을 눌러 다시 시작해주세요."
                4 -> "오답입니다!\n버튼을 눌러 다시 시작해주세요."
                else -> ""
              },
                style = NaganeTypography.h1,
                color = nagane_theme_sub,
            textAlign = TextAlign.Center
                    )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .drawBehind {
                    drawRect(animatedColor)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = randomNumber.toString(),
                style = NaganeTypography.h1,
                fontSize = 64.sp,
                color = nagane_theme_sub
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(8.dp)
                .width(280.dp)
                .height(52.dp),
            onClick = {
                scope.launch {
                    if (isAnimating) {
                        animateBackgroundColor = true
                        // 현재 애니메이션이 실행 중이면 3초 동안 숫자를 변경한 후 멈춤
                        for (i in 0 until 30) {
                            randomNumber = Random.nextInt(0, 100)
                            delay(100)  // 0.1초마다 랜덤 숫자 변경
                        }
                        isAnimating = false
                        nowStep = 2
                    } else {
                        // 현재 애니메이션이 실행 중이 아니면 숫자를 계속 변경
                        isAnimating = true
                        // animateBackgroundColor = false
                    }
                }
            },
            enabled = (!animateBackgroundColor),
            colors = ButtonDefaults.buttonColors(
                containerColor = nagane_theme_sub,
                contentColor = nagane_theme_main,
                disabledContainerColor = nagane_theme_light_6.copy(alpha = 0.75f),
                disabledContentColor = nagane_theme_light_0.copy(alpha = 0.25f)
            )) {
            Text(
                text = if (animateBackgroundColor) "대기중" else if (isAnimating) "멈춤" else "시작",
                style = NaganeTypography.h2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .padding(8.dp)
                .width(280.dp)
                .height(52.dp),
            onClick = {
                navController.popBackStack()
            },
            enabled = !(nowStep == 2 || isAnimating),
            border = BorderStroke(2.dp, if (nowStep == 2 || isAnimating) nagane_theme_light_6.copy(alpha = 0.75f) else nagane_theme_sub),
            colors = ButtonDefaults.buttonColors(
                containerColor = nagane_theme_main,
                contentColor = nagane_theme_sub,
                disabledContainerColor = nagane_theme_light_6.copy(alpha = 0.75f),
                disabledContentColor = nagane_theme_light_0.copy(alpha = 0.25f)
            )) {
            Text(
                text = "나가기",
                style = NaganeTypography.h2,
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            MethodBox(
                text = R.string.odd,
                icon = Icons.Filled.LooksOne,
                isDisabled = nowStep != 2,
                isSelected = true,
                onClick = {
                    if (nowStep == 2) {
                        animateBackgroundColor = false
                        nowStep = if (randomNumber % 2 == 1) {
                            3
                        } else {
                            4
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.width(32.dp))
            MethodBox(
                text = R.string.even,
                icon = Icons.Filled.LooksTwo,
                isDisabled = nowStep != 2,
                isSelected = true,
                onClick = {
                    if (nowStep == 2) {
                        animateBackgroundColor = false
                        nowStep = if (randomNumber % 2 == 0) {
                            3
                        } else {
                            4
                        }
                    }
                }
            )
        }
    }
}


