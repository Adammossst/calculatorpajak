package com.adamchaniago0025.calculatorpajak.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adamchaniago0025.calculatorpajak.ui.screen.AboutScreen
import com.adamchaniago0025.calculatorpajak.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navcontroller: NavHostController = rememberNavController()) {
    NavHost(
        navController = navcontroller,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            MainScreen(navcontroller)
        }
        composable(Screen.About.route) {
            AboutScreen(navcontroller)
        }

    }
}