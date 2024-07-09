package com.nagane.table.ui.screen.order

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AdfScanner
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.data.model.Cart
import com.nagane.table.data.model.CartCreateDto
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.screen.home.components.DrawerContentButton
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel(),
) {
    val carts by cartViewModel.cartItems
    var totalPrice by remember { mutableIntStateOf(carts.sumOf { it.price * it.quantity }) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            cartViewModel.fetchCartItems()
        }
    }

    LaunchedEffect(carts) {
        totalPrice = carts.sumOf { it.price * it.quantity }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = stringResource(id = R.string.order_title),
                backgroundColor = nagane_theme_sub,
                subColor = nagane_theme_main
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(nagane_theme_main)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderContainer(
                    carts = carts,
                    totalPrice = totalPrice
                )
                Spacer(modifier = Modifier.width(64.dp))
                PaymentMethodCheck(
                    modifier = Modifier
                        .weight(1f),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun OrderContainer(
    totalPrice : Int = 0,
    carts: List<Cart>,
) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(600.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke((2).dp, nagane_theme_sub)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(
                id = R.drawable.nagane_dark_b),
                contentDescription = null,
                alpha = 0.3f
            )
            OrderContent(carts)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(nagane_theme_sub)
                    .align(Alignment.BottomCenter),
            ) {
                TotalPriceBox(
                    totalPrice = totalPrice
                )
            }
        }
    }
}

@Composable
fun OrderContent(
    carts : List<Cart>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
           text = stringResource(id = R.string.order_check_title),
            style = NaganeTypography.h1,
            fontSize = 32.sp,
            color = nagane_theme_sub
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 80.dp)
        ) {
            items(carts) { cart ->
                OrderBox(cart = cart)
            }
        }
    }
}

@Composable
fun OrderBox(
    cart : Cart
) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cart.menuName,
                style = NaganeTypography.h2,
                color = nagane_theme_sub,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${cart.quantity} 개",
                style = NaganeTypography.h2,
                color = nagane_theme_sub,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${cart.price}원",
                style = NaganeTypography.h2,
                color = nagane_theme_sub,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 16.dp),
            thickness = 2.dp,
            color = nagane_theme_sub
        )
    }
}

@Composable
fun TotalPriceBox(
    totalPrice : Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.payment_total_price),
            style = NaganeTypography.h1,
            fontSize = 28.sp,
            color = nagane_theme_main
        )
        Text(
            text = "$totalPrice 원",
            style = NaganeTypography.h1,
            fontSize = 28.sp,
            color = nagane_theme_main
        )
    }
}


@Composable
fun PaymentMethodCheck(
    modifier : Modifier = Modifier,
    navController : NavController
) {
    var paymentCase by rememberSaveable {
        mutableIntStateOf(1)
    }
    var dineCase by rememberSaveable {
        mutableIntStateOf(0)
    }

    Column(
        modifier = modifier
            .fillMaxHeight(),
//            .padding(
//                vertical = 16.dp
//            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        ) {
            Text(
                text = stringResource(id = R.string.payment_method_select),
                style = NaganeTypography.h3,
                fontSize = 28.sp,
                color = nagane_theme_sub
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                thickness = 2.dp,
                color = nagane_theme_sub
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MethodBox(
                    icon = Icons.Filled.CreditCard,
                    text = R.string.payment_card,
                    isSelected = (paymentCase == 1),
                    onClick = {
                        paymentCase = 1
                    }
                )
                MethodBox(
                    icon = Icons.Filled.Payments,
                    text = R.string.payment_cash,
                    isSelected = (paymentCase == 2),
                    onClick = {
//                        paymentCase = if (paymentCase == 2) {
//                            0
//                        } else {
//                            2
//                        }
                    },
                    isDisabled = true
                )
            }
        }
        // Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.eat_method_select),
                style = NaganeTypography.h3,
                fontSize = 28.sp,
                color = nagane_theme_sub,
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                thickness = 2.dp,
                color = nagane_theme_sub
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MethodBox(
                    icon = Icons.Filled.Dining,
                    text = R.string.dine_in,
                    isSelected = (dineCase == 1),
                    onClick = {
                        dineCase = 1
                    }
                )
                MethodBox(
                    icon = Icons.Filled.ShoppingBasket,
                    text = R.string.pickup,
                    isSelected = (dineCase == 2),
                    onClick = {
                        dineCase = 2
                    }
                )
            }
        }

        // Spacer(modifier = Modifier.height(60.dp))
        OrderBottomButtonRow(
            paymentEnable = ((paymentCase == 1 || paymentCase == 2) && (dineCase == 1 || dineCase == 2)),
            navController = navController,
            paymentCase = paymentCase,
            dineCase = dineCase
        )
    }
}

@Composable
fun OrderBottomButtonRow(
    paymentEnable: Boolean = false,
    navController: NavController,
    cartViewModel: CartViewModel = viewModel(),
    paymentCase: Int,
    dineCase: Int
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        PaymentDialog(
            onDismiss = {
                showDialog = false
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Order.route) { inclusive = true }
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        DrawerContentButton(
            modifier = Modifier
                .width(160.dp)
                .height(80.dp)
                .background(nagane_theme_sub),
            icon = Icons.Filled.Close,
            text = R.string.cancel,
            onClick = {
                navController.popBackStack()
            }
        )
        Box(
            modifier = Modifier
                .height(80.dp)
                .width(2.dp)
                .background(nagane_theme_main)
        )
        DrawerContentButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(if (paymentEnable) nagane_theme_sub else nagane_theme_light_6.copy(alpha = 0.75f)),
            icon = Icons.Filled.Paid,
            iconColor = if (paymentEnable) LocalContentColor.current else nagane_theme_light_0.copy(alpha = 0.5f),
            text = R.string.go_payment,
            textColor = if (paymentEnable) nagane_theme_main else nagane_theme_light_0.copy(alpha = 0.5f),
            onClick = {
                if (paymentEnable) {
                    showDialog = true
                    cartViewModel.createOrder(
                        paymentCase = paymentCase,
                        dineCase = dineCase
                    )
                }
            }
        )

    }
}

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
            else nagane_theme_sub)
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
                    else nagane_theme_main),
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

@Preview(
    device = Devices.TABLET)
@Composable
fun OrderScreenPreview() {
    NaganeTableTheme {
        Surface {
            OrderScreen(navController = rememberNavController())
        }
    }
}