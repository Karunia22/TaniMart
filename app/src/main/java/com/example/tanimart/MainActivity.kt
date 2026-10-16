package com.example.tanimart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tanimart.data.network.TokenStorage
import com.example.tanimart.ui.home.HomeScreen
import com.example.tanimart.ui.navigation.AppNavigation
import com.example.tanimart.ui.theme.TaniMartTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        TokenStorage.init(applicationContext)

        enableEdgeToEdge()

        setContent {
            TaniMartTheme {
                AppNavigation()
            }
        }
    }
}