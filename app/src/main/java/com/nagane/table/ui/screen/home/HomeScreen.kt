package com.nagane.table.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.MoveAdminButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    menuViewModel: MenuViewModel = viewModel())
{
    ///List of Navigation Items that will be clicked

    val items = listOf(
        NavigationItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItems(
            title = "Info",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        NavigationItems(
            title = "Edit",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            badgeCount = 105
        ),
        NavigationItems(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    //Remember Clicked index state
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp)) //space (margin) from top
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            //  navController.navigate(item.route)

                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {  // Show Badge
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
                    )
                }

            }
        },
        gesturesEnabled = true
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
                    onClickGoBasket = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeContent()
            }

        }
    }
}


@Composable
private fun HomeContent() {
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
                // MenuList()
                MenuListPreview()
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