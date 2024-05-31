package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
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
import com.ahpp.notshoes.bd.endereco.AdicionarEnderecoCliente
import com.ahpp.notshoes.bd.cliente.ClienteRepository
import com.ahpp.notshoes.util.validacao.ValidarCamposEndereco
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.util.visualTransformation.CepVisualTransformation
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarEnderecoScreen(onBackPressed: () -> Unit) {

    BackHandler {
        onBackPressed()
    }

    val ctx = LocalContext.current

    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }

    var cepValido by remember { mutableStateOf(true) }
    var enderecoValido by remember { mutableStateOf(true) }
    var numeroValido by remember { mutableStateOf(true) }
    var bairroValido by remember { mutableStateOf(true) }
    var estadoValido by remember { mutableStateOf(true) }
    var cidadeValido by remember { mutableStateOf(true) }

    val estadosList = listOf(
        "Estado",
        "Acre",
        "Alagoas",
        "Amapá",
        "Amazonas",
        "Bahia",
        "Ceará",
        "Distrito Federal",
        "Espírito Santo",
        "Goiás",
        "Maranhão",
        "Mato Grosso",
        "Mato Grosso do Sul",
        "Minas Gerais",
        "Pará",
        "Paraíba",
        "Paraná",
        "Pernambuco",
        "Piauí",
        "Rio de Janeiro",
        "Rio Grande do Norte",
        "Rio Grande do Sul",
        "Rondônia",
        "Roraima",
        "Santa Catarina",
        "São Paulo",
        "Sergipe",
        "Tocantins"
    )
    var expanded by remember { mutableStateOf(false) }
    var estado by remember { mutableStateOf(estadosList[0]) }

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
                text = "Cadastrar enredeço",
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
                .background(Color.White),
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = cep,
                onValueChange = {
                    if (it.length <= 8) {
                        cep = it
                    }
                    cepValido = true
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = !cepValido,
                supportingText = {
                    if (!cepValido) {
                        Text(text = "Digite um CEP.")
                    }
                },
                placeholder = { Text(text = "CEP", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
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
                visualTransformation = CepVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = endereco,
                onValueChange = {
                    if (it.length <= 255) {
                        endereco = it
                    }
                    enderecoValido = true
                },
                isError = !enderecoValido,
                supportingText = {
                    if (!enderecoValido) {
                        Text(text = "Digite um endereço válido.")
                    }
                },
                placeholder = { Text(text = "Endereço", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
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

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                OutlinedTextField(
                    value = numero,
                    onValueChange = {
                        if (it.length <= 10) {
                            numero = it
                        }
                        numeroValido = true
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = !numeroValido,
                    supportingText = {
                        if (!numeroValido) {
                            Text(text = "Digite um número válido.")
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Número",
                            color = Color(0xFF4A5255)
                        )
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 5.dp)
                        .weight(1f),
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

                OutlinedTextField(
                    value = complemento,
                    onValueChange = {
                        if (it.length <= 255) {
                            complemento = it
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Complemento",
                            color = Color(0xFF4A5255)
                        )
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, end = 10.dp)
                        .weight(1f),
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

            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = bairro,
                onValueChange = {
                    if (it.length <= 255) {
                        bairro = it
                    }
                    bairroValido = true
                },
                isError = !bairroValido,
                supportingText = {
                    if (!bairroValido) {
                        Text(text = "Informe um bairro válido.")
                    }
                },
                placeholder = { Text(text = "Bairro", color = Color(0xFF4A5255)) },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
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

            Spacer(modifier = Modifier.height(10.dp))

            Row {

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 5.dp)
                        .weight(1f),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {

                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = estado,
                        onValueChange = {},
                        isError = !estadoValido,
                        supportingText = {
                            if (!estadoValido) {
                                Text(text = "Escolha um estado.")
                            }
                        },
                        readOnly = true,
                        singleLine = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        estadosList.forEach { estadoSelecionado ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        estadoSelecionado,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    estado = estadoSelecionado
                                    expanded = false
                                    estadoValido = true
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = cidade,
                    onValueChange = {
                        if (it.length <= 255) {
                            cidade = it
                        }
                        cidadeValido = true
                    },
                    isError = !cidadeValido,
                    supportingText = {
                        if (!cidadeValido) {
                            Text(text = "Informe uma cidade válida.")
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Cidade",
                            color = Color(0xFF4A5255)
                        )
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, end = 10.dp)
                        .weight(1f),
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

            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedButton(
                    onClick = {
                        cepValido = ValidarCamposEndereco.validarCep(cep)
                        enderecoValido = ValidarCamposEndereco.validarEndereco(endereco)
                        numeroValido = ValidarCamposEndereco.validarNumero(numero)
                        bairroValido = ValidarCamposEndereco.validarBairro(bairro)
                        estadoValido = ValidarCamposEndereco.validarEstado(estado)
                        cidadeValido = ValidarCamposEndereco.validarCidade(cidade)

                        if (cepValido && enderecoValido && numeroValido && bairroValido && estadoValido && cidadeValido) {
                            val adicionarEnderecoCliente =
                                AdicionarEnderecoCliente(
                                    estado,
                                    cidade,
                                    cep,
                                    endereco,
                                    bairro,
                                    numero,
                                    complemento,
                                )

                            adicionarEnderecoCliente.sendAdicionarEnderecoCliente(object :
                                AdicionarEnderecoCliente.Callback {
                                override fun onSuccess(code: String) {
                                    Log.i(
                                        "CODIGO RECEBIDO (sucesso no cadastro de endereço): ",
                                        code
                                    )

                                    if (code == "1") {
                                        Handler(Looper.getMainLooper()).post {
                                            val repository = ClienteRepository()
                                            clienteLogado =
                                                repository.getCliente(clienteLogado.idCliente)
                                            Toast.makeText(
                                                ctx,
                                                "Endereço adicionado com sucesso.",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF029CCA))
                ) {
                    Text(
                        text = "Salvar endereço",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            Color.White
                        )
                    )
                }
                Spacer(Modifier.padding(top = 10.dp))
                Text(
                    modifier = Modifier.clickable(true, onClick = onBackPressed),
                    text = "CANCELAR",
                    fontSize = 15.sp,
                    style = TextStyle(
                        Color.Black
                    )
                )
            }
        }
    }
}