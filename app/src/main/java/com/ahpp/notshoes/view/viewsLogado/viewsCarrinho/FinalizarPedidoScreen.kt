package com.ahpp.notshoes.view.viewsLogado.viewsCarrinho

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.endereco.getEnderecos
import com.ahpp.notshoes.model.Endereco
import com.ahpp.notshoes.util.RadioButtonButtonPersonalizado
import com.ahpp.notshoes.util.cards.CardEnderecoCarrinho
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos.CadastrarEnderecoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FinalizarPedidoScreen(onBackPressed: () -> Unit) {

    BackHandler {
        onBackPressed()
    }

//    val ctx = LocalContext.current

    var clickedCadastrarEndereco by remember { mutableStateOf(false) }

    if (clickedCadastrarEndereco) {
        CadastrarEnderecoScreen(onBackPressed = { clickedCadastrarEndereco = false })
    } else {

        var tipoEntrega by remember { mutableStateOf("Expressa") }
        var tipoPagamento by remember { mutableStateOf("Pix") }

        var expandedEnderecos by remember { mutableStateOf(false) }

        var enderecosList by remember { mutableStateOf(emptyList<Endereco>()) }
        var isLoading by remember { mutableStateOf(true) }

        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                enderecosList = getEnderecos(clienteLogado.idCliente)
                isLoading = false
            }
        }

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
                        .padding(10.dp),
                    contentPadding = PaddingValues(0.dp),
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
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = "Finalizar pedido",
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
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = com.ahpp.notshoes.R.drawable.outline_home_24),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(text = "Endereço de entrega", fontSize = 15.sp)
                    }

                    if (enderecosList.isNotEmpty()) {

                        // Separar o endereço principal dos outros endereços e colocar no topo da lista
                        val enderecoPrincipal =
                            enderecosList.find { it.idEndereco == clienteLogado.idEnderecoPrincipal }
                        val outrosEnderecos =
                            enderecosList.filter { it.idEndereco != clienteLogado.idEnderecoPrincipal }
                        val listaEnderecosOrganizada =
                            listOfNotNull(enderecoPrincipal) + outrosEnderecos

                        var enderecoParaEntrega by remember { mutableStateOf(enderecoPrincipal) }

                        if (!expandedEnderecos) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (enderecoParaEntrega != null) {
                                    Card(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = CardColors(
                                            containerColor = Color.White,
                                            Color.Black,
                                            Color.Black,
                                            Color.Black
                                        ),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .fillMaxWidth(),
                                        elevation = CardDefaults.cardElevation(4.dp),
                                    ) {
                                        enderecoParaEntrega?.let { CardEnderecoCarrinho(it) }
                                    }
                                    ElevatedButton(
                                        onClick = {
                                            expandedEnderecos = true
                                        },
                                        modifier = Modifier
                                            .width(230.dp)
                                            .height(40.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFF029CCA
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = "USAR OUTRO ENDEREÇO",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            style = TextStyle(
                                                Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 10.dp),

                                ) {
                                Text(text = "Selecione um endereço")
                            }
                            LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                                items(
                                    listaEnderecosOrganizada,
                                    key = { it.idEndereco }) { endereco ->
                                    Card(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = CardColors(
                                            containerColor = Color.White,
                                            Color.Black,
                                            Color.Black,
                                            Color.Black
                                        ),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .fillMaxWidth()
                                            .clickable(enabled = true, onClick = {
                                                enderecoParaEntrega = endereco
                                                expandedEnderecos = false
                                            }),
                                        elevation = CardDefaults.cardElevation(4.dp),
                                    ) {
                                        CardEnderecoCarrinho(endereco)
                                    }
                                }
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        ElevatedButton(
                                            onClick = {
                                                clickedCadastrarEndereco = true
                                            },
                                            modifier = Modifier
                                                .width(270.dp)
                                                .height(40.dp),
                                            shape = RoundedCornerShape(5.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(
                                                    0xFF029CCA
                                                )
                                            )
                                        ) {
                                            Text(
                                                text = "CADASTRAR OUTRO ENDEREÇO",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp,
                                                style = TextStyle(
                                                    Color.White
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 45.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nenhum endereço cadastrado.",
                                fontSize = 25.sp,
                                style = TextStyle(
                                    Color(0xFF029CCA)
                                )
                            )
                            ElevatedButton(
                                onClick = {
                                    clickedCadastrarEndereco = true
                                },
                                modifier = Modifier
                                    .width(230.dp)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF029CCA
                                    )
                                )
                            ) {
                                Text(
                                    text = "ADICIONAR ENDEREÇO",
                                    fontSize = 15.sp,
                                    style = TextStyle(
                                        Color.White
                                    )
                                )
                            }
                        }

                    }

                    if (!expandedEnderecos) {

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = com.ahpp.notshoes.R.drawable.baseline_directions_car_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "  Selecione o tipo de entrega",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                        ) {
                            RadioButtonButtonPersonalizado(
                                text = "Expressa",
                                isSelected = tipoEntrega == "Expressa",
                                onClick = { tipoEntrega = "Expressa" }
                            )
                            RadioButtonButtonPersonalizado(
                                text = "Normal",
                                isSelected = tipoEntrega == "Normal",
                                onClick = { tipoEntrega = "Normal" }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = com.ahpp.notshoes.R.drawable.baseline_attach_money_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "  Forma de pagamento",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                        ) {
                            RadioButtonButtonPersonalizado(
                                text = "Pix",
                                isSelected = tipoPagamento == "Pix",
                                onClick = { tipoPagamento = "Pix" }
                            )
                            RadioButtonButtonPersonalizado(
                                text = "Cartão de crédito",
                                isSelected = tipoPagamento == "crédito",
                                onClick = { tipoPagamento = "crédito" }
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = com.ahpp.notshoes.R.drawable.baseline_checklist_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "  Valor total",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        //continua aqui

                        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                            Row (Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "Frete: ",
                                    fontSize = 12.sp,
                                )
                                Text(
                                    text = "Grátis",
                                    fontSize = 12.sp,
                                    color = Color.Green
                                )
                            }

                            Text(
                                text = "Valor total",
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}