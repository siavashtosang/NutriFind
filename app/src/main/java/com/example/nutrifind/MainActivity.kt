package com.example.nutrifind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.nutrifind.data.nutri_find_repository.repository.NutriFindRepository
import com.example.nutrifind.data.nutri_find_repository.repository.UserPreferences
import com.example.nutrifind.ui.NutriFindApp
import com.example.nutrifind.ui.navigation.NutriFindNavHost
import com.example.nutrifind.ui.theme.NutriFindTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

           NutriFindApp()
        }
    }
}