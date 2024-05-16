package com.ahpp.notshoes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ahpp.notshoes.bd.ClienteRepository
import com.ahpp.notshoes.dataStore
import com.ahpp.notshoes.model.Cliente
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, navControllerInicio: NavController) {

    //tenta transformar essa recuperaçao de idlogado em uma funcao
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val usuarioLogadoPreferences = stringPreferencesKey("user_id")
    val idUsuarioFlow = remember {
        dataStore.data
            .map { preferences ->
                preferences[usuarioLogadoPreferences] ?: "-1"
            }
    }
    val idUsuarioLogado by idUsuarioFlow.collectAsState(initial = "-1")
    val scope = rememberCoroutineScope()

    val cliente: Cliente
    if (idUsuarioLogado != "-1") {
        val repository = ClienteRepository()
        cliente = repository.getCliente(idUsuarioLogado.toInt())
    } else {
        cliente = Cliente(
            idCliente = -1,
            genero = "",
            nome = "Usuário",
            email = "",
            senha = "",
            cpf = "",
            idEndereco = -1,
            idListaDesejos = -1,
            idCarrinho = -1
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF029CCA)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)
                    .width(270.dp),
                text = "Olá ${cliente.nome} :)", fontSize = 20.sp, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
            Text(
                modifier = Modifier
                    .padding(top = 15.dp, end = 10.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            scope.launch {
                                dataStore.edit { preferences ->
                                    preferences[usuarioLogadoPreferences] = "-1"
                                }
                            }
                            navControllerInicio.navigate("login") {
                                popUpTo(navControllerInicio.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                // // evitar abrir novamente a mesma tela ao reselecionar mesmo item
                                launchSingleTop = true
                                // restaura o estado ao voltar para a tela anterior
                                restoreState = false
                            }
                        }),
                text = "Sair",
                fontSize = 20.sp,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(10.dp)
        ) {
            Text(text = "Id cliente: ${cliente.idCliente}")
            Text(text = "Genero: ${cliente.genero}")
            Text(text = "Nome: ${cliente.nome}")
            Text(text = "Email: ${cliente.email}")
            Text(text = "Senha: ${cliente.senha}")
            Text(text = "Cpf: ${cliente.cpf}")
            Text(text = "Id endereco: ${cliente.idEndereco}")
            Text(text = "Id Lista desejos: ${cliente.idListaDesejos}")
            Text(text = "Id Carrinho: ${cliente.idCarrinho}")
        }
    }
}