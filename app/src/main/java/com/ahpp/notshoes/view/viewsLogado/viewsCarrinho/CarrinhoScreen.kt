package com.ahpp.notshoes.view.viewsLogado.viewsCarrinho

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.carrinho.getItensCarrinho
import com.ahpp.notshoes.bd.carrinho.getProdutoCarrinho
import com.ahpp.notshoes.model.ItemCarrinho
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.util.cards.CardItemCarrinho
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.util.funcoes.carrinho.adicionarUnidade
import com.ahpp.notshoes.util.funcoes.carrinho.calcularValorCarrinhoComDesconto
import com.ahpp.notshoes.util.funcoes.carrinho.calcularValorCarrinhoTotal
import com.ahpp.notshoes.util.funcoes.carrinho.removerProduto
import com.ahpp.notshoes.util.funcoes.carrinho.removerUnidade
import com.ahpp.notshoes.util.funcoes.possuiConexao
import com.ahpp.notshoes.util.screensReutilizaveis.SemConexaoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat

@Composable
fun CarrinhoScreen() {

    var internetCheker by remember { mutableStateOf(false) }

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    var clickedFinalizarPedido by remember { mutableStateOf(false) }

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    var itensList by remember { mutableStateOf(emptyList<ItemCarrinho>()) }
    var produtosList by remember { mutableStateOf(emptyList<Produto>()) }

    var valorTotal by remember { mutableDoubleStateOf(0.0) }
    var valorTotalComDesconto by remember { mutableDoubleStateOf(0.0) }

    // criei esse combinedList porque preciso da lista de produtos aqui tambem para calcular
    // os valores da compra total, e aproveitando isso passei o produto para o cardItemCarrinho
    // para ele nao precisar buscar o produto no banco la dentro tambem
    var combinedList by remember { mutableStateOf(emptyList<Pair<ItemCarrinho, Produto>>()) }

    val ctx = LocalContext.current

    val scope = rememberCoroutineScope()

    fun atualizarLista() {
        internetCheker = possuiConexao(ctx)
        if (internetCheker) {
            scope.launch(Dispatchers.IO) {
                val itens = getItensCarrinho(clienteLogado.idCarrinho)
                val produtos = getProdutoCarrinho(clienteLogado.idCarrinho)

                // Combine itensList e produtosList
                val combined = itens.mapNotNull { item ->
                    val produto = produtos.find { it.idProduto == item.idProduto }
                    if (produto != null) {
                        item to produto
                    } else {
                        null
                    }
                }

                withContext(Dispatchers.Main) {
                    itensList = itens
                    produtosList = produtos
                    combinedList = combined
                    valorTotal = calcularValorCarrinhoTotal(itensList, produtosList)
                    valorTotalComDesconto =
                        calcularValorCarrinhoComDesconto(itensList, produtosList)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        atualizarLista()
    }

    if (clickedFinalizarPedido) {

        // resumo do pedido
        val detalhesPedido = combinedList.joinToString(separator = "\n\n") { item ->
            val valorComDesconto =
                item.second.preco.toDouble() - (item.second.preco.toDouble() * item.second.desconto.toDouble())
            "(${item.first.quantidade}) ${item.second.nomeProduto} - R$ ${
                numberFormat.format(
                    valorComDesconto * item.first.quantidade
                )
            }"
        }
        FinalizarPedidoScreen(
            itensList,
            detalhesPedido,
            valorTotalComDesconto,
            onBackPressed = { clickedFinalizarPedido = false },
            clickFinalizarPedido = { atualizarLista() })

    } else if (internetCheker) {
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
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(270.dp),
                    text = "Carrinho", fontSize = 20.sp, maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        Color(0xFFFFFFFF)
                    )
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(
                        Color.White
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {

                    if (combinedList.isNotEmpty()) {
                        LazyColumn(state = listState) {
                            items(
                                items = combinedList,
                                key = { it.first.idProduto }) { (item, produto) ->
                                CardItemCarrinho(
                                    produto = produto,
                                    item = item,
                                    onRemoveProduto = { produtoToRemove ->
                                        removerProduto(ctx, produtoToRemove) { atualizarLista() }
                                    },
                                    adicionarUnidade = { itemToAdd ->
                                        adicionarUnidade(ctx, itemToAdd) { atualizarLista() }
                                    },
                                    removerUnidade = { itemToRemove ->
                                        removerUnidade(ctx, itemToRemove) { atualizarLista() }
                                    }
                                )
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
                                text = "Seu carrinho está vazio.",
                                fontSize = 25.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            if (itensList.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color(0xFFBFD570))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .width(270.dp),
                            text = "Total: ${numberFormat.format(valorTotal)}",
                            fontSize = 10.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                Color(0xFF000000)
                            )
                        )
                        Text(
                            modifier = Modifier
                                .width(270.dp),
                            text = "Com desconto: ${numberFormat.format(valorTotalComDesconto)}",
                            fontSize = 15.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                Color(0xFF000000)
                            )
                        )
                        Text(
                            modifier = Modifier
                                .width(270.dp),
                            text = "Você irá economizar: ${numberFormat.format(valorTotal - valorTotalComDesconto)}",
                            fontSize = 11.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                Color(0xFF009688)
                            )
                        )
                    }
                    ElevatedButton(
                        onClick = { if (itensList.isNotEmpty()) clickedFinalizarPedido = true },
                        modifier = Modifier
                            .width(110.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E20A))
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
    } else {
        SemConexaoScreen(onBackPressed = {
            atualizarLista()
        })
    }
}
