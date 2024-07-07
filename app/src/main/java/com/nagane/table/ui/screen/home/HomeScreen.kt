package com.nagane.table.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.home.components.CategoryRow
import com.nagane.table.ui.screen.home.components.CustomBottomBar
import com.nagane.table.ui.screen.home.components.CustomModalDrawerContent
import com.nagane.table.ui.screen.home.components.HomeAppBar
import com.nagane.table.ui.screen.home.components.MenuList
import com.nagane.table.ui.theme.NaganeTableTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
)
{

    var nowCase by rememberSaveable {
        mutableStateOf("menu")
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerWidth = if (drawerState.isOpen) 480.dp else 0.dp

    val scope = rememberCoroutineScope()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // Drawer 기존 왼쪽에서 오른쪽으로 열리도록 조치
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        CustomModalDrawerContent(
                            drawerWidth,
                            nowCase,
                            closeDrawer = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                },
                gesturesEnabled = true
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    HomeContent(
                        navController = navController,
                        onClickGoCart = {
                            scope.launch {
                                cartViewModel.fetchCartItems()
                                nowCase = "cart"
                                delay(100L)
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                        onClickMenu = {
                            nowCase = "menu"
                            scope.launch {
                                delay(100L)
                                drawerState.open()
                            }
                        }
                    )
                }
            }
        }
    }

}


@Composable
private fun HomeContent(
    onClickBill: () -> Unit = {},
    onClickGoCart: () -> Unit = {},
    menuViewModel: MenuViewModel = viewModel(),
    navController: NavController,
    onClickMenu: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeAppBar(
                navController = navController,
                title = menuViewModel.tableName ?: "확인불가",
                tableNumber = (menuViewModel.tableNumber ?: "-1") + "번"
            )
        },
        bottomBar = {
            CustomBottomBar(
                onClickBill = onClickBill,
                onClickGoCart = onClickGoCart,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 64.dp)
            ) {
                CategoryRow()

                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(
                        id = R.drawable.nagane_light_b),
                        contentDescription = null,
                        alpha = 0.3f
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        MenuList(
                            onClick = { menuNo ->
                                menuViewModel.fetchMenuDetail(menuNo)

                                onClickMenu()
                            }
                        )
                    }
                }
            }
        }
    }
}


//@Preview(
//    device = Devices.TABLET)
//@Composable
//fun HomeScreenPreview() {
//    NaganeTableTheme {
//        Surface {
//            HomeScreen(navController = rememberNavController())
//        }
//    }
//}

@Preview(
    device = Devices.TABLET)
@Composable
fun HomeFramePreview() {
    NaganeTableTheme {
        Surface {
            Scaffold(
                topBar = {
                    HomeAppBar(navController = rememberNavController())
                },
                bottomBar = {
                    CustomBottomBar()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                }
            }
        }
    }
}