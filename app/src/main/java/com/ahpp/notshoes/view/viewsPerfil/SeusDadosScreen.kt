package com.ahpp.notshoes.view.viewsPerfil

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.AtualizarDadosCliente
import com.ahpp.notshoes.bd.ClienteRepository
import com.ahpp.notshoes.util.ValidarCampos
import com.ahpp.notshoes.util.cliente
import java.io.IOException

@Composable
fun SeusDadosScreen(onBackPressed: () -> Unit) {

    var clickedAlterarDadosPessoais by remember { mutableStateOf(false) }

    if (clickedAlterarDadosPessoais) {
        AlterarDadosPessoaisScreen(onBackPressed = { clickedAlterarDadosPessoais = false })
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF029CCA))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.Bottom), contentPadding = PaddingValues(0.dp),
                    onClick = { onBackPressed() },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Image(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Toque para voltar",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(270.dp),
                    text = "Seus dados", fontSize = 20.sp, maxLines = 1,
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
                        .clickable(true, onClick = { clickedAlterarDadosPessoais = true }),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Alterar dados pessoais",
                            fontSize = 18.sp
                        )
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = null,
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
                        .clickable(true, onClick = {}),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Alterar e-mail",
                            fontSize = 18.sp
                        )
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = null,
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
                        .clickable(true, onClick = {}),
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 10.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Alterar senha",
                            fontSize = 18.sp
                        )
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                            contentDescription = null,
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

@Composable
fun AlterarDadosPessoaisScreen(onBackPressed: () -> Unit) {

    val ctx = LocalContext.current

    var nomeNovo by remember { mutableStateOf(cliente.nome) }
    var cpfNovo by remember { mutableStateOf(cliente.cpf) }
    var telefoneNovo by remember { mutableStateOf(cliente.telefoneContato) }
    var generoNovo by remember { mutableStateOf(cliente.genero) }

    var nomeValido by remember { mutableStateOf(true) }
    var cpfValido by remember { mutableStateOf(true) }
    var telefoneValido by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF029CCA))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .size(45.dp), contentPadding = PaddingValues(0.dp),
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
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(270.dp),
                text = "Altere seus dados", fontSize = 20.sp, maxLines = 1,
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
                .padding(10.dp)
        ) {
            Row {
                Text(text = "Nome", fontWeight = FontWeight.Bold)
            }
            OutlinedTextField(
                value = nomeNovo,
                onValueChange = {
                    nomeNovo = it
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
                    .fillMaxWidth(),
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

            Row {
                Text(text = "CPF", fontWeight = FontWeight.Bold)
            }

            OutlinedTextField(
                value = if (cpfNovo == "") "" else cpfNovo,
                onValueChange = {
                    cpfNovo = it
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
                    .fillMaxWidth(),
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

            Row {
                Text(text = "Telefone de contato", fontWeight = FontWeight.Bold)
            }

            OutlinedTextField(
                value = if (telefoneNovo == "") "" else telefoneNovo,
                onValueChange = {
                    telefoneNovo = it
                    telefoneValido = true
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = !telefoneValido,
                supportingText = {
                    if (!telefoneValido) {
                        Text(text = "Digite um telefone válido.")
                    }
                },
                placeholder = { Text(text = "Digite seu telefone de contato", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .fillMaxWidth(),
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

            Row {
                Text(text = "Gênero", fontWeight = FontWeight.Bold)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                GeneroButton(
                    text = "Masculino",
                    isSelected = generoNovo == "M",
                    onClick = { generoNovo = "M" }
                )
                GeneroButton(
                    text = "Feminino",
                    isSelected = generoNovo == "F",
                    onClick = { generoNovo = "F" }
                )
                GeneroButton(
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

                        nomeValido = ValidarCampos.validarNome(nomeNovo)
                        cpfValido = ValidarCampos.validarCpf(cpfNovo)
                        telefoneValido = ValidarCampos.validarTelefone(telefoneNovo)

                        if (nomeValido && cpfValido && telefoneValido) {
                            val atualizarDadosCliente =
                                AtualizarDadosCliente(nomeNovo, cpfNovo, telefoneNovo, generoNovo)

                            atualizarDadosCliente.sendAtualizarData(object :
                                AtualizarDadosCliente.Callback {
                                override fun onSuccess(code: String) {
                                    Log.i("CODIGO RECEBIDO: ", code)
                                    Handler(Looper.getMainLooper()).post {
                                        val repository = ClienteRepository()
                                        cliente = repository.getCliente(cliente.idCliente)
                                        Toast.makeText(ctx, "Dados foram atualizados.", Toast.LENGTH_SHORT)
                                            .show()
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

@Composable
fun GeneroButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF029CCA) else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonColors(backgroundColor, contentColor, contentColor, contentColor)
        )
        Text(text = text)
    }
}
