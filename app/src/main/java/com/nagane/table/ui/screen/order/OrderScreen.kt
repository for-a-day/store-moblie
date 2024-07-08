package com.nagane.table.ui.screen.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
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
                        .weight(1f)
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
    val scrollState = rememberScrollState()

    val context = LocalContext.current

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
        // Spacer(modifier = Modifier.height(360.dp))
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
fun PaymentMethodCheck(
    modifier : Modifier = Modifier,
) {
    Column(
        modifier = modifier
            // .width(600.dp)
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(id = R.string.payment_method_select),
            style = NaganeTypography.h1,
            fontSize = 32.sp,
            color = nagane_theme_sub
        )
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


// 선택한 건 음영 효과에 밑은 main color로 대신 음영 있으니까 대비 가능 굿~
@Preview
@Composable
fun MethodBox() {
    Card(
        modifier = Modifier
            .size(200.dp),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(nagane_theme_sub)
                    .align(Alignment.BottomCenter),
            ) {

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