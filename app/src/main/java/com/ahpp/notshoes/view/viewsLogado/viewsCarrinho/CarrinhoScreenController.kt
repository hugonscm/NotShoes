package com.ahpp.notshoes.view.viewsLogado.viewsCarrinho

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahpp.notshoes.util.viewModel.CarrinhoViewModel
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos.CadastrarEnderecoScreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSeusDados.AtualizarDadosPessoaisScreen

@Composable
fun CarrinhoScreenController() {
    val carrinhoViewModel: CarrinhoViewModel = viewModel()
    val navControllerCarrinho = rememberNavController()
    NavHost(navController = navControllerCarrinho, startDestination = "carrinhoScreen") {
        composable(route = "carrinhoScreen") {
            CarrinhoScreen(navControllerCarrinho, carrinhoViewModel)
        }
        composable(route = "finalizarPedidoScreen") {
            FinalizarPedidoScreen(navControllerCarrinho, carrinhoViewModel)
        }
        composable(route = "compraFinalizadaScreen") {
            CompraFinalizadaScreen(navControllerCarrinho)
        }
        composable(route = "atualizarDadosPessoaisScreen") {
            AtualizarDadosPessoaisScreen(navControllerCarrinho)
        }
        composable(route = "cadastrarEnderecoScreen") {
            CadastrarEnderecoScreen(navControllerCarrinho)
        }
    }
}