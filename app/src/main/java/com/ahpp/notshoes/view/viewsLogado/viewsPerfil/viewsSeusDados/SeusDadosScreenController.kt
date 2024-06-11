package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSeusDados

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SeusDadosScreenController(navControllerPerfil: NavController) {
    val navControllerSeusDados = rememberNavController()
    NavHost(navController = navControllerSeusDados, startDestination = "seusDadosScreen") {

        composable(route = "seusDadosScreen") {
            SeusDadosScreen(navControllerPerfil, navControllerSeusDados)
        }
        composable(route = "atualizarDadosPessoaisScreen") {
            AtualizarDadosPessoaisScreen(navControllerSeusDados)
        }
        composable(route = "atualizarEmailScreen") {
            AtualizarEmailScreen(navControllerSeusDados)
        }
        composable(route = "atualizarSenhaScreen") {
            AtualizarSenhaScreen(navControllerSeusDados)
        }
    }
}