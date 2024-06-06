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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.produto.ProdutoRepository
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.util.cards.CardListaDesejos
import com.ahpp.notshoes.view.screensReutilizaveis.ProdutoScreen
import com.ahpp.notshoes.view.clienteLogado
import com.ahpp.notshoes.util.funcoes.possuiConexao
import com.ahpp.notshoes.view.screensReutilizaveis.SemConexaoScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ListaDeDesejoscreen() {

    val ctx = LocalContext.current

    var internetCheker by remember { mutableStateOf(possuiConexao(ctx)) }

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    //clickedProduto é usado para monitorar a tela de produto selecionado
    //ela se torna true quando um produto é clicado lá em CardResultados()
    //e false quando clica em voltar na tela de produto selecionado

    var clickedProduto by remember { mutableStateOf(false) }

    if (clickedProduto) {
        ProdutoScreen(
            onBackPressed = {
                clickedProduto = false
                internetCheker = possuiConexao(ctx)
            },
        )
    } else if (!internetCheker) {
        SemConexaoScreen(onBackPressed = {
            internetCheker = possuiConexao(ctx)
        })
    } else {

        var produtosList by remember { mutableStateOf(emptyList<Produto>()) }

        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                val repository = ProdutoRepository()
                produtosList = repository.getProdutosListaDesejos(clienteLogado.idListaDesejos)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .width(270.dp),
                    text = "Lista de desejos", fontSize = 20.sp, maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        Color(0xFFFFFFFF)
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                if (produtosList.isNotEmpty()) {
                    LazyColumn(state = listState) {
                        items(items = produtosList) { produto ->
                            CardListaDesejos(onClickProduto = { clickedProduto = true },
                                produto,
                                //atualizar a lista de desejos na tela após remover um produto
                                onRemoveProduct = { removedProduto ->
                                    produtosList =
                                        produtosList.filter { it.idProduto != removedProduto.idProduto }
                                })
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
                            text = "Sua lista de desejos está vazia.",
                            fontSize = 25.sp,
                            color = Color(0xFF029CCA)
                        )
                    }
                }

            }

        }

    }

}