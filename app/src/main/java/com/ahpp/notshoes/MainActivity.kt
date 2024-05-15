package com.ahpp.notshoes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahpp.notshoes.util.HomeController
import com.ahpp.notshoes.view.LoginScreen
import com.ahpp.notshoes.view.RegistroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable(route = "login") {
                    LoginScreen(modifier = Modifier, navController)
                }
                composable(route = "registro") {
                    RegistroScreen(modifier = Modifier, navController)
                }
                composable(route = "homeController") {
                    HomeController(modifier = Modifier)
                }
            }
        }
    }
}

