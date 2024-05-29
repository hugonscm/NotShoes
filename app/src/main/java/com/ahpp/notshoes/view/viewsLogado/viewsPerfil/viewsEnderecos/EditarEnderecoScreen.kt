package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos

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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.ClienteRepository
import com.ahpp.notshoes.bd.endereco.EditarEnderecoCliente
import com.ahpp.notshoes.model.Endereco
import com.ahpp.notshoes.util.ValidarCamposEndereco
import com.ahpp.notshoes.util.clienteLogado
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarEnderecoScreen(onBackPressed: () -> Unit, enderecoSelecionado: Endereco) {

    BackHandler {
        onBackPressed()
    }

    val ctx = LocalContext.current

    var cep by remember { mutableStateOf(enderecoSelecionado.cep) }
    var endereco by remember { mutableStateOf(enderecoSelecionado.endereco) }
    var numero by remember { mutableStateOf(enderecoSelecionado.numero.toString()) }
    var complemento by remember { mutableStateOf(enderecoSelecionado.complemento) }
    var bairro by remember { mutableStateOf(enderecoSelecionado.bairro) }
    var cidade by remember { mutableStateOf(enderecoSelecionado.cidade) }

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
    var estado by remember { mutableStateOf(estadosList[estadosList.indexOf(enderecoSelecionado.estado)]) }

    var checkedTornarEnderecoPrincipal by remember { mutableStateOf(false) }

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
                text = "Editar endereço", fontSize = 20.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    Color(0xFFFFFFFF)
                )
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
                    if (it.length <= 10) {
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
                )
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

            if (enderecoSelecionado.idEndereco != clienteLogado.idEnderecoPrincipal){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedTornarEnderecoPrincipal,
                        onCheckedChange = { checkedTornarEnderecoPrincipal = it })
                    Text(
                        text = "Tornar esse endereço principal",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
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
                            val editarEnderecoCliente =
                                EditarEnderecoCliente(
                                    estado,
                                    cidade,
                                    cep,
                                    endereco,
                                    bairro,
                                    numero,
                                    complemento,
                                    clienteLogado.idCliente,
                                    enderecoSelecionado.idEndereco,
                                    checkedTornarEnderecoPrincipal
                                )

                            editarEnderecoCliente.sendEditarEnderecoCliente(object :
                                EditarEnderecoCliente.Callback {
                                override fun onSuccess(code: String) {
                                    Log.i(
                                        "CODIGO RECEBIDO (sucesso na atualizacao de endereço): ",
                                        code
                                    )

                                    if (code == "1") {
                                        Handler(Looper.getMainLooper()).post {

                                            if(checkedTornarEnderecoPrincipal){
                                                val repository = ClienteRepository()
                                                clienteLogado =
                                                    repository.getCliente(clienteLogado.idCliente)
                                            }

                                            Toast.makeText(
                                                ctx,
                                                "Endereço atualizado com sucesso.",
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
                        text = "Atualizar endereço",
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