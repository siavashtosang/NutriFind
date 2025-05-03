package com.example.nutrifind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.nutrifind.ui.NutriFindApp
import dagger.hilt.android.AndroidEntryPoint


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