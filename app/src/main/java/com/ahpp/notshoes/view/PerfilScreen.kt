package com.ahpp.notshoes.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ahpp.notshoes.dataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, navControllerInicio: NavController) {

    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val usuarioLogadoPreferences = stringPreferencesKey("user_id")
    val idUsuarioFlow = remember {
        dataStore.data
            .map { preferences ->
                preferences[usuarioLogadoPreferences] ?: "-1"
            }
    }
    val idUsuario by idUsuarioFlow.collectAsState(initial = "-1")
    val scope = rememberCoroutineScope()

    Column {
        Text(text = "Perfil")
        Text(text = "Olá, seu id é $idUsuario")
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
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
        }) { Text(text = "SAIR") }
    }

}