package com.ahpp.notshoes.view.viewsLogado.viewsCategoria

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahpp.notshoes.R
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.branco
import com.ahpp.notshoes.view.screensReutilizaveis.ResultadosScreen

@Composable
fun CategoriaScreenController() {
    val navControllerCategoria = rememberNavController()
    NavHost(navController = navControllerCategoria, startDestination = "categoriaScreen") {

        composable(route = "categoriaScreen") {
            CategoriaScreen(navControllerCategoria)
        }

        composable(route = "resultadosScreen/{valorBusca}/{tipoBusca}/{fromScreen}",
            arguments = listOf(
                navArgument("valorBusca") { type = NavType.StringType },
                navArgument("tipoBusca") { type = NavType.StringType },
                navArgument("fromScreen") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val valorBusca = backStackEntry.arguments?.getString("valorBusca")
            val tipoBusca = backStackEntry.arguments?.getString("tipoBusca")
            val fromScreen = backStackEntry.arguments?.getString("fromScreen")
            ResultadosScreen(navControllerCategoria, valorBusca.toString(), tipoBusca.toString(), fromScreen.toString())
        }
    }
}

@Composable
fun CategoriaScreen(navControllerCategoria: NavController) {

    // manter a posicao do scroll ao voltar pra tela
    val scrollState = rememberSaveable(saver = ScrollState.Saver) { ScrollState(0) }

    val tamanhoSpacer = 15.dp
    val tamanhoPaddingBotton = 10.dp
    val tamanhoFonte = 22.sp
    val elevationCards = 10.dp
    val tamanhoPaddingLinha = 5.dp
    val corCards = branco
    val corBackground = branco

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corBackground)
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
                .background(azulEscuro)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .width(270.dp),
                text = "Categorias", fontSize = 20.sp, maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    branco
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(scrollState)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(top = 10.dp, bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Regata/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_regata),
                            contentDescription = "Categoria Regata"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Regata",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Regata",
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Camisa Básica/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }
                    ),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_camisa_basica),
                            contentDescription = "Categoria Camisa Básica"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Camisa Básica",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Camisa Básica"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Calça/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_calca),
                            contentDescription = "Categoria Calça"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Calça",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Calça"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Cueca/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_cueca),
                            contentDescription = "Categoria Cueca"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Cueca",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Cueca"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Short/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_short),
                            contentDescription = "Categoria Short"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Short",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Short"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Tênis/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_tenis),
                            contentDescription = "Categoria Tênis"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Tênis",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Tênis"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Adidas/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_adidas),
                            contentDescription = "Categoria Produtos Adidas"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Adidas",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Produtos Adidas"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Fila/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_fila),
                            contentDescription = "Categoria Produtos Fila"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Fila",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Produtos Fila"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Mizuno/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_mizuno),
                            contentDescription = "Categoria Produtos Mizuno"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Mizuno",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Produtos Mizuno"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Nike/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_nike),
                            contentDescription = "Categoria produtos Nike"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Nike",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Produtos Nike"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Olympikus/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_olympikus),
                            contentDescription = "Categoria Produtos Olympikus"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Olympikus",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Produtos Olympikus"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = tamanhoPaddingBotton)
                    .clickable(
                        enabled = true,
                        onClick = {
                            navControllerCategoria.navigate("resultadosScreen/Puma/categoria/categoriaScreen") {
                                launchSingleTop = true
                            }
                        }),
                elevation = CardDefaults.cardElevation(elevationCards)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corCards)
                        .padding(tamanhoPaddingLinha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            painter = painterResource(id = R.drawable.img_categoria_puma),
                            contentDescription = "Categoria Produtos Puma"
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = "Produtos Puma",
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "Acessar categoria Produtos Puma"
                    )
                }
            }
        }
    }
}