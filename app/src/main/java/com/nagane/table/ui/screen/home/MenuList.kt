package com.nagane.table.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.R
import com.nagane.table.data.model.Menu
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_7
import com.nagane.table.ui.theme.nagane_theme_light_9
import com.nagane.table.ui.theme.nagane_theme_main

@Composable
fun MenuList(
    menuViewModel: MenuViewModel = viewModel()
) {
    val menus by menuViewModel.menus

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(menus) { menu ->
            MenuBox(
                menu = menu,
                img = when(menu.menuNo) {
                    1 -> R.drawable.cake_matcha
                    2 -> R.drawable.cake_piece
                    3 -> R.drawable.cake_hole
                    4 -> R.drawable.cake_tea
                    else -> R.drawable.macarong
                }
            )
        }
    }
}

@Composable
private fun MenuBox(
    menu: Menu,
    img: Int = R.drawable.cake_piece,
    onClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .height(340.dp),
        colors = CardDefaults.cardColors(
            containerColor = nagane_theme_light_0
        ),
        border = BorderStroke((1.5).dp, nagane_theme_main)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuImage(img)
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                    )
                    .width(180.dp),
                thickness = 2.dp,
                color = nagane_theme_main.copy(alpha = 0.5f)
            )
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                ),
                text = menu.menuName,
                style = NaganeTypography.h1,
                fontSize = 28.sp,
                color = nagane_theme_light_9
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                ),
                text = "${menu.price}원",
                style = NaganeTypography.i,
                fontSize = 24.sp,
                color = nagane_theme_light_7
            )
        }
    }
}

@Preview
@Composable
fun MenuImage(
    img: Int = R.drawable.cake_piece
) {
    Card(
        modifier = Modifier.size(200.dp),
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

@Preview
@Composable
private fun MenuBoxPreview() {
    MenuBox(menu = Menu(
        1,
        "마이쪄",
        1000,
        false
    ))
}

@Preview(showBackground = true,
        device = Devices.TABLET)
@Composable
fun MenuListPreview() {
    val dummyMenus = listOf(
        Menu(menuNo = 1, menuName = "Chocolate Cake", price = 5000, soldOut = false),
        Menu(menuNo = 2, menuName = "Vanilla Ice Cream", price = 3000, soldOut = true),
        Menu(menuNo = 3, menuName = "Strawberry Shortcake", price = 4500, soldOut = false),
        Menu(menuNo = 4, menuName = "Cheesecake", price = 4000, soldOut = true),
        Menu(menuNo = 5, menuName = "Macaron", price = 2000, soldOut = false)
    )

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(dummyMenus) { menu ->
            MenuBox(
                menu = menu,
                img = when(menu.menuNo) {
                    1 -> R.drawable.cake_matcha
                    2 -> R.drawable.cake_piece
                    3 -> R.drawable.cake_hole
                    4 -> R.drawable.cake_tea
                    else -> R.drawable.macarong
                }
            )
        }
    }
}