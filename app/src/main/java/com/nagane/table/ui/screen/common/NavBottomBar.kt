//package com.nagane.table.ui.screen.common
//
//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Devices
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.nagane.table.R
//import com.nagane.table.ui.theme.nagane_theme_light_6
//import com.nagane.table.ui.theme.nagane_theme_main
//
//@Preview(showSystemUi = true)
//@Composable
//fun BottomNavigationPreview() {
//    BottomNavigationUI(navController = rememberNavController())
//}
//
//@Composable
//fun BottomNavigationUI(
//    navController: NavHostController,
//) {
////    val items = listOf<BottomNavItem>(
////        BottomNavItem.Home,
////        BottomNavItem.Trend,
////        BottomNavItem.Register,
////        BottomNavItem.MyPage
////    )
//
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    BottomNavigation(
//        backgroundColor = Color.Transparent,
//    ) {
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = {
//                    Icon(
//                        Icons.Default.Devices,
//                        contentDescription = null)
//                },
//                label = { Text(stringResource(id = item.title), fontSize = 12.sp) },
//                selectedContentColor = nagane_theme_main,
//                unselectedContentColor = nagane_theme_light_6,
//                selected = currentRoute == item.screenRoute.route,
//                alwaysShowLabel = true,
//                onClick = {
//                    navController.navigate(item.screenRoute.route) {
//                        // Pop up to the start destination of the graph to
//                        // avoid building up a large stack of destinations
//                        // on the back stack as users select items
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
//                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
