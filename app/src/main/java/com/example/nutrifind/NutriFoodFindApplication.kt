package com.example.nutrifind

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.nutrifind.ui.MainViewModel
import com.example.nutrifind.ui.navigation.NutriFindNavHost
import com.example.nutrifind.ui.theme.NutriFindTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NutriFoodFindApplication : Application()