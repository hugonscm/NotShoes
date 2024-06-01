package com.ahpp.notshoes.view.viewsLogado

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.produto.ProdutoRepository
import kotlinx.coroutines.delay
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ahpp.notshoes.util.screensReutilizaveis.ProdutoScreen
import com.ahpp.notshoes.util.screensReutilizaveis.ResultadosBuscaCategoriaScreen
import com.ahpp.notshoes.util.screensReutilizaveis.ResultadosBuscaNomeScreen
import com.ahpp.notshoes.util.categoriaSelecionada
import com.ahpp.notshoes.util.produtoSelecionado
import com.ahpp.notshoes.util.textoBusca
import java.text.NumberFormat

@Composable
fun InicioScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    // manter a posicao do scroll ao voltar pra tela
    val scrollState = rememberScrollState()

    var clickedPesquisa by remember { mutableStateOf(false) }
    var clickedCategoria by remember { mutableStateOf(false) }
    var clickedProduto by remember { mutableStateOf(false) }

    if (clickedPesquisa) {
        //esse onBackPressed() é chamado la no ResultadosBuscaNome() para voltar para a tela
        // anterior ele altera o valor de clicked para false, assim caindo no else aqui em baixo
        // e voltando pra tela inicio
        ResultadosBuscaNomeScreen(
            onBackPressed = { clickedPesquisa = false },
            textoBusca
        )
    } else if (clickedCategoria) {
        ResultadosBuscaCategoriaScreen(
            onBackPressed = { clickedCategoria = false },
            categoriaSelecionada
        )
    } else if (clickedProduto) {
        ProdutoScreen(
            onBackPressed = { clickedProduto = false },
        )
    } else {
        //funcionalidade "toque novamente pra sair"
        //dessa forma só atinte a tela inicio
        var backPressedOnce = false
        val ctx = LocalContext.current
        val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Fecha o aplicativo ao clicar em voltar em menos de 2s
                    if (backPressedOnce) {
                        (ctx as? Activity)?.finish()
                    } else {
                        backPressedOnce = true
                        Toast.makeText(
                            ctx,
                            "Toque em voltar mais uma vez para sair.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed(
                            { backPressedOnce = false },
                            2000
                        )
                    }
                }
            }
        }
        // callback para interceptar o evento de voltar
        DisposableEffect(onBackPressedDispatcher) {
            onBackPressedDispatcher?.onBackPressedDispatcher?.addCallback(backCallback)
            onDispose {
                backCallback.remove()
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            SearchBar(onSearchClicked = { clickedPesquisa = true })
            PagerDescontos()
            PagerFiltroValores()
            FiltrosTelaInicial(navController, onIconClicked = { clickedCategoria = true })
            Promocoes(onPromocaoClicked = { clickedProduto = true })
            NavegarPorMarcas(onMarcaClicked = { clickedCategoria = true })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearchClicked: () -> Unit) {

    var text by remember { mutableStateOf("") }

    SearchBar(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 10.dp),
        colors = SearchBarDefaults.colors(Color(0xFFCAD4D6)),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            if (text.isNotEmpty()) {
                textoBusca = text
                onSearchClicked()
            }
        },
        active = false,
        onActiveChange = { },
        placeholder = { Text(text = "Busque um produto!", fontSize = 14.sp) },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        textoBusca = text
                        onSearchClicked()
                    }
                },
                enabled = true
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar produtos.",
                )
            }
        }
    ) {
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerDescontos() {
    val images = listOf(
        painterResource(R.drawable.img_nike_day),
        painterResource(R.drawable.img_promocoes),
        painterResource(R.drawable.img_air_jordan),
    )

    val pagerState =
        androidx.compose.foundation.pager.rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .clickable(enabled = true, onClick = {}),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .height(245.dp),
                        painter = images[currentPage],
                        contentDescription = ""
                    )
                }
            }
        }
        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
        )

    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 5.dp)
            //coloquei essa altura aqui fixa porque o IndicatorDots vai alterando de tamanho,
            // e isso ficava mexendo no tamanho geral da tela
            .height(18.dp)
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerFiltroValores() {
    val images = listOf(
        painterResource(R.drawable.img_valor_ate_199),
        painterResource(R.drawable.img_valor_200_ate_399),
        painterResource(R.drawable.img_valor_400_ate_599),
        painterResource(R.drawable.img_valor_acima_600)
    )

    val pagerState =
        androidx.compose.foundation.pager.rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier.padding(top = 10.dp),
    ) {
        Box {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 35.dp)
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .clickable(enabled = true, onClick = {}),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .height(58.dp)
                            .width(332.dp),
                        painter = images[currentPage],
                        contentDescription = "",
                    )
                }
            }
        }
    }
}

