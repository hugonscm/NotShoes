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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.dataStore
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.view.viewsDeslogado.clienteLogado
import com.ahpp.notshoes.view.viewsDeslogado.usuarioLogadoPreferences
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(navControllerLogin: NavController, navControllerPerfil: NavHostController) {

    val ctx = LocalContext.current

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
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
                .background(azulEscuro)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .width(270.dp),
                text = "Olá ${clienteLogado.nome} :)", fontSize = 20.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            scope.launch {
                                ctx.dataStore.edit { preferences ->
                                    preferences[usuarioLogadoPreferences] = "-1"
                                }
                                navControllerLogin.navigate("login") {
                                    popUpTo(navControllerLogin.graph.findStartDestination().id) {
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
                color = Color.White
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
                    .clickable(true, onClick = {
                        navControllerPerfil.navigate("pedidosScreen") {
                            launchSingleTop = true
                        }
                    }),
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
                    .clickable(true, onClick = {
                        navControllerPerfil.navigate("seusDadosScreen") {
                            launchSingleTop = true
                        }
                    }),
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
                    .clickable(true, onClick = {
                        navControllerPerfil.navigate("enderecosScreen") {
                            launchSingleTop = true
                        }
                    }),
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
                    .clickable(true, onClick = {
                        navControllerPerfil.navigate("sobreAppScreen") {
                            launchSingleTop = true
                        }
                    }),
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