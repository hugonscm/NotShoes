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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.cliente.AtualizarEmailCliente
import com.ahpp.notshoes.bd.cliente.ClienteRepository
import com.ahpp.notshoes.util.validacao.ValidarCamposDados
import com.ahpp.notshoes.util.clienteLogado
import java.io.IOException

@Composable
fun AlterarEmailScreen(onBackPressed: () -> Unit) {

    BackHandler {
        onBackPressed()
    }

    val ctx = LocalContext.current

    var emailNovo by remember { mutableStateOf("") }

    var emailValido by remember { mutableStateOf(true) }

    var codigoStatusAlteracao by remember { mutableStateOf("201") }

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
                painter = painterResource(id = R.drawable.baseline_contact_mail_24),
                contentDescription = "Toque para voltar",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Alterar e-mail", fontSize = 28.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                enabled = false,
                placeholder = { Text(clienteLogado.email, color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Transparent,
                    disabledContainerColor = Color(0xFFEEF3F5),
                )
            )
            Spacer(Modifier.padding(top = 15.dp))
            OutlinedTextField(
                value = emailNovo,
                onValueChange = {
                    if (it.length <= 255) {
                        emailNovo = it
                    }
                    emailValido = ValidarCamposDados.validarEmail(emailNovo)
                    codigoStatusAlteracao = "201"
                },
                isError = !emailValido || codigoStatusAlteracao == "500",
                supportingText = {
                    if (!emailValido) {
                        Text(
                            text = "Digite um e-mail válido.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    } else if (codigoStatusAlteracao == "500") {
                        Text(text = "E-mail já cadastrado.")
                    }
                },
                placeholder = { Text(text = "Digite um novo e-mail", color = Color(0xFF4A5255)) },
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

                    emailValido = ValidarCamposDados.validarEmail(emailNovo)

                    if (emailValido) {
                        val atualizarEmailCliente =
                            AtualizarEmailCliente(emailNovo)

                        atualizarEmailCliente.sendAtualizarData(object :
                            AtualizarEmailCliente.Callback {
                            override fun onSuccess(code: String) {
                                //500 = email ja existe
                                //201 = email alterado com sucesso
                                codigoStatusAlteracao = code
                                Log.i("CODIGO RECEBIDO {ALTERAR EMAIL}: ", code)

                                if (code == "201") {
                                    Handler(Looper.getMainLooper()).post {
                                        val repository = ClienteRepository()
                                        clienteLogado =
                                            repository.getCliente(clienteLogado.idCliente)
                                        Toast.makeText(
                                            ctx,
                                            "E-mail alterado com sucesso.",
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
                    text = "ALTERAR E-MAIL",
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