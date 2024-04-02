package com.foods.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.foods.R
import com.foods.ui.theme.FoodsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodsTheme(
                darkTheme = false,
                dynamicColor = false
            ) {
                val systemUIController = rememberSystemUiController()
                val navigationBarColor = colorResource(id = R.color.light_gray)
                val statusBarColor =
                    if (isSystemInDarkTheme()) Color.DarkGray
                    else colorResource(id = R.color.gray_white)
                val isDarkTheme = isSystemInDarkTheme()

                SideEffect {
                    systemUIController.setNavigationBarColor(
                        color = navigationBarColor,
                        navigationBarContrastEnforced = false,
                    )
                    systemUIController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = !isDarkTheme
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = colorResource(id = R.color.gray_white)
                ) {
                    MainScreen()
                }
            }
        }
    }
}