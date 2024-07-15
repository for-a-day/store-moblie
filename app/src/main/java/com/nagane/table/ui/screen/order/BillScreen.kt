package com.nagane.table.ui.screen.order

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Index
import com.nagane.table.R
import com.nagane.table.data.model.Order
import com.nagane.table.data.model.OrderList
import com.nagane.table.data.model.OrderMenuDto
import com.nagane.table.data.model.OrderMenuResponseDto
import com.nagane.table.data.model.OrderResponseDto
import com.nagane.table.data.model.TableLogin
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_extra
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_light_8
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_mainsub
import com.nagane.table.ui.theme.nagane_theme_sub
import com.nagane.table.ui.util.getDate
import com.nagane.table.ui.util.getDay
import com.nagane.table.ui.util.getHour
import com.nagane.table.ui.util.getMinute
import com.nagane.table.ui.util.getMonth
import com.nagane.table.ui.util.getSecond
import com.nagane.table.ui.util.getTimeDifferenceString
import com.nagane.table.ui.util.getYear

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BillScreen(
    navController: NavController,
    orderViewModel: OrderViewModel = viewModel(),
) {
    val orderList by orderViewModel.orderList
    var nowSelected by remember { mutableStateOf(0) }

    val highlightColor = nagane_theme_main
    val darkHighlightColor = nagane_theme_sub
    val defaultSubColor = nagane_theme_sub
    val darkSubColor = nagane_theme_main

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = stringResource(id = R.string.order_title),
//                leftButton = {
//                    BackButton(
//                        onClick = {
//                            navController.popBackStack()
//                        },
//                        tint = defaultSubColor
//                    )
//                },
                backgroundColor = highlightColor,
                subColor = defaultSubColor
            )
        }
    ) {
        if (orderList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(nagane_theme_light_0),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.order_yet),
                    style = NaganeTypography.h1,
                    fontSize = 32.sp,
                    color = highlightColor
                )
                Spacer(modifier = Modifier.height(64.dp))
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(280.dp)
                        .height(52.dp),
                    onClick = {
                        navController.popBackStack()
                    },
                    border = BorderStroke(2.dp, nagane_theme_main),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = nagane_theme_main,
                        contentColor = nagane_theme_sub,
                        disabledContainerColor = nagane_theme_light_6.copy(alpha = 0.75f),
                        disabledContentColor = nagane_theme_light_0.copy(alpha = 0.25f)
                    )) {
                    Text(
                        text = "닫기",
                        style = NaganeTypography.h2,
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(nagane_theme_light_0)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    BillSideContent(
                        modifier = Modifier.weight(1f),
                        order = orderList[nowSelected],
                        nowSelected = nowSelected,
                    )
                    OrderSideContent(
                        modifier = Modifier.weight(1f),
                        orderList = orderList,
                        nowSelected = nowSelected,
                        changeSelect = { nowIndex ->
                            nowSelected = nowIndex
                        },
                        closeScreen = {
                            navController.navigate(Screens.Home.route) {
                                popUpTo(Screens.Bill.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderSideContent(
    modifier : Modifier,
    orderList: List<OrderResponseDto>,
    highlightColor : Color = nagane_theme_main,
    defaultSubColor : Color = nagane_theme_sub,
    nowSelected : Int,
    changeSelect : (Int) -> Unit = {},
    closeScreen: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(
                vertical = 16.dp,
                horizontal = 64.dp
            )
    ) {
        Text(
            text = stringResource(id = R.string.order_check_title),
            style = NaganeTypography.h3,
            fontSize = 28.sp,
            color = nagane_theme_main
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 16.dp),
            thickness = 2.dp,
            color = nagane_theme_main
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                itemsIndexed(orderList) { index, order ->
                    OrderCheckBox(
                        order = order,
                        isSelected = index == nowSelected,
                        index = (orderList.size - index),
                        onClick = { changeSelect(index) }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.BottomCenter)
                    .background(nagane_theme_light_0),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = closeScreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = nagane_theme_main,
                        contentColor = nagane_theme_light_0,
                        disabledContainerColor = nagane_theme_light_8,
                        disabledContentColor = nagane_theme_light_0
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.close),
                        style = NaganeTypography.h1,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderCheckBox(
    isSelected: Boolean = false,
    isDisabled: Boolean = false,
    onClick: () -> Unit = {},
    order: OrderResponseDto,
    index: Int = 0,
    highlightColor : Color = nagane_theme_main,
    defaultSubColor : Color = nagane_theme_sub
) {
    val innerColor = if (isDisabled) nagane_theme_light_0.copy(alpha = 0.5f)
                        else if (isSelected) nagane_theme_sub
                        else nagane_theme_main

    val innerDarkColor = if (isDisabled) nagane_theme_light_0.copy(alpha = 0.5f)
                            else if (isSelected) nagane_theme_main
                            else nagane_theme_sub

    val borderColor = if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
                            else nagane_theme_main

    val borderDarkColor = if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
            else if (isSelected) nagane_theme_main
            else nagane_theme_sub

    val containerDarkColor = if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
                                else if (isSelected) nagane_theme_sub
                                else nagane_theme_main
    val containerColor = if (isDisabled) nagane_theme_light_6.copy(alpha = 0.75f)
                                else if (isSelected) nagane_theme_main
                                else nagane_theme_light_0

    val menuName = order.orderMenuDetailList.first().menuName
    val displayedMenuName = if (menuName.length > 5) {
        "${menuName.take(5)}..."
    } else {
        menuName
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 0.dp
        ),
        border = BorderStroke((2).dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    containerColor
                )
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${index}",
                    style = NaganeTypography.h1,
                    fontSize = 24.sp,
                    color = innerColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "번째 주문",
                    style = NaganeTypography.h1,
                    fontSize = 18.sp,
                    color = innerColor
                )
            }
            Text(
                text = if (order.orderMenuDetailList.size == 1) displayedMenuName else "$displayedMenuName 외 ${order.orderMenuDetailList.size-1} 품목",
                style = NaganeTypography.p,
                fontSize = 20.sp,
                color = innerColor
            )
//            Text(
//                text = "총 ${order.amount} 원",
//                style = NaganeTypography.i,
//                fontSize = 22.sp,
//                color = innerColor,
//            )
            Text(
                text = getTimeDifferenceString(order.orderDate),
                style = NaganeTypography.h2,
                fontSize = 20.sp,
                color = innerColor,
            )
        }
    }
}


@Composable
fun BillSideContent(
    modifier : Modifier,
    highlightColor : Color = nagane_theme_main,
    defaultSubColor : Color = nagane_theme_sub,
    nowSelected: Int,
    order: OrderResponseDto,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(nagane_theme_sub),
            // .border(1.dp, highlightColor),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(
            id = R.drawable.nagane_light_b),
            contentDescription = null,
            alpha = 0.3f
        )
        ReceiptFrame(
            order = order,
            nowSelected = nowSelected,
        )
    }
}

