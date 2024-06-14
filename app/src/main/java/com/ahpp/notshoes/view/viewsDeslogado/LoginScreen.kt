package com.ahpp.notshoes.view.viewsDeslogado

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.data.LoginCliente
import com.ahpp.notshoes.data.cliente.getCliente
import com.ahpp.notshoes.dataStore
import com.ahpp.notshoes.model.Cliente
import com.ahpp.notshoes.ui.theme.azulClaro
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.corPlaceholder
import com.ahpp.notshoes.util.funcoes.conexao.possuiConexao

import com.ahpp.notshoes.util.validacao.ValidarCamposDados
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

//variavel que mantem o objeto cliente logado
lateinit var clienteLogado: Cliente
val usuarioLogadoPreferences = stringPreferencesKey("user_id")

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {

    var isLoading by remember { mutableStateOf(true) }
    var internetCheker by remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    // ativar ou desativar o botao de login de acordo com o contexto
    var enabledButton by remember { mutableStateOf(true) }

    val idUsuarioFlow = remember {
        ctx.dataStore.data
            .map { preferences ->
                preferences[usuarioLogadoPreferences] ?: "-1"
            }
    }

    val idUsuario by idUsuarioFlow.collectAsState(initial = "-2")

    val scope = rememberCoroutineScope()
    LaunchedEffect(idUsuario) {
        when (idUsuario) {
            // nao tem usuario logado
            "-1" -> {
                isLoading = false
            }
            // esta carregando o id do banco local
            "-2" -> {
                isLoading = true
            }
            // ja tem usuario logado
            else -> {
                internetCheker = possuiConexao(ctx)
                if (internetCheker) {
                    scope.launch(Dispatchers.IO) {
                        clienteLogado = getCliente(idUsuario.toInt())
                        if (clienteLogado.idCliente == -1) {
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(
                                    ctx,
                                    R.string.servidor_em_manutencao,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            (ctx as? Activity)?.finish()
                        }
                        withContext(Dispatchers.Main) { // Switch back to main thread for UI update
                            navController.navigate("bottomNavBar") { launchSingleTop = true }
                        }
                    }
                } else {
                    Toast.makeText(ctx, R.string.verifique_conexao_internet, Toast.LENGTH_SHORT)
                        .show()
                    (ctx as? Activity)?.finish()
                }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val colorsTextFields = OutlinedTextFieldDefaults.colors(
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

        val shapeArredondado = RoundedCornerShape(10.dp)

        val elevationButton = ButtonDefaults.buttonElevation(10.dp)

        var dadosIncorretos by remember { mutableStateOf(false) }

        var email by remember { mutableStateOf("") }
        var senha by remember { mutableStateOf("") }

        var emailValido by remember { mutableStateOf(true) }
        var senhaValida by remember { mutableStateOf(true) }

        //gerenciar visibilidade da senha
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(id = R.drawable.baseline_visibility_24)
        else
            painterResource(id = R.drawable.baseline_visibility_off_24)

        Column(
            modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White,
                            Color.White,
                            Color.White,
                            Color.White,
                            Color.White,
                            azulClaro,
                            azulEscuro
                        )
                    )
                )
                .padding(start = 20.dp, end = 20.dp)
        ) {

            Row(
                modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(150.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.entre_aproveite_ofertas),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulEscuro
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (it.length <= 255) {
                        email = it
                    }
                    emailValido = ValidarCamposDados.validarEmail(email)
                    dadosIncorretos = false
                },
                isError = !emailValido || (dadosIncorretos),
                supportingText = {
                    if (!emailValido) {
                        Text(text = stringResource(id = R.string.digite_email_valido))
                    } else if (dadosIncorretos) {
                        Text(text = stringResource(id = R.string.dados_incorretos))
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email),
                        color = corPlaceholder
                    )
                },
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = null, tint = Color.Black)
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = colorsTextFields,
                shape = shapeArredondado
            )

            OutlinedTextField(
                value = senha,
                isError = !senhaValida || (dadosIncorretos),
                supportingText = {
                    if (!senhaValida) {
                        Text(text = stringResource(id = R.string.digite_senha_valida))
                    } else if (dadosIncorretos) {
                        Text(text = stringResource(id = R.string.dados_incorretos))
                    }
                },
                onValueChange = {
                    if (it.length <= 255) {
                        senha = it
                    }
                    senhaValida = ValidarCamposDados.validarSenha(senha)
                    dadosIncorretos = false
                },
                placeholder = { Text(stringResource(id = R.string.senha), color = corPlaceholder) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = stringResource(id = R.string.alterar_visibilidade_senha),
                            tint = Color.Black
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                colors = colorsTextFields,
                shape = shapeArredondado
            )

            Row(
                modifier
                    .padding(top = 13.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                            Toast.makeText(
                                ctx,
                                "Implementar isso aqui vai ser doidera",
                                Toast.LENGTH_SHORT
                            ).show()
                        }),
                    text = stringResource(id = R.string.esqueceu_sua_senha),
                    fontSize = 16.sp,
                    color = azulEscuro
                )
            }

            Row(
                modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                FilledTonalButton(
                    onClick = {

                        emailValido = ValidarCamposDados.validarEmail(email)
                        senhaValida = ValidarCamposDados.validarSenha(senha)

                        if (emailValido && senhaValida) {
                            enabledButton = false
                            if (possuiConexao(ctx)) {
                                val loginCliente = LoginCliente(email, senha)
                                loginCliente.sendLoginData(object : LoginCliente.Callback {
                                    override fun onSuccess(idUsuarioRecebido: String) {
                                        // -1 usuario não existe
                                        if (idUsuarioRecebido != "-1") {
                                            // salvar o id do usuário logado
                                            scope.launch {
                                                ctx.dataStore.edit { preferences ->
                                                    preferences[usuarioLogadoPreferences] =
                                                        idUsuarioRecebido
                                                }
                                            }

                                        } else {
                                            enabledButton = true
                                            dadosIncorretos = true
                                        }
                                    }

                                    override fun onFailure(e: IOException) {
                                        // erro de rede não é possível mostrar um Toast de
                                        // um Thread que não seja UI, então é feito dessa forma
                                        Handler(Looper.getMainLooper()).post {
                                            Toast.makeText(ctx, R.string.erro_rede, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        //Log.e("Erro: ", e.message.toString())
                                        enabledButton = true
                                    }
                                })
                            } else {
                                Toast.makeText(
                                    ctx,
                                    R.string.verifique_conexao_internet,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                enabledButton = true
                            }
                        }
                    },
                    modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = enabledButton,
                    colors = ButtonDefaults.buttonColors(containerColor = azulEscuro),
                    shape = shapeArredondado,
                    elevation = elevationButton
                ) {
                    Text(
                        text = stringResource(id = R.string.entrar),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            Color.White
                        )
                    )
                }
            }

            Row(
                modifier
                    .padding(top = 25.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.nao_tem_conta) + " ",
                    fontSize = 15.sp,
                    style = TextStyle(
                        Color.Black
                    )
                )
                Text(
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                            navController.navigate("registro") {
                                launchSingleTop = true
                            }
                        }),
                    text = stringResource(id = R.string.cadastrese),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = azulEscuro
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_login),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 3.dp)
                )
            }

        }
    }
}