package com.example.nutrifind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.nutrifind.ui.navigation.NutriFindNavHost
import com.example.nutrifind.ui.theme.NutriFindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriFindTheme {

                val navController = rememberNavController()

                NutriFindNavHost(navController = navController)

            }
        }
    }
}