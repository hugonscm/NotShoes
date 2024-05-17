package com.ahpp.notshoes.view

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ahpp.notshoes.R
import com.ahpp.notshoes.util.produtoSelecionado

@SuppressLint("DefaultLocale")
@Composable
fun ProdutoScreen(onBackPressed: () -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(produtoSelecionado.fotoProduto)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    )
    //estado para monitorar se deu erro ou nao
    val state = painter.state

    Column {
        //essa row tem os 2 botoes que ficam no topo da tela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .size(55.dp), contentPadding = PaddingValues(0.dp),
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
            Button(
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.Bottom), contentPadding = PaddingValues(0.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                    contentDescription = "Adicionar aos favoritos.",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        //a coluna abaixo tem o resto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = produtoSelecionado.nomeProduto,
                    fontSize = 30.sp,
                )

                if (produtoSelecionado.emOferta) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0xFF00C4FF))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(35.dp),
                                painter = painterResource(id = R.drawable.baseline_access_alarm_24),
                                contentDescription = null,
                            )
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Este produto está em oferta. Aproveite!",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(color = Color.Black)
                                )
                                Text(text = "Enquanto durar o estoque!", fontSize = 12.sp)
                            }
                        }
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = "R$ ${produtoSelecionado.preco}",
                                textDecoration = TextDecoration.LineThrough,
                                style = TextStyle(Color.Gray),
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val percentualDesconto = 100 * produtoSelecionado.desconto.toDouble()
                            val percentualDescontoFormated = String.format("%.0f", percentualDesconto)
                            Text(
                                text = "-$percentualDescontoFormated%",
                                style = TextStyle(Color(0xFF00E20A)),
                                fontSize = 15.sp
                            )
                        }

                        val valorComDesconto =
                            produtoSelecionado.preco.toDouble() - ((produtoSelecionado.preco.toDouble() * produtoSelecionado.desconto.toDouble()))
                        val valorComDescontoFormated = String.format("%.2f", valorComDesconto)

                        Row(modifier = Modifier, verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "R$ $valorComDescontoFormated",
                                fontWeight = FontWeight.Bold,
                                fontSize = 35.sp
                            )
                            Text(
                                text = " à vista",
                                fontSize = 20.sp
                            )
                        }

                        if (produtoSelecionado.estoqueProduto > 0) {
                            Text(
                                text = "Em estoque. Envio imediato!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        } else {
                            Text(
                                text = "Estoque esgotado :(",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.Red
                            )
                        }
                    }

                    ElevatedButton(
                        onClick = { },
                        modifier = Modifier
                            .width(95.dp)
                            .height(55.dp)
                            .align(Alignment.CenterVertically),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E20A))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                            contentDescription = "Toque para voltar",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 35.dp),
                    text = "Detalhes do produto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )

                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = produtoSelecionado.descricao,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}