package com.ahpp.notshoes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.ProdutoRepository
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.util.CardListaDesejos
import com.ahpp.notshoes.util.ProdutoScreen
import com.ahpp.notshoes.util.cliente
import kotlinx.coroutines.delay

@Composable
fun ListaDeDesejoscreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        //clickedProduto é usado para monitorar a tela de produto selecionado
        //ela se torna true quando um produto é clicado lá em CardResultados()
        //e false quando clica em voltar na tela de produto selecionado

        var clickedProduto by remember { mutableStateOf(false) }

        if (clickedProduto) {
            ProdutoScreen(
                onBackPressed = { clickedProduto = false },
            )
        } else {

            var produtosList by remember { mutableStateOf(emptyList<Produto>()) }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(500) // Aguarde 500 ms
                val repository = ProdutoRepository()
                produtosList = repository.getProdutosListaDesejos(cliente.idListaDesejos)
                isLoading = false
            }

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
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                        .background(Color.White)
//                )
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
                        text = "Lista de desejos", fontSize = 20.sp, maxLines = 1,
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
                                    Color(0xFF86D0E2),
                                    Color(0xFFFFFFFF)
                                )
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {

                        if (produtosList.isNotEmpty()) {
                            LazyColumn {
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
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}