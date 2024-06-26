package com.ahpp.notshoes.view.viewsLogado.viewsCarrinho

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.data.carrinho.getItensCarrinho
import com.ahpp.notshoes.data.carrinho.getProdutoCarrinho
import com.ahpp.notshoes.model.ItemCarrinho
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.barraValoresCarrinho
import com.ahpp.notshoes.ui.theme.textoValorEconomizado
import com.ahpp.notshoes.ui.theme.verde
import com.ahpp.notshoes.util.cards.CardItemCarrinho
import com.ahpp.notshoes.util.funcoes.carrinho.adicionarUnidade
import com.ahpp.notshoes.util.funcoes.carrinho.calcularValorCarrinhoComDesconto
import com.ahpp.notshoes.util.funcoes.carrinho.calcularValorCarrinhoTotal
import com.ahpp.notshoes.util.funcoes.carrinho.removerProduto
import com.ahpp.notshoes.util.funcoes.carrinho.removerUnidade
import com.ahpp.notshoes.util.funcoes.conexao.possuiConexao
import com.ahpp.notshoes.util.viewModel.CarrinhoViewModel
import com.ahpp.notshoes.view.screensReutilizaveis.SemConexaoScreen
import com.ahpp.notshoes.view.viewsDeslogado.clienteLogado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat

@Composable
fun CarrinhoScreen(navControllerCarrinho: NavController, carrinhoViewModel: CarrinhoViewModel) {

    var internetCheker by remember { mutableStateOf(false) }

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    var itemList by remember { mutableStateOf(emptyList<ItemCarrinho>()) }
    var produtosList by remember { mutableStateOf(emptyList<Produto>()) }

    var valorTotal by remember { mutableDoubleStateOf(0.0) }
    var valorTotalComDesconto by remember { mutableDoubleStateOf(0.0) }

    var detalhesPedido by remember { mutableStateOf("") }

    // criei esse combinedList porque preciso da lista de produtos aqui para calcular
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
                    itemList = itens
                    produtosList = produtos
                    combinedList = combined
                    valorTotal = calcularValorCarrinhoTotal(itemList, produtosList)
                    valorTotalComDesconto =
                        calcularValorCarrinhoComDesconto(itemList, produtosList)

                    detalhesPedido = combinedList.joinToString(separator = "\n\n") { item ->
                        val valorComDesconto =
                            item.second.preco.toDouble() - (item.second.preco.toDouble() * item.second.desconto.toDouble())
                        "(${item.first.quantidade}) ${item.second.nomeProduto} - R$ ${
                            numberFormat.format(
                                valorComDesconto * item.first.quantidade
                            )
                        }"
                    }
                    //salvar os dados no viewModel para recuperar na tela finalidarPedidoScreen
                    carrinhoViewModel.selecionaritemList(itemList)
                    carrinhoViewModel.selecionardetalhesPedido(detalhesPedido)
                    carrinhoViewModel.selecionarvalorTotalComDesconto(valorTotalComDesconto)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        atualizarLista()
    }

    if (!internetCheker) {
        SemConexaoScreen(onBackPressed = {
            atualizarLista()
        })
    } else {
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = {
                    openDialog.value = false
                },
                icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                title = {
                    Text(
                        text = stringResource(R.string.dados_incompletos),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        stringResource(R.string.complete_seus_dados_para_prosseguir),
                        textAlign = TextAlign.Start
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            navControllerCarrinho.navigate("atualizarDadosPessoaisScreen") {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text(stringResource(R.string.confirmar), color = Color.Black)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(stringResource(R.string.cancelar), color = Color.Black)
                    }
                }
            )
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
                    .height(60.dp)
                    .background(azulEscuro)
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.carrinho),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
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
                                text = stringResource(R.string.seu_carrinho_esta_vazio),
                                fontSize = 25.sp,
                                color = azulEscuro
                            )
                        }
                    }
                }
            }

            if (itemList.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(barraValoresCarrinho)
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
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(
                            modifier = Modifier
                                .width(270.dp),
                            text = "Com desconto: ${numberFormat.format(valorTotalComDesconto)}",
                            fontSize = 15.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(
                            modifier = Modifier
                                .width(270.dp),
                            text = "Você irá economizar: ${numberFormat.format(valorTotal - valorTotalComDesconto)}",
                            fontSize = 11.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            color = textoValorEconomizado
                        )
                    }
                    ElevatedButton(
                        onClick = {
                            if (itemList.isNotEmpty() && clienteLogado.cpf != "" && clienteLogado.telefoneContato != "") {
                                atualizarLista()
                                navControllerCarrinho.navigate("finalizarPedidoScreen") {
                                    launchSingleTop = true
                                }
                            } else {
                                openDialog.value = true
                            }
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = verde)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,

                            ) {
                            Text(
                                text = stringResource(R.string.finalizar),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Icon(
                                tint = Color.White.copy(alpha = 0.9f),
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                contentDescription = stringResource(R.string.finalizar_compra_description),
                            )
                        }
                    }
                }
            }
        }
    }
}