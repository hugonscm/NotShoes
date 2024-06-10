package com.ahpp.notshoes.view.viewsDeslogado

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.data.RegistroCliente
import com.ahpp.notshoes.ui.theme.azulClaro
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.corPlaceholder
import com.ahpp.notshoes.util.funcoes.canGoBack
import com.ahpp.notshoes.util.funcoes.conexao.possuiConexao
import com.ahpp.notshoes.util.validacao.ValidarCamposDados
import java.io.IOException

@Composable
fun RegistroScreen(modifier: Modifier = Modifier, navController: NavController) {

    val canGoBack by remember { derivedStateOf { navController.canGoBack } }
    var checkedTermos by remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var nomeValido by remember { mutableStateOf(true) }
    var emailValido by remember { mutableStateOf(true) }
    var senhaValida by remember { mutableStateOf(true) }

    var codigoStatusRegistro by remember { mutableStateOf("201") }

    val ctx = LocalContext.current

    //gerenciar visibilidade da senha
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)

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

    val elevationButton = ButtonDefaults.buttonElevation(5.dp)

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
                text = "Crie sua conta e aproveite nossas ofertas!",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = azulEscuro
            )
        }

        OutlinedTextField(
            value = nome,
            onValueChange = {
                if (it.length <= 255) {
                    nome = it
                }
                nomeValido = ValidarCamposDados.validarNome(nome)
            },
            isError = !nomeValido,
            supportingText = {
                if (!nomeValido) {
                    Text(text = "Digite um nome válido.")
                }
            },
            placeholder = { Text(text = "Nome Completo", color = corPlaceholder) },
            leadingIcon = {
                Icon(Icons.Filled.Person, contentDescription = "Icone pessoa", tint = Color.Black)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            maxLines = 1,
            colors = colorsTextFields,
            shape = shapeArredondado
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (it.length <= 255) {
                    email = it
                }
                emailValido = ValidarCamposDados.validarEmail(email)
                codigoStatusRegistro = "201"
            },
            isError = !emailValido || codigoStatusRegistro == "500",
            supportingText = {
                if (!emailValido) {
                    Text(text = "Digite um e-mail válido.")
                } else if (codigoStatusRegistro == "500") {
                    Text(text = "E-mail já cadastrado.")
                }
            },
            placeholder = { Text(text = "E-mail", color = corPlaceholder) },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Icone email", tint = Color.Black)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            maxLines = 1,
            colors = colorsTextFields,
            shape = shapeArredondado
        )

        OutlinedTextField(
            value = senha,
            isError = !senhaValida,
            supportingText = {
                if (!senhaValida) {
                    Text(text = "Digite uma senha válida.")
                }
            },
            onValueChange = {
                if (it.length <= 255) {
                    senha = it
                }
                senhaValida = ValidarCamposDados.validarSenha(senha)
            },
            placeholder = { Text(text = "Senha", color = corPlaceholder) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Icone senha", tint = Color.Black)
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon", tint = Color.Black
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            maxLines = 1,
            colors = colorsTextFields,
            shape = shapeArredondado
        )

        Row(
            modifier
                .fillMaxWidth()
                .height(30.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedTermos,
                onCheckedChange = { checkedTermos = it },
                colors = CheckboxDefaults.colors(azulEscuro)
            )
            Row {
                Text(
                    text = "Eu li e concordo com os ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = { }),
                    text = "termos de uso.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = azulEscuro
                )
            }
        }

        Row(
            modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            FilledTonalButton(
                onClick = {
                    nomeValido = ValidarCamposDados.validarNome(nome)
                    emailValido = ValidarCamposDados.validarEmail(email)
                    senhaValida = ValidarCamposDados.validarSenha(senha)

                    if (checkedTermos) {
                        if (nomeValido && emailValido && senhaValida) {
                            if (possuiConexao(ctx)) {
                                val registroCliente = RegistroCliente(nome, email, senha)

                                registroCliente.sendRegistroData(object : RegistroCliente.Callback {
                                    override fun onSuccess(code: String) {
                                        //500 = usuario ja existe
                                        //201 = usuario criado com sucesso
                                        codigoStatusRegistro = code
                                        Log.i("CÓDIGO RECEBIDO {CRIAR CONTA}: ", code)

                                        if (code == "201") {
                                            Handler(Looper.getMainLooper()).post {
                                                Toast.makeText(
                                                    ctx,
                                                    "Conta criada com sucesso.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("login")
                                            }
                                        }
                                    }

                                    override fun onFailure(e: IOException) {
                                        // erro de rede
                                        // não é possível mostrar um Toast de um Thread
                                        // que não seja UI, então é feito dessa forma
                                        Handler(Looper.getMainLooper()).post {
                                            Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        Log.e("Erro: ", e.message.toString())
                                    }
                                })
                            } else {
                                Toast.makeText(
                                    ctx,
                                    "Sem conexão com a internet.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    } else {
                        Toast.makeText(ctx, "Aceite os termos de uso.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azulEscuro),
                shape = shapeArredondado,
                elevation = elevationButton
            ) {
                Text(
                    text = "Criar conta",
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
                text = "Já possui uma conta? ",
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyLarge,
                color =
                Color.Black

            )
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = {
                        if(canGoBack){
                            navController.popBackStack("login", false)
                        }
                    }),
                text = "Entre",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = azulEscuro
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.img_registro),
                contentDescription = null,
                modifier = Modifier
                    .width(185.dp)
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 3.dp)
            )
        }

    }
}