@Composable
fun ReceiptFrame(
    modifier: Modifier = Modifier,
    highlightColor : Color = nagane_theme_main,
    defaultSubColor : Color = nagane_theme_sub,
    nowSelected: Int,
    order: OrderResponseDto,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(nagane_theme_sub)
            .border(1.dp, highlightColor),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(
            id = R.drawable.nagane_light_b),
            contentDescription = null,
            alpha = 0.3f
        )
        ReceiptContent(
            nowSelected = nowSelected,
            order = order
        )
    }
}

@Composable
fun ReceiptContent(
    nowSelected: Int,
    order: OrderResponseDto,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${getYear(order.orderDate)}년 ${getMonth(order.orderDate)}월 ${getDay(order.orderDate)}일",
                    style = NaganeTypography.b,
                    color = nagane_theme_extra.copy(alpha = 0.9f),
                    fontSize = 28.sp,
                )
                Text(
                    text = "${getHour(order.orderDate)}시 ${getMinute(order.orderDate)}분",
                    style = NaganeTypography.i,
                    color = nagane_theme_extra.copy(alpha = 0.9f),
                    fontSize = 22.sp,
                )
            }
            Divider(
                modifier =  Modifier.padding(vertical = 16.dp),
                thickness = 2.dp,
                color = nagane_theme_extra.copy(alpha = 0.75f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            itemsIndexed(order.orderMenuDetailList) { index, orderMenu ->
                OrderMenuBlock(
                    orderMenu = orderMenu,
                    index = index + 1
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                modifier =  Modifier.padding(vertical = 16.dp),
                thickness = 2.dp,
                color = nagane_theme_extra.copy(alpha = 0.75f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = stringResource(id = R.string.total_price_check),
                    style = NaganeTypography.b,
                    color = nagane_theme_extra.copy(alpha = 0.9f),
                    fontSize = 24.sp,
                )
                Text(
                    text = "${order.orderMenuDetailList.sumOf { it.price * it.quantity }}원",
                    style = NaganeTypography.b,
                    color = nagane_theme_main.copy(alpha = 0.9f),
                    fontSize = 28.sp,
                )
            }
        }
    }
}

@Composable
fun OrderMenuBlock(
    orderMenu : OrderMenuResponseDto,
    index: Int = 0
) {

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = 8.dp
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .width(120.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = orderMenu.menuName,
                    style = NaganeTypography.h2,
                    fontSize = 22.sp,
                    color = nagane_theme_extra.copy(alpha = 0.8f),
                )
            }
            Text(
                text = "${orderMenu.price}",
                style = NaganeTypography.h2,
                color = nagane_theme_extra.copy(alpha = 0.65f),
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${orderMenu.quantity} 개",
                style = NaganeTypography.h2,
                color = nagane_theme_extra.copy(alpha = 0.65f),
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${orderMenu.price * orderMenu.quantity}원",
                style = NaganeTypography.h2,
                color = nagane_theme_extra.copy(alpha = 0.8f),
                fontSize = 22.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp),
            thickness = 1.dp,
            color = nagane_theme_extra.copy(alpha = 0.6f)
        )
    }
}

@Preview(
    device = Devices.TABLET)
@Composable
fun BillFramePreview() {
    NaganeTableTheme {
        Surface {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                ReceiptFrame(
                    modifier = Modifier.weight(1f),
                    order = OrderResponseDto(orderNo=21, amount=10000, orderDate="2024-07-09T17:03:44.234451", paymentMethod="CARD", tableNumber=4004, orderMenuDetailList= listOf(OrderMenuResponseDto(menuNo=1, menuName="넘쳐나는 사랑을 담은 라떼", quantity=2, price=3000))),
                    nowSelected = 1
                )
                ReceiptFrame(
                    modifier = Modifier.weight(1f),
                    order = OrderResponseDto(orderNo=21, amount=10000, orderDate="2024-07-09T17:03:44.234451", paymentMethod="CARD",tableNumber=4004, orderMenuDetailList= listOf(OrderMenuResponseDto(menuNo=1, menuName="넘쳐나는 사랑을 담은 라떼", quantity=2, price = 5000))),
                    nowSelected = 1
                )
            }
        }
    }
}