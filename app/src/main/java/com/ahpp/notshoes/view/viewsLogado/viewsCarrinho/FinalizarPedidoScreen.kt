package com.ahpp.notshoes.view.viewsLogado.viewsCarrinho

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.carrinho.FinalizarPedido
import com.ahpp.notshoes.bd.endereco.getEnderecos
import com.ahpp.notshoes.model.Endereco
import com.ahpp.notshoes.model.ItemCarrinho
import com.ahpp.notshoes.model.Venda
import com.ahpp.notshoes.util.RadioButtonButtonPersonalizado
import com.ahpp.notshoes.util.cards.CardEnderecoCarrinho
import com.ahpp.notshoes.view.clienteLogado
import com.ahpp.notshoes.util.funcoes.possuiConexao
import com.ahpp.notshoes.view.screensReutilizaveis.SemConexaoScreen
import com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsEnderecos.CadastrarEnderecoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun FinalizarPedidoScreen(
    itensList: List<ItemCarrinho>,
    detalhesPedido: String,
    valorTotalPedido: Double,
    onBackPressed: () -> Unit,
    clickFinalizarPedido: () -> Unit
) {
    val ctx = LocalContext.current
    var internetCheker by remember { mutableStateOf(possuiConexao(ctx)) }

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    // manter a posicao do scroll ao voltar pra tela
    val scrollState = rememberScrollState()

    BackHandler {
        onBackPressed()
    }

    var clickedCadastrarEndereco by remember { mutableStateOf(false) }
    var clickedCompraFinalizada by remember { mutableStateOf(false) }

    if (clickedCompraFinalizada) {
        CompraFinalizadaScreen(onBackPressed = {
            clickedCompraFinalizada = false
            onBackPressed()
        })
    } else if (clickedCadastrarEndereco) {
        CadastrarEnderecoScreen(onBackPressed = {
            clickedCadastrarEndereco = false
            internetCheker = possuiConexao(ctx)
        })
    } else if (!internetCheker) {
        SemConexaoScreen(onBackPressed = {
            internetCheker = possuiConexao(ctx)
        })
    } else {

        var tipoEntrega by remember { mutableStateOf("Expressa") }
        var tipoPagamento by remember { mutableStateOf("Pix") }

        var valorFrete by remember { mutableDoubleStateOf(if (tipoEntrega == "Expressa") 30.0 else 0.0) }

        var expandedEnderecos by remember { mutableStateOf(false) }

        var enderecosList by remember { mutableStateOf(emptyList<Endereco>()) }
        var isLoading by remember { mutableStateOf(true) }
        var enderecoParaEntrega by remember { mutableStateOf<Endereco?>(null) }

        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                enderecosList = getEnderecos(clienteLogado.idCliente)
                val enderecoPrincipal =
                    enderecosList.find { it.idEndereco == clienteLogado.idEnderecoPrincipal }
                enderecoParaEntrega = enderecoPrincipal
                isLoading = false
            }
        }

        val enderecoPrincipal =
            enderecosList.find { it.idEndereco == clienteLogado.idEnderecoPrincipal }
        val outrosEnderecos =
            enderecosList.filter { it.idEndereco != clienteLogado.idEnderecoPrincipal }
        val listaEnderecosOrganizada =
            listOfNotNull(enderecoPrincipal) + outrosEnderecos

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFFFFFFF),
                                Color(0xFF86D0E2),
                                Color(0xFF86D0E2),
                                Color(0xFFFFFFFF)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                        .height(60.dp)
                        .background(Color(0xFF029CCA))
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .size(45.dp),
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
                        color = Color.White
                    )
                }

                if (expandedEnderecos) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp),

                        ) {
                        Text(text = "Selecione um endereço", fontSize = 20.sp)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        items(
                            listaEnderecosOrganizada,
                            key = { it.idEndereco }) { endereco ->
                            Card(
                                shape = RoundedCornerShape(5.dp),
                                colors = CardDefaults.cardColors(
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
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)

                    ) {
                        if (enderecosList.isNotEmpty() && enderecoParaEntrega != null) {
                            Card(
                                shape = RoundedCornerShape(5.dp),
                                colors = CardColors(
                                    containerColor = Color.White,
                                    Color.Black,
                                    Color.Black,
                                    Color.Black
                                ),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(4.dp),
                            ) {
                                enderecoParaEntrega?.let { CardEnderecoCarrinho(it) }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
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
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.padding(bottom = 10.dp),
                                    text = "Nenhum endereço cadastrado.",
                                    fontSize = 25.sp,
                                    style = TextStyle(
                                        Color(0xFFFF0000)
                                    )
                                )

                                ElevatedButton(
                                    onClick = {
                                        clickedCadastrarEndereco = true
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
                                        text = "ADICIONAR ENDEREÇO",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        style = TextStyle(
                                            Color.White
                                        )
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(R.drawable.baseline_directions_car_24),
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
                                onClick = {
                                    tipoEntrega = "Expressa"
                                    valorFrete = 30.0
                                }
                            )
                            RadioButtonButtonPersonalizado(
                                text = "Normal",
                                isSelected = tipoEntrega == "Normal",
                                onClick = {
                                    tipoEntrega = "Normal"
                                    valorFrete = 0.0
                                }
                            )
                        }

                        Row(
                            modifier = Modifier.padding(top = 30.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(R.drawable.baseline_attach_money_24),
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
                                isSelected = tipoPagamento == "Cartão de crédito",
                                onClick = { tipoPagamento = "Cartão de crédito" }
                            )
                        }

                        Row(
                            modifier = Modifier.padding(top = 30.dp, start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(R.drawable.baseline_checklist_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "  Valor total",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 10.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = detalhesPedido,
                                    fontSize = 10.sp,
                                )
                            }

                        }
                    }
                }
                if (!expandedEnderecos) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Frete: ",
                            fontSize = 15.sp,
                        )
                        Text(
                            text = if (tipoEntrega == "Expressa") numberFormat.format(valorFrete) else "Grátis",
                            fontSize = 15.sp,
                            color = if (tipoEntrega == "Expressa") Color.Black else Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Valor total: ",
                            fontSize = 15.sp,
                        )
                        Text(
                            text = numberFormat.format(valorTotalPedido + valorFrete),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    ElevatedButton(
                        onClick = {

                            internetCheker = possuiConexao(ctx)

                            if (enderecoParaEntrega != null) {
                                if (internetCheker) {
                                    val resumoCompra =
                                        "$detalhesPedido\n\nForma de pagamento: $tipoPagamento\nTipo de entrega: $tipoEntrega\n\nValor Total: ${
                                            numberFormat.format(
                                                valorTotalPedido + valorFrete
                                            )
                                        }"

                                    val venda =
                                        Venda(
                                            dataPedido = LocalDate.now(),
                                            status = "Em processamento",
                                            detalhesPedido = resumoCompra,
                                            formaPagamento = tipoPagamento,
                                            idCarrinho = clienteLogado.idCarrinho
                                        )

                                    val finalizarPedido = FinalizarPedido(venda, itensList)

                                    finalizarPedido.sendFinalizarPedido(
                                        object : FinalizarPedido.Callback {
                                            override fun onSuccess(code: String) {
                                                Log.i(
                                                    "CODIGO RECEBIDO (sucesso finalizar pedido): ",
                                                    code
                                                )

                                                if (code == "1") {
                                                    clickFinalizarPedido()
                                                    clickedCompraFinalizada = true
                                                }
                                            }

                                            override fun onFailure(e: IOException) {
                                                // erro de rede
                                                // não é possível mostrar um Toast de um Thread
                                                // que não seja UI, então é feito dessa forma
                                                Handler(Looper.getMainLooper()).post {
                                                    Toast.makeText(
                                                        ctx,
                                                        "Erro de rede.",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                                Log.e("Erro: ", e.message.toString())
                                            }
                                        }
                                    )
                                }
                            } else {
                                Toast.makeText(
                                    ctx,
                                    "Selecione um endereço para entrega",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(
                                0xFF00E20A
                            )
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,

                            ) {
                            Text(
                                text = "FINALIZAR",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                            Image(
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                contentDescription = "Finalizar compra",
                            )
                        }
                    }
                }
            }
        }

    }
}