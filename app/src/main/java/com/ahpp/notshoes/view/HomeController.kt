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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahpp.notshoes.bd.cliente.getCliente
import com.ahpp.notshoes.dataStore
import com.ahpp.notshoes.model.Cliente
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.util.funcoes.possuiConexao
import com.ahpp.notshoes.view.screensReutilizaveis.SemConexaoScreen
import com.ahpp.notshoes.view.viewsLogado.viewsCarrinho.CarrinhoScreen
import com.ahpp.notshoes.view.viewsLogado.CategoriaScreen
import com.ahpp.notshoes.view.viewsLogado.InicioScreen
import com.ahpp.notshoes.view.viewsLogado.ListaDeDesejoscreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.PerfilScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

lateinit var textoBusca: String
lateinit var categoriaSelecionada: String
lateinit var filtroPrecoSelecionado: String
lateinit var produtoSelecionado: Produto

lateinit var clienteLogado: Cliente
lateinit var dataStore: DataStore<Preferences>
val usuarioLogadoPreferences = stringPreferencesKey("user_id")

@Composable
fun HomeController(modifier: Modifier = Modifier, navControllerInicio: NavController) {

    // recuperar o id do usuario logado, e armazenar o objeto na variavel cliente
    //que vai ficar disponivel para todas as telas definidas na HomeController
    val ctx = LocalContext.current
    dataStore = ctx.dataStore
    val idUsuarioFlow = remember {
        dataStore.data
            .map { preferences ->
                preferences[usuarioLogadoPreferences] ?: "-1"
            }
    }
    val idUsuarioLogado by idUsuarioFlow.collectAsState(initial = "-1")

    var internetCheker by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    fun atualizarClienteLogado() {
        internetCheker = possuiConexao(ctx)
        if (idUsuarioLogado != "-1" && internetCheker) {
            scope.launch(Dispatchers.IO) {
                clienteLogado = getCliente(idUsuarioLogado.toInt())
            }
        }
    }

    LaunchedEffect(idUsuarioLogado) {
        atualizarClienteLogado()
    }

    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Inicio,
        BottomNavItem.Categorias,
        BottomNavItem.Carrinho,
        BottomNavItem.ListaDesejos,
        BottomNavItem.Perfil
    )

    if (internetCheker){
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
    } else {
        SemConexaoScreen (onBackPressed = {
            atualizarClienteLogado()
        })
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