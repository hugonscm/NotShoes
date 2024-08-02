package com.ahpp.notshoes.constantes

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ahpp.notshoes.model.Cliente
import com.ahpp.notshoes.ui.theme.azulEscuro

//variavel que mantem o objeto cliente logado
lateinit var clienteLogado: Cliente

//key para acessar o id do usuario no datastore
val usuarioLogadoPreferences = stringPreferencesKey("user_id")

//cores para os campos de texto
object CustomTextFieldColors {
    @Composable
    fun colorsTextFields() = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color(0xFFEEF3F5),
        focusedContainerColor = Color(0xFFEEF3F5),
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        unfocusedBorderColor = Color(0xFFEEF3F5),
        focusedBorderColor = azulEscuro,
        focusedLabelColor = Color.Black,
        errorContainerColor = Color(0xFFEEF3F5),
        cursorColor = azulEscuro,
    )
}
