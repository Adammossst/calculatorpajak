package com.adamchaniago0025.calculatorpajak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adamchaniago0025.calculatorpajak.navigation.SetupNavGraph
import com.adamchaniago0025.calculatorpajak.ui.theme.CalculatorpajakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorpajakTheme {
                SetupNavGraph()
            }
        }
    }
}