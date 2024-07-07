package com.nagane.table.ui.screen.home.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.R
import com.nagane.table.data.model.CartCreateDto
import com.nagane.table.ui.screen.home.MenuViewModel
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.launch

@Composable
fun MenuDrawerContent(
    menuViewModel: MenuViewModel = viewModel(),
    closeDrawer: () -> Unit = {},
    cartViewModel: CartViewModel = viewModel()
) {
    val menu by menuViewModel.menuDetail
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                text = menu.menuName,
                style = NaganeTypography.b,
                fontSize = 32.sp,
                // color = nagane_theme_light_9,
                color = nagane_theme_sub,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            MenuImage()
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.price_title),
                style = NaganeTypography.b,
                fontSize = 24.sp,
                // color = nagane_theme_light_9,
                color = nagane_theme_sub,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${menu.price}원",
                style = NaganeTypography.i,
                fontSize = 24.sp,
                // color = nagane_theme_light_9,
                color = nagane_theme_sub,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.description_title),
                style = NaganeTypography.b,
                fontSize = 24.sp,
                // color = nagane_theme_light_9,
                color = nagane_theme_sub,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menu.description,
                style = NaganeTypography.i,
                fontSize = 24.sp,
                // color = nagane_theme_light_9,
                color = nagane_theme_sub,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(120.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(nagane_theme_main)
            )
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
                    icon = Icons.Filled.AddShoppingCart,
                    text = R.string.add_cart,
                    onClick = {
                        closeDrawer()
                        scope.launch {
                            scrollState.scrollTo(0)
                        }
                        cartViewModel.addCart(
                            CartCreateDto(
                                menuNo = menu.menuNo,
                                menuName = menu.menuName,
                                price = menu.price
                            ),
                            onResult = { result ->
                                val toastResult = if (result) "장바구니에 메뉴가 추가됐습니다." else "오류가 발생했습니다"
                                Toast.makeText(
                                    context,
                                    toastResult,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )

                    }
                )

            }
        }
    }
}

@Preview
@Composable
private fun MenuImage(
    img: Int = R.drawable.cake_piece,
) {
    Card(
        modifier = Modifier.size(280.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = img),
            contentDescription = null)
    }
}