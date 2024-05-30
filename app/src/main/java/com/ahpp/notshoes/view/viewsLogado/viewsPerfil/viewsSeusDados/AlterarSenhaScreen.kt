package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSeusDados

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.cliente.AtualizarSenhaCliente
import com.ahpp.notshoes.bd.cliente.ClienteRepository
import com.ahpp.notshoes.util.validacao.ValidarCamposDados
import com.ahpp.notshoes.util.clienteLogado
import java.io.IOException
import java.security.MessageDigest

@Composable
fun AlterarSenhaScreen(onBackPressed: () -> Unit) {

    BackHandler {
        onBackPressed()
    }

    val ctx = LocalContext.current

    var senhaAtual by remember { mutableStateOf("") }
    var senhaNova by remember { mutableStateOf("") }

    var senhaAtualCorreta by remember { mutableStateOf(true) }
    var senhaAtualValida by remember { mutableStateOf(true) }
    var senhaNovaValida by remember { mutableStateOf(true) }

    var codigoStatusAlteracao by remember { mutableStateOf("201") }

    var senhaAtualVisibility by remember { mutableStateOf(false) }
    var senhaNovoVisibility by remember { mutableStateOf(false) }

    val iconVisibility1 = if (senhaAtualVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)

    val iconVisibility2 = if (senhaNovoVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)

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
                .background(Color(0xFF029CCA)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp, end = 10.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onBackPressed() },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF029CCA)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.padding(top = 70.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_key_24),
                contentDescription = "Toque para voltar",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Alterar senha", fontSize = 28.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
            OutlinedTextField(
                value = senhaAtual,
                onValueChange = {
                    if (it.length <= 255) {
                        senhaAtual = it
                    }
                    senhaAtualValida = ValidarCamposDados.validarSenha(senhaAtual)
                    senhaAtualCorreta = true
                },
                isError = !senhaAtualValida || !senhaAtualCorreta,
                supportingText = {
                    if (!senhaAtualValida) {
                        Text(
                            text = "Digite uma senha válida.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    } else if (!senhaAtualCorreta) {
                        Text(
                            text = "Senha atual incorreta.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                placeholder = { Text(text = "Senha atual", color = Color(0xFF4A5255)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = {
                        senhaAtualVisibility = !senhaAtualVisibility
                    }) {
                        Icon(
                            painter = iconVisibility1,
                            contentDescription = "Visibility Icon", tint = Color.Black
                        )
                    }
                },
                visualTransformation = if (senhaAtualVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFEEF3F5),
                    focusedContainerColor = Color(0xFFEEF3F5),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color(0xFF000000),
                    cursorColor = Color(0xFF029CCA),
                    errorContainerColor = Color(0xFFEEF3F5),
                    errorSupportingTextColor = Color(0xFFC00404)
                )
            )
            OutlinedTextField(
                value = senhaNova,
                onValueChange = {
                    if (it.length <= 255) {
                        senhaNova = it
                    }
                    senhaNovaValida = ValidarCamposDados.validarSenha(senhaNova)
                },
                isError = !senhaNovaValida,
                supportingText = {
                    if (!senhaNovaValida) {
                        Text(
                            text = "Digite uma senha válida.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                placeholder = { Text(text = "Nova senha", color = Color(0xFF4A5255)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = {
                        senhaNovoVisibility = !senhaNovoVisibility
                    }) {
                        Icon(
                            painter = iconVisibility2,
                            contentDescription = "Visibility Icon", tint = Color.Black
                        )
                    }
                },
                visualTransformation = if (senhaNovoVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFEEF3F5),
                    focusedContainerColor = Color(0xFFEEF3F5),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color(0xFF000000),
                    cursorColor = Color(0xFF029CCA),
                    errorContainerColor = Color(0xFFEEF3F5),
                    errorSupportingTextColor = Color(0xFFC00404)
                )
            )

            Spacer(Modifier.padding(top = 10.dp))

            ElevatedButton(
                onClick = {

                    val senhaAtualCriptografada: String = md5Hash(senhaAtual)

                    senhaAtualCorreta = senhaAtualCriptografada == clienteLogado.senha

                    senhaAtualValida = ValidarCamposDados.validarSenha(senhaAtual)
                    senhaNovaValida = ValidarCamposDados.validarSenha(senhaNova)

                    if (senhaAtualCorreta && senhaAtualValida && senhaNovaValida) {
                        val atualizarSenhaCliente = AtualizarSenhaCliente(senhaNova)

                        atualizarSenhaCliente.sendAtualizarData(object :
                            AtualizarSenhaCliente.Callback {
                            override fun onSuccess(code: String) {
                                //500 = erro
                                //201 = senha alterada com sucesso
                                codigoStatusAlteracao = code
                                Log.i("CODIGO RECEBIDO {ALTERAR SENHA}: ", code)

                                if (code == "201") {
                                    Handler(Looper.getMainLooper()).post {
                                        val repository = ClienteRepository()
                                        clienteLogado =
                                            repository.getCliente(clienteLogado.idCliente)
                                        Toast.makeText(
                                            ctx,
                                            "Senha alterada com sucesso.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onBackPressed()
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
                    }
                },
                modifier = Modifier
                    .width(230.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "ALTERAR SENHA",
                    fontSize = 15.sp,
                    //fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        Color(0xFF046380)
                    )
                )
            }
            Spacer(Modifier.padding(top = 10.dp))
            Text(
                modifier = Modifier.clickable(true, onClick = onBackPressed),
                text = "CANCELAR",
                fontSize = 15.sp,
                //fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color.White
                )
            )
        }
    }
}

fun md5Hash(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bytes = md.digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}