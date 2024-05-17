package com.ahpp.notshoes.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.ProdutosRepository
import com.ahpp.notshoes.view.ProdutoScreen

//esse ResultadosBuscaNome é usado quando o usuário busca por nome do produto
@Composable
fun ResultadosBuscaNome(onBackPressed: () -> Unit, nome: String) {

    //clickedProduto é usado para monitorar a tela de produto selecionado
    //ela se torna true quando um produto é clicado lá em CardResultados()
    //e false quando clica em voltar na tela de produto selecionado

    var clickedProduto by remember { mutableStateOf(false) }

    if (clickedProduto) {
        ProdutoScreen(
            onBackPressed = { clickedProduto = false },
        )
    } else {

        val repository = ProdutosRepository()
        val produtosList = repository.buscarProdutoNome(nome)

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
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp), contentPadding = PaddingValues(0.dp),
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
                modifier = Modifier.padding(top = 15.dp, start = 10.dp, bottom = 5.dp),
                text = "Resultados para $nome:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                if (produtosList.isNotEmpty()) {
                    LazyColumn {
                        items(items = produtosList) { produto ->
                            CardResultados(onClickProduto = { clickedProduto = true }, produto)
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
                            text = "Nenhum produto encontrado.",
                            fontSize = 28.sp,
                        )
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = "Tente novamente com outro termo para busca.",
                            fontSize = 15.sp,
                        )
                    }
                }

            }

        }
    }
}

//esse ResultadosBuscaCategoria é usado quando o usuário clica em alguma categoria
@Composable
fun ResultadosBuscaCategoria(onBackPressed: () -> Unit, categoriaSelecionada: String) {

    //clickedProduto é usado para monitorar a tela de produto selecionado
    //ela se torna true quando um produto é clicado lá em CardResultados()
    //e false quando clica em voltar na tela de produto selecionado

    var clickedProduto by remember { mutableStateOf(false) }

    if (clickedProduto) {
        ProdutoScreen(
            onBackPressed = { clickedProduto = false },
        )
    } else {
        val repository = ProdutosRepository()
        val produtosList = repository.filtrarProdutoCategoria(categoriaSelecionada)

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
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp), contentPadding = PaddingValues(0.dp),
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
                modifier = Modifier.padding(top = 15.dp, start = 10.dp, bottom = 5.dp),
                text = "Resultados para $categoriaSelecionada:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                if (produtosList.isNotEmpty()) {
                    LazyColumn {
                        items(items = produtosList) { produto ->
                            CardResultados(onClickProduto = { clickedProduto = true }, produto)
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
                            text = "Nenhum produto encontrado.",
                            fontSize = 28.sp,
                        )
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = "Tente novamente com outro termo para busca.",
                            fontSize = 15.sp,
                        )
                    }
                }

            }

        }
    }
}