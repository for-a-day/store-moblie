package com.nagane.table.ui.screen.home.components

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.R
import com.nagane.table.data.model.Cart
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_4
import com.nagane.table.ui.theme.nagane_theme_light_7
import com.nagane.table.ui.theme.nagane_theme_light_9
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun CartDrawerContent(
    closeDrawer: () -> Unit = {},
    cartViewModel: CartViewModel = viewModel(),
    onPaymentPage: () -> Unit = {}
) {
    val carts by cartViewModel.cartItems

    var nowTotalPrice by remember { mutableIntStateOf(carts.sumOf { it.price * it.quantity }) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(carts) {
        nowTotalPrice = carts.sumOf { it.price * it.quantity }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                text = stringResource(id = R.string.cart_title),
                style = NaganeTypography.b,
                fontSize = 24.sp,
                color = nagane_theme_sub,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            CartList(
                scrollState = scrollState,
                carts = carts,
                onChangeTotalPrice = { price ->
                    nowTotalPrice += price
                }
            )
            Spacer(modifier = Modifier.height(360.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(nagane_theme_main),
            ) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(80.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.total_price),
                        style = NaganeTypography.h1,
                        color = nagane_theme_sub
                    )
                }
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(2.dp)
                        .background(nagane_theme_main)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$nowTotalPrice 원",
                        style = NaganeTypography.h1,
                        color = nagane_theme_sub
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DrawerContentButton(
                    modifier = Modifier
                        .width(160.dp)
                        .height(80.dp)
                        .background(nagane_theme_sub),
                    icon = Icons.Filled.Close,
                    text = R.string.close,
                    onClick = {
                        closeDrawer()
                        scope.launch {
                            scrollState.scrollTo(0)
                        }
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
                        .background(nagane_theme_sub),
                    icon = Icons.Filled.AddCard,
                    text = R.string.go_charge,
                    onClick = {
                        if (carts.isEmpty()) {
                            Toast.makeText(
                                context,
                                R.string.empty_cart,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            closeDrawer()
                            scope.launch {
                                scrollState.scrollTo(0)
                            }
                            onPaymentPage()
                        }
                    }
                )

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CartList(
    cartViewModel: CartViewModel = viewModel(),
    carts : List<Cart>,
    scrollState : ScrollState = rememberScrollState(),
    onChangeTotalPrice : (Int) -> Unit = {}
) {

    if (carts.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Cancel,
                contentDescription = null,
                tint = nagane_theme_sub.copy(alpha = 0.75f),
                modifier = Modifier.size(100.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.empty_cart),
                style = NaganeTypography.h1,
                fontSize = 22.sp,
                color = nagane_theme_sub,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
            ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 120.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                maxItemsInEachRow = 1
            ) {
                carts.map {  cart ->
                    CartBox(
                        cart,
                        onDelete = {
                            cartViewModel.deleteCartMenu(cart.cartNo)
                        },
                        changeQuantity = { isIncre: Boolean ->
                            var changedQuantity = cart.quantity

                            if (isIncre) {
                                changedQuantity += 1
                                onChangeTotalPrice(cart.price)
                            } else {
                                changedQuantity -= 1
                                onChangeTotalPrice(-cart.price)
                            }
                            cartViewModel.updateCartQuantity(cart.cartNo, changedQuantity)
                            cart.quantity = changedQuantity
                        }
                    )
                }

            }
        }
    }

}

@Composable
private fun CartBox(
    cart: Cart,
    onDelete: () -> Unit = {},
    changeQuantity: (Boolean) -> Unit = {}
) {
    var nowQuality by remember { mutableIntStateOf(cart.quantity) }

    Box(
        contentAlignment = Alignment.TopEnd,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = nagane_theme_sub
            ),
            border = BorderStroke((1.5).dp, nagane_theme_main)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
                        )
                        .width(180.dp),
                    thickness = 4.dp,
                    color = nagane_theme_main.copy(alpha = 0.5f)
                )
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = cart.menuName,
                        style = NaganeTypography.h1,
                        fontSize = 24.sp,
                        color = nagane_theme_light_9,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu_price),
                            style = NaganeTypography.b,
                            fontSize = 20.sp,
                            color = nagane_theme_light_7,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${cart.price}원",
                            style = NaganeTypography.p,
                            fontSize = 20.sp,
                            color = nagane_theme_light_7,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        QuantityBlock(false, onClick = {
                            changeQuantity(false)
                            nowQuality -= 1
                        }, cart.quantity)
                        Text(
                            text = "${nowQuality}개",
                            style = NaganeTypography.h2,
                            fontSize = 20.sp,
                            color = nagane_theme_light_7,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        QuantityBlock(true, onClick = {
                            changeQuantity(true)
                            nowQuality += 1
                        }, cart.quantity)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
        DeleteBtn(onClick = onDelete)
    }
}

@Preview
@Composable
fun QuantityBlock(
    isIncre: Boolean = true,
    onClick: () -> Unit = {},
    nowQuantity: Int = 0
) {
    Card(
        modifier = Modifier
            .size(40.dp)
            .clickable(
                onClick = onClick,
                enabled = !(!isIncre && nowQuantity <= 1)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (!isIncre && nowQuantity <= 1) {
                        nagane_theme_light_4
                    } else {
                        nagane_theme_main
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isIncre) Icons.Filled.Add else Icons.Filled.Remove,
                contentDescription = null,
                tint = nagane_theme_sub)
        }
    }
}

@Composable
fun DeleteBtn(
    onClick: () -> Unit = {},
) {
    Icon(
        modifier = Modifier
            .offset(
                x = (-36).dp,
                y = 25.dp)
            .size(32.dp)
            .clickable(
                onClick = onClick
            ),
        imageVector = Icons.Filled.RemoveCircle,
        contentDescription = stringResource(id = R.string.remove_cart),
        tint = nagane_theme_main
    )
}