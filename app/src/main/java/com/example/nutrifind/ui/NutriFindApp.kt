package com.example.nutrifind.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.nutrifind.ui.navigation.NutriFindNavHost
import com.example.nutrifind.ui.theme.NutriFindTheme

@Composable
fun NutriFindApp(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isDarkTheme by mainViewModel.isDarkTheme.collectAsState(initial = false)

    val navController = rememberNavController()

    NutriFindTheme(darkTheme = isDarkTheme) {
        NutriFindNavHost(navController = navController)
    }
}