@Composable
fun FiltrosTelaInicial(navController: NavHostController, onIconClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier.size(50.dp), contentPadding = PaddingValues(0.dp),
                onClick = {
                    categoriaSelecionada = "Camisa Básica"
                    onIconClicked()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_shirt),
                    contentDescription = "Icone ver produtos da categoria camisa básica.",
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                "Camisa básica",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier.size(50.dp), contentPadding = PaddingValues(0.dp),
                onClick = {
                    categoriaSelecionada = "Calça"
                    onIconClicked()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_pants),
                    contentDescription = "Icone ver produtos da categoria calças.",
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                "Calça",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier.size(50.dp), contentPadding = PaddingValues(0.dp),
                onClick = {
                    categoriaSelecionada = "Tênis"
                    onIconClicked()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_shoes),
                    contentDescription = "Icone ver produtos da categoria tênis.",
                    modifier = Modifier.size(35.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                "Tênis",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier.size(50.dp), contentPadding = PaddingValues(0.dp),
                onClick = {
                    navController.navigate("categorias") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // // evitar abrir novamente a mesma tela ao reselecionar mesmo item
                        launchSingleTop = true
                        // restaura o estado ao voltar para a tela anterior
                        restoreState = true
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = "Icone ir para tela de categorias.",
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                "Ver Tudo",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Promocoes(onPromocaoClicked: () -> Unit) {

    val repository = ProdutoRepository()
    val ofertas = repository.getPromocoes()

    val localeBR = java.util.Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBR)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
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
                        text = "PROMOÇÕES",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = Color.Black)
                    )
                    Text(text = "Enquanto durar o estoque!", fontSize = 12.sp)
                }
            }
        }

        if (ofertas.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Que pena!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
                Text(
                    text = "Nenhuma promoção ativa.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                LazyRow {
                    items(items = ofertas) { produtoEmPromocao ->
                        //imagem do produto
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(produtoEmPromocao.imagemProduto)
                                .crossfade(true)
                                .size(coil.size.Size.ORIGINAL)
                                .build()
                        )
                        //estado para monitorar se deu erro ou nao
                        val state = painter.state

                        Card(
                            shape = RoundedCornerShape(3.dp),
                            colors = CardColors(
                                containerColor = Color.White,
                                Color.Black,
                                Color.Black,
                                Color.Black
                            ),
                            modifier = Modifier
                                .height(165.dp)
                                .padding(horizontal = 3.dp)
                                .clickable(enabled = true, onClick = {
                                    produtoSelecionado = produtoEmPromocao
                                    onPromocaoClicked()
                                }),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                Modifier
                                    .width(130.dp)
                                    .padding(5.dp)
                            ) {
                                when (state) {
                                    is AsyncImagePainter.State.Loading -> {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(
                                                Alignment.CenterHorizontally
                                            )
                                        )
                                    }

                                    is AsyncImagePainter.State.Error -> {
                                        Image(
                                            Icons.Default.Close,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(100.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    else -> {
                                        Image(
                                            painter = painter,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(100.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                                Text(
                                    text = produtoEmPromocao.nomeProduto,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "De: ${numberFormat.format(produtoEmPromocao.preco.toDouble())}",
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 10.sp
                                )

                                val valorComDesconto =
                                    produtoEmPromocao.preco.toDouble() - ((produtoEmPromocao.preco.toDouble() * produtoEmPromocao.desconto.toDouble()))

                                Text(
                                    text = "Por: ${numberFormat.format(valorComDesconto)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                if (produtoEmPromocao.estoqueProduto > 0) {
                                    Text(
                                        text = "Restam ${produtoEmPromocao.estoqueProduto} unidades!",
                                        fontSize = 10.sp
                                    )
                                } else {
                                    Text(
                                        text = "Estoque esgotado :(",
                                        fontSize = 10.sp,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavegarPorMarcas(onMarcaClicked: () -> Unit) {

    //valores para usar na altura, largura e padding horizontal das imagens que estao na Row
    val dpHeightItens = 70.dp
    val dpWidthItens = 130.dp
    val dpPaddingHorizontalItens = 10.dp

    Column(Modifier.padding(top = 20.dp)) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "NAVEGUE POR MARCAS",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(color = Color.Black)
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 5.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(5.dp))
                .horizontalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_adidas),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Adidas"
                        onMarcaClicked()
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.logo_fila),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Fila"
                        onMarcaClicked()
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.logo_mizuno),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Mizuno"
                        onMarcaClicked()
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.logo_nike),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Nike"
                        onMarcaClicked()
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.logo_olympikus),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Olympikus"
                        onMarcaClicked()
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.logo_puma),
                contentDescription = null,
                modifier = Modifier
                    .width(dpWidthItens)
                    .height(dpHeightItens)
                    .padding(horizontal = dpPaddingHorizontalItens)
                    .clickable(enabled = true, onClick = {
                        categoriaSelecionada = "Puma"
                        onMarcaClicked()
                    })
            )
        }
    }
}