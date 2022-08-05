package com.tzmax.abacus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import com.tzmax.abacus.ui.theme.AbacusTheme
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tzmax.abacus.ui.model.AbacusViewModel
import com.tzmax.abacus.ui.screens.AppView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val viewModel: AbacusViewModel by viewModels()
        setContent {
            val systemUiController = rememberSystemUiController()
            AbacusTheme {
                systemUiController.setStatusBarColor(Color.Transparent, true)
                AppView(viewModel)
            }
        }
    }
}
