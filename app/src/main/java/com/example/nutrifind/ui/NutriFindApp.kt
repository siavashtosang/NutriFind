package com.example.nutrifind.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.nutrifind.ui.navigation.NutriFindNavHost
import com.example.nutrifind.ui.theme.NutriFindTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NutriFindApp(
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val isDarkTheme by mainViewModel.isDarkTheme.collectAsState()

    val navController = rememberNavController()

    NutriFindTheme(darkTheme = isDarkTheme) {
        NutriFindNavHost(navController = navController)
    }

}