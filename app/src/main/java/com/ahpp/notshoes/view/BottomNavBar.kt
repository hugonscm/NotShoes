package com.ahpp.notshoes.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.view.viewsLogado.viewsCarrinho.CarrinhoScreen
import com.ahpp.notshoes.view.viewsLogado.CategoriaScreen
import com.ahpp.notshoes.view.viewsLogado.InicioScreen
import com.ahpp.notshoes.view.viewsLogado.ListaDeDesejoscreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.PerfilScreen

lateinit var textoBusca: String
lateinit var categoriaSelecionada: String
lateinit var filtroPrecoSelecionado: String
lateinit var produtoSelecionado: Produto

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navControllerInicio: NavController) {

    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Inicio,
        BottomNavItem.Categorias,
        BottomNavItem.Carrinho,
        BottomNavItem.ListaDesejos,
        BottomNavItem.Perfil
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(50.dp),
                containerColor = Color(0xFFD1E9F0),
                tonalElevation = 3.dp,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            Color.Black,
                            Color.Black,
                            Color(0xFF82D7F0)
                        ),
                        //label = { Text(screen.label, fontSize = 8.sp) },
                        alwaysShowLabel = false,
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // // evitar abrir novamente a mesma tela ao reselecionar mesmo item
                                launchSingleTop = true
                                // restaura o estado ao voltar para a tela anterior
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Inicio.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Inicio.route) { InicioScreen(modifier, navController) }
            composable(BottomNavItem.Categorias.route) { CategoriaScreen() }
            composable(BottomNavItem.Carrinho.route) { CarrinhoScreen() }
            composable(BottomNavItem.ListaDesejos.route) { ListaDeDesejoscreen() }
            composable(BottomNavItem.Perfil.route) { PerfilScreen(modifier, navControllerInicio) }
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Inicio : BottomNavItem("inicio", Icons.Default.Home, "Inicio")
    data object Categorias :
        BottomNavItem("categorias", Icons.AutoMirrored.Filled.List, "Categorias")

    data object Carrinho : BottomNavItem("carrinho", Icons.Default.ShoppingCart, "Carrinho")
    data object ListaDesejos :
        BottomNavItem("lista_desejos", Icons.Default.Favorite, "Lista de desejos")

    data object Perfil : BottomNavItem("perfil", Icons.Default.Person, "Perfil")
}