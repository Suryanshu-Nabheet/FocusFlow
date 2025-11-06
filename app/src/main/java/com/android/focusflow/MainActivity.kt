package com.android.focusflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.focusflow.navigation.FocusFlowNavHost
import com.android.focusflow.navigation.Screen
import com.android.focusflow.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusFlowTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkGray,
                            contentColor = PureWhite
                        ) {
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Note,
                                        contentDescription = "Notes"
                                    )
                                },
                                label = { Text("Notes") },
                                selected = currentRoute == Screen.Notes.route,
                                onClick = {
                                    navController.navigate(Screen.Notes.route) {
                                        popUpTo(Screen.Notes.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = ElectricBlue,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue.copy(alpha = 0.2f),
                                    unselectedIconColor = LightGray,
                                    unselectedTextColor = LightGray
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Tasks"
                                    )
                                },
                                label = { Text("Tasks") },
                                selected = currentRoute == Screen.Tasks.route,
                                onClick = {
                                    navController.navigate(Screen.Tasks.route) {
                                        popUpTo(Screen.Notes.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = ElectricBlue,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue.copy(alpha = 0.2f),
                                    unselectedIconColor = LightGray,
                                    unselectedTextColor = LightGray
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = "Calendar"
                                    )
                                },
                                label = { Text("Calendar") },
                                selected = currentRoute == Screen.Calendar.route,
                                onClick = {
                                    navController.navigate(Screen.Calendar.route) {
                                        popUpTo(Screen.Notes.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = ElectricBlue,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue.copy(alpha = 0.2f),
                                    unselectedIconColor = LightGray,
                                    unselectedTextColor = LightGray
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Timer,
                                        contentDescription = "Timer"
                                    )
                                },
                                label = { Text("Timer") },
                                selected = currentRoute == Screen.Timer.route,
                                onClick = {
                                    navController.navigate(Screen.Timer.route) {
                                        popUpTo(Screen.Notes.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = ElectricBlue,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue.copy(alpha = 0.2f),
                                    unselectedIconColor = LightGray,
                                    unselectedTextColor = LightGray
                                )
                            )
                        }
                    },
                    containerColor = DarkBlack
                ) { paddingValues ->
                    FocusFlowNavHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}
