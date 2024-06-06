package com.ahpp.notshoes.view.screensReutilizaveis

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ahpp.notshoes.bd.produto.ProdutoRepository
import com.ahpp.notshoes.view.clienteLogado
import com.ahpp.notshoes.util.funcoes.possuiConexao
import com.ahpp.notshoes.util.funcoes.produto.adicionarListaDesejos
import com.ahpp.notshoes.util.funcoes.produto.adicionarProdutoCarrinho
import com.ahpp.notshoes.view.produtoSelecionado
import java.text.NumberFormat

// essa tela esta vinculada aos produtos que estao em promoçao na tela de inicio
// e tambem aos produtos que estao na lista de desejos, portanto so tem callback
// para voltar pra tela anterior, e ela remove/adiciona o produto na lista de desejos aqui mesmo
@SuppressLint("DefaultLocale")
@Composable
fun ProdutoScreen(onBackPressed: () -> Unit) {

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    val ctx = LocalContext.current

    // backhandler eh ativado quando o usuario aperta em voltar no aparelho
    // para evitar que volte direto pra tela inicio
    BackHandler {
        onBackPressed()
    }

    var adicionadoListaDesejosCheck by remember { mutableStateOf<String?>(null) }

    val produtoRepository = ProdutoRepository()

    LaunchedEffect(Unit) {
        if (possuiConexao(ctx)) {
            produtoRepository.verificarProdutoListaDesejos(
                produtoSelecionado.idProduto,
                clienteLogado.idListaDesejos
            ) {
                adicionadoListaDesejosCheck = it
            }
        }
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(produtoSelecionado.imagemProduto)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    )
    //estado para monitorar se deu erro ou nao
    val state = painter.state

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)
        )
        //essa row tem os 2 botoes que ficam no topo da tela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF029CCA)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp, end = 10.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onBackPressed() },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    Icons.Default.Close,
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp, end = 10.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (possuiConexao(ctx)) {
                        adicionadoListaDesejosCheck = adicionadoListaDesejosCheck?.let {
                            adicionarListaDesejos(
                                it, produtoSelecionado
                            )
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(ctx, "Sem conexão com a internet.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(if (adicionadoListaDesejosCheck != "1") R.drawable.baseline_favorite_border_24 else R.drawable.baseline_favorite_filled_24),
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
                            .padding(top = 10.dp)
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
                            .padding(top = 10.dp)
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
                            .padding(top = 10.dp)
                    )
                }
            }
            Column {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    text = produtoSelecionado.nomeProduto,
                    fontSize = 25.sp,
                )

                if (produtoSelecionado.emOferta) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(50.dp)
                            .background(Color(0xFF59D35E))
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
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = numberFormat.format(produtoSelecionado.preco.toDouble()),
                                textDecoration = TextDecoration.LineThrough,
                                style = TextStyle(Color.Gray),
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val percentualDesconto = 100 * produtoSelecionado.desconto.toDouble()
                            val percentualDescontoFormated =
                                String.format("%.0f", percentualDesconto)
                            Text(
                                text = "-$percentualDescontoFormated%",
                                style = TextStyle(Color(0xFF00E20A)),
                                fontSize = 15.sp
                            )
                        }

                        val valorComDesconto =
                            produtoSelecionado.preco.toDouble() - ((produtoSelecionado.preco.toDouble() * produtoSelecionado.desconto.toDouble()))

                        Row(modifier = Modifier, verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = numberFormat.format(valorComDesconto),
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
                                modifier = Modifier.padding(top = 5.dp),
                                text = "Em estoque. Envio imediato!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        } else {
                            Text(
                                modifier = Modifier.padding(top = 5.dp),
                                text = "Estoque esgotado :(",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.Red
                            )
                        }
                    }

                    ElevatedButton(
                        onClick = {
                            if (possuiConexao(ctx)) {
                                adicionarProdutoCarrinho(ctx, produtoSelecionado)
                            } else {
                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(
                                        ctx,
                                        "Sem conexão com a internet.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(95.dp)
                            .height(55.dp)
                            .align(Alignment.CenterVertically),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E20A))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                            contentDescription = "Adicionar ao carrinho",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = "Detalhes do produto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )

                Text(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    ),
                    text = produtoSelecionado.descricao,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

// essa tela esta vinculada as telas de resultados de busca por nome e categoria
// ela tem callback para atualizar o icone de favorito do produto na tela anterior
@SuppressLint("DefaultLocale")
@Composable
fun ProdutoScreen(
    onBackPressed: () -> Unit, favoritado: String,
    onFavoritoClick: (String) -> Unit
) {

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    val ctx = LocalContext.current

    // backhandler eh ativado quando o usuario aperta em voltar no aparelho
    // para evitar que volte direto pra tela inicio
    BackHandler {
        onBackPressed()
    }


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(produtoSelecionado.imagemProduto)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    )
    //estado para monitorar se deu erro ou nao
    val state = painter.state

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)
        )
        //essa row tem os 2 botoes que ficam no topo da tela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF029CCA)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp, end = 10.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onBackPressed() },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    Icons.Default.Close,
                    contentDescription = "Toque para voltar",
                    modifier = Modifier.size(30.dp)
                )
            }
            Button(
                modifier = Modifier
                    .size(65.dp)
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp, end = 10.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (possuiConexao(ctx)) {
                        onFavoritoClick(favoritado)
                    } else {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(ctx, "Sem conexão com a internet.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(if (favoritado != "1") R.drawable.baseline_favorite_border_24 else R.drawable.baseline_favorite_filled_24),
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
                            .padding(top = 10.dp)
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
                            .padding(top = 10.dp)
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
                            .padding(top = 10.dp)
                    )
                }
            }
            Column {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    text = produtoSelecionado.nomeProduto,
                    fontSize = 25.sp,
                )

                if (produtoSelecionado.emOferta) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(50.dp)
                            .background(Color(0xFF59D35E))
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
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = numberFormat.format(produtoSelecionado.preco.toDouble()),
                                textDecoration = TextDecoration.LineThrough,
                                style = TextStyle(Color.Gray),
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val percentualDesconto = 100 * produtoSelecionado.desconto.toDouble()
                            val percentualDescontoFormated =
                                String.format("%.0f", percentualDesconto)
                            Text(
                                text = "-$percentualDescontoFormated%",
                                style = TextStyle(Color(0xFF00E20A)),
                                fontSize = 15.sp
                            )
                        }

                        val valorComDesconto =
                            produtoSelecionado.preco.toDouble() - ((produtoSelecionado.preco.toDouble() * produtoSelecionado.desconto.toDouble()))

                        Row(modifier = Modifier, verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = numberFormat.format(valorComDesconto),
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
                                modifier = Modifier.padding(top = 5.dp),
                                text = "Em estoque. Envio imediato!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        } else {
                            Text(
                                modifier = Modifier.padding(top = 5.dp),
                                text = "Estoque esgotado :(",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.Red
                            )
                        }
                    }

                    ElevatedButton(
                        onClick = {
                            if (possuiConexao(ctx)) {
                                adicionarProdutoCarrinho(ctx, produtoSelecionado)
                            } else {
                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(
                                        ctx,
                                        "Sem conexão com a internet.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(95.dp)
                            .height(55.dp)
                            .align(Alignment.CenterVertically),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E20A))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                            contentDescription = "Adicionar ao carrinho",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = "Detalhes do produto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )

                Text(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    ),
                    text = produtoSelecionado.descricao,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}