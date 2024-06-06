package com.ahpp.notshoes.view.viewsLogado.viewsPerfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ahpp.notshoes.R
import com.ahpp.notshoes.view.clienteLogado
import com.ahpp.notshoes.view.dataStore
import com.ahpp.notshoes.view.usuarioLogadoPreferences
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSeusDados.SeusDadosScreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSobre.SobreScreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos.EnderecosScreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsPedidos.PedidosScreen
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, navControllerInicio: NavController) {

    var clickedPedidos by remember { mutableStateOf(false) }
    var clickedSeusDados by remember { mutableStateOf(false) }
    var clickedEnderecos by remember { mutableStateOf(false) }
    var clickedSobre by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    if (clickedPedidos) {
        PedidosScreen(onBackPressed = { clickedPedidos = false })
    } else if (clickedSeusDados) {
        SeusDadosScreen(onBackPressed = { clickedSeusDados = false })
    } else if (clickedEnderecos) {
        EnderecosScreen(onBackPressed = { clickedEnderecos = false })
    } else if (clickedSobre) {
        SobreScreen(onBackPressed = { clickedSobre = false })
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.White)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFF029CCA)).padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .width(270.dp),
                    text = "Olá ${clienteLogado.nome} :)", fontSize = 20.sp, maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        Color(0xFFFFFFFF)
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable(
                            enabled = true,
                            onClick = {
                                scope.launch {
                                    dataStore.edit { preferences ->
                                        preferences[usuarioLogadoPreferences] = "-1"
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
                                }
                            }
                        ),
                    text = "Sair",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        Color(0xFFFFFFFF)
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(true, onClick = {clickedPedidos = true}),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.Start,
                        Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_attach_money_24),
                            contentDescription = null,
                        )
                        Text(
                            text = "Pedidos",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(true, onClick = { clickedSeusDados = true }),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.Start,
                        Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = null,
                        )
                        Text(
                            text = "Seus dados",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(true, onClick = { clickedEnderecos = true }),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.Start,
                        Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_home_24),
                            contentDescription = null,
                        )
                        Text(
                            text = "Endereços",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable(true, onClick = { clickedSobre = true }),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.Start,
                        Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_info_24),
                            contentDescription = null,
                        )
                        Text(
                            text = "Sobre o aplicativo",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}