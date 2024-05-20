package com.ahpp.notshoes.view

import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ahpp.notshoes.bd.RegistroCliente
import com.ahpp.notshoes.util.ValidarCampos
import java.io.IOException

@Composable
fun RegistroScreen(modifier: Modifier = Modifier, navController: NavController) {

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

    //gerenciar click no texto de já registrado
    val isAlreadyRegisteredClicked = remember { mutableStateOf(false) }

    if (isAlreadyRegisteredClicked.value) {
        // Save the state of the current destination (login screen)
        navController.popBackStack(route = "login", inclusive = true, saveState = true)
        isAlreadyRegisteredClicked.value = false // Reset the flag
    }

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
                        Color(0xFFFFFFFF),
                        Color(0xFFFFFFFF),
                        Color(0xFFFFFFFF),
                        Color(0xFF86D0E2),
                        Color(0xFF029CCA)
                    )
                )
            )
            .padding(start = 20.dp, end = 20.dp)
    ) {

        Row(
            modifier
                .padding(top = 35.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Crie sua conta", fontSize = 34.sp, fontWeight = FontWeight.Bold)
        }

        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Corra e aproveite nossas ofertas!", fontSize = 20.sp)
        }

        Row(modifier.padding(top = 25.dp, start = 10.dp)) {
            Text(text = "Nome", fontWeight = FontWeight.Bold)
        }

        OutlinedTextField(
            value = nome,
            onValueChange = {
                if (it.length <= 255) {
                    nome = it
                }
                nomeValido = ValidarCampos.validarNome(nome)
            },
            isError = !nomeValido,
            supportingText = {
                if (!nomeValido) {
                    Text(text = "Digite um nome válido.")
                }
            },
            placeholder = { Text(text = "Seu nome", color = Color(0xFF4A5255)) },
            leadingIcon = {
                Icon(Icons.Filled.Person, contentDescription = "Icone pessoa", tint = Color.Black)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEF3F5),
                focusedContainerColor = Color(0xFFEEF3F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color(0xFF029CCA),
                focusedLabelColor = Color(0xFF000000),
                cursorColor = Color(0xFF029CCA),
            )
        )

        Row(modifier.padding(top = 10.dp, start = 10.dp)) {
            Text(text = "E-mail", fontWeight = FontWeight.Bold)
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (it.length <= 255) {
                    email = it
                }
                emailValido = ValidarCampos.validarEmail(email)
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
            placeholder = { Text(text = "email@exemplo.com", color = Color(0xFF4A5255)) },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Icone email", tint = Color.Black)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEF3F5),
                focusedContainerColor = Color(0xFFEEF3F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color(0xFF000000),
                cursorColor = Color(0xFF029CCA),
            )
        )

        Row(modifier.padding(top = 10.dp, start = 10.dp)) {
            Text(text = "Senha", fontWeight = FontWeight.Bold)
        }

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
                senhaValida = ValidarCampos.validarSenha(senha)
            },
            placeholder = { Text(text = "Crie uma nova senha", color = Color(0xFF4A5255)) },
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
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFEEF3F5),
                focusedContainerColor = Color(0xFFEEF3F5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color(0xFF029CCA),
                focusedLabelColor = Color(0xFF000000),
                cursorColor = Color(0xFF029CCA),
            )
        )

        Row(
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp)
        ) {
            ElevatedButton(
                onClick = {
                    nomeValido = ValidarCampos.validarNome(nome)
                    emailValido = ValidarCampos.validarEmail(email)
                    senhaValida = ValidarCampos.validarSenha(senha)

                    if (nomeValido && emailValido && senhaValida) {
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
                                    Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT).show()
                                }
                                Log.e("Erro: ", e.message.toString())
                            }
                        })
                    }
                },
                modifier
                    .width(230.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF029CCA))
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
                text = "Já possui uma? ",
                fontSize = 15.sp,
                style = TextStyle(
                    Color.Black
                )
            )
            Text(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = { navController.popBackStack("login", false) }),
                text = "Entre",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                style = TextStyle(
                    Color(0xFF029CCA)
                )
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