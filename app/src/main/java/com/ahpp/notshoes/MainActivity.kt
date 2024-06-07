package com.ahpp.notshoes

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahpp.notshoes.view.HomeController
import com.ahpp.notshoes.view.viewsDeslogado.LoginScreen
import com.ahpp.notshoes.view.viewsDeslogado.RegistroScreen

//usado para salvar o id do usuario logado, precisa ser definido no level mais alto do projeto
// foi usado la no LoginScreen.kt
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_id_logado")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
                    HomeController(modifier = Modifier, navController)
                }
            }

        }
    }
}