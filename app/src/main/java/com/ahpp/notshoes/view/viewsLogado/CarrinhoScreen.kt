package com.ahpp.notshoes.view.viewsLogado

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.carrinho.getItensCarrinho
import com.ahpp.notshoes.model.ItemCarrinho
import com.ahpp.notshoes.util.cards.CardItemCarrinho
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.util.funcoes.carrinho.adicionarUnidade
import com.ahpp.notshoes.util.funcoes.carrinho.removerProduto
import com.ahpp.notshoes.util.funcoes.carrinho.removerUnidade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CarrinhoScreen() {

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    var itensList by remember { mutableStateOf(emptyList<ItemCarrinho>()) }

    val ctx = LocalContext.current

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            itensList = getItensCarrinho(clienteLogado.idCarrinho)
        }
    }

    fun atualizarLista() {
        scope.launch(Dispatchers.IO) {
            itensList = getItensCarrinho(clienteLogado.idCarrinho)
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
                .height(50.dp)
                .background(Color(0xFF029CCA)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFF86D0E2),
                            Color(0xFFFFFFFF),
                        )
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                if (itensList.isNotEmpty()) {
                    LazyColumn(state = listState) {
                        items(items = itensList, key = { it.idProduto }) { item ->
                            CardItemCarrinho(
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
    }
}

