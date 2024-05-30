package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsSeusDados

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.cliente.AtualizarDadosPessoaisCliente
import com.ahpp.notshoes.bd.cliente.ClienteRepository
import com.ahpp.notshoes.util.RadioButtonButtonPersonalizado
import com.ahpp.notshoes.util.validacao.ValidarCamposDados
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.util.visualTransformation.CpfVisualTransformation
import com.ahpp.notshoes.util.visualTransformation.PhoneVisualTransformation
import java.io.IOException

@Composable
fun AlterarDadosPessoaisScreen(onBackPressed: () -> Unit) {

    BackHandler {
        onBackPressed()
    }

    val ctx = LocalContext.current

    var nomeNovo by remember { mutableStateOf(clienteLogado.nome) }
    var cpfNovo by remember { mutableStateOf(clienteLogado.cpf) }
    var telefoneNovo by remember { mutableStateOf(clienteLogado.telefoneContato) }
    var generoNovo by remember { mutableStateOf(clienteLogado.genero) }

    var nomeValido by remember { mutableStateOf(true) }
    var cpfValido by remember { mutableStateOf(true) }
    var telefoneValido by remember { mutableStateOf(true) }

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
            verticalAlignment = Alignment.CenterVertically
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
                    Icons.Default.Close,
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                text = "Alterar dados pessoais",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
                Text(text = "Nome", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            OutlinedTextField(
                value = nomeNovo,
                onValueChange = {
                    if (it.length <= 255) {
                        nomeNovo = it
                    }
                    nomeValido = true
                },
                isError = !nomeValido,
                supportingText = {
                    if (!nomeValido) {
                        Text(text = "Digite um nome válido.")
                    }
                },
                placeholder = { Text(text = "Seu Nome", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
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

            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
                Text(text = "CPF", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedTextField(
                value = if (cpfNovo == "") "" else cpfNovo,
                onValueChange = {
                    if (it.length <= 11) {
                        cpfNovo = it
                    }
                    cpfValido = true
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = !cpfValido,
                supportingText = {
                    if (!cpfValido) {
                        Text(text = "Digite um CPF válido.")
                    }
                },
                placeholder = { Text(text = "Digite seu CPF", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
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
                ),
                visualTransformation = CpfVisualTransformation()
            )

            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
                Text(text = "Telefone de contato", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedTextField(
                value = if (telefoneNovo == "") "" else telefoneNovo,
                onValueChange = {
                    if (it.length < 12) {
                        telefoneNovo = it
                    }
                    telefoneValido = true
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = !telefoneValido,
                supportingText = {
                    if (!telefoneValido) {
                        Text(text = "Digite um telefone válido.")
                    }
                },
                placeholder = {
                    Text(
                        text = "Digite seu telefone de contato",
                        color = Color(0xFF4A5255)
                    )
                },
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
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
                ),
                visualTransformation = PhoneVisualTransformation()
            )

            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
                Text(text = "Gênero", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                RadioButtonButtonPersonalizado(
                    text = "Masculino",
                    isSelected = generoNovo == "M",
                    onClick = { generoNovo = "M" }
                )
                RadioButtonButtonPersonalizado(
                    text = "Feminino",
                    isSelected = generoNovo == "F",
                    onClick = { generoNovo = "F" }
                )
                RadioButtonButtonPersonalizado(
                    text = "Prefiro não informar",
                    isSelected = generoNovo == "0",
                    onClick = { generoNovo = "0" }
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                ElevatedButton(
                    onClick = {

                        nomeValido = ValidarCamposDados.validarNome(nomeNovo)
                        cpfValido = ValidarCamposDados.validarCpf(cpfNovo)
                        telefoneValido = ValidarCamposDados.validarTelefone(telefoneNovo)

                        if (nomeValido && cpfValido && telefoneValido) {
                            val atualizarDadosCliente =
                                AtualizarDadosPessoaisCliente(
                                    nomeNovo,
                                    cpfNovo,
                                    telefoneNovo,
                                    generoNovo
                                )

                            atualizarDadosCliente.sendAtualizarData(object :
                                AtualizarDadosPessoaisCliente.Callback {
                                override fun onSuccess(code: String) {
                                    Log.i("CODIGO RECEBIDO{ALTERAR DADOS CLIENTE}: ", code)
                                    Handler(Looper.getMainLooper()).post {
                                        val repository = ClienteRepository()
                                        clienteLogado =
                                            repository.getCliente(clienteLogado.idCliente)
                                        Toast.makeText(
                                            ctx,
                                            "Dados foram atualizados.",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        onBackPressed()
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF029CCA))
                ) {
                    Text(
                        text = "Alterar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            Color.White
                        )
                    )
                }
            }
        }
    }
}