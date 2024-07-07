package com.nagane.table.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdfScanner
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Composable
fun CustomBottomBar(
    onClickBill: () -> Unit = {},
    onClickGoBasket: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(nagane_theme_main),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomButton(
            Icons.Filled.AdfScanner,
            R.string.bottom_bill,
            onClickBill
        )
        BottomButton(
            Icons.Filled.ShoppingCartCheckout,
            R.string.bottom_basket,
            onClickGoBasket
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun BottomButton(
    icon : ImageVector = Icons.Filled.AdfScanner,
    text : Int = R.string.bottom_bill,
    onClick : () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(
                vertical = 16.dp,
                horizontal = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = nagane_theme_sub
        )
    ) {
        Box(
            modifier = Modifier
                .padding(
                    16.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon,
                    contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = text),
                    style = NaganeTypography.h1,
                    color = nagane_theme_main
                )
            }
        }
    }
}

@Preview(
    device = Devices.TABLET)
@Composable
fun CustomBottomBarPreview() {
    NaganeTableTheme {
        Surface {
            CustomBottomBar()
        }
    }
}