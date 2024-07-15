package com.nagane.table.ui.screen.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdfScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.R
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.screen.home.MenuViewModel
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main


@Preview
@Composable
fun CustomModalDrawerContent(
    drawerWidth: Dp = 480.dp,
    nowCase: String = "menu",
    closeDrawer: () -> Unit = {},
    onPaymentPage: () -> Unit = {},
    cartViewModel: CartViewModel = viewModel(),
    menuViewModel: MenuViewModel = viewModel(),
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth),
        drawerContainerColor = nagane_theme_main,
    ) {
        when(nowCase) {
            "menu" -> MenuDrawerContent(
                            menuViewModel = menuViewModel,
                            closeDrawer = closeDrawer,
                            cartViewModel = cartViewModel
                        )
            else -> CartDrawerContent(
                closeDrawer = closeDrawer,
                onPaymentPage = onPaymentPage,
                cartViewModel = cartViewModel
            )
        }

    }
}

@Composable
fun DrawerContentButton(
    icon : ImageVector = Icons.Filled.AdfScanner,
    iconColor: Color = LocalContentColor.current,
    text : Int = R.string.bottom_bill,
    textColor : Color = nagane_theme_main,
    onClick : () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier : Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon,
            contentDescription = null,
            tint = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = text),
            style = NaganeTypography.h1,
            color = textColor
        )
    }
}

