package com.adamchaniago0025.calculatorpajak.navigation

sealed class Screen (val route : String) {
    data object Home : Screen("MainScreen")
    data object About : Screen("About Screen")
}