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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.branco

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
                text = stringResource(R.string.categorias_titulo), fontSize = 20.sp, maxLines = 1,
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
                            contentDescription = stringResource(id = R.string.acessar_categoria_regata)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.regata),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_regata),
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
                            contentDescription = stringResource(id = R.string.acessar_categoria_camisa_basica)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.camisa_basica),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_camisa_basica)
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
                            contentDescription = stringResource(R.string.acessar_categoria_calca)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.calca),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_calca)
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
                            contentDescription = stringResource(R.string.acessar_categoria_cueca)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.cueca),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_cueca)
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
                            contentDescription = stringResource(R.string.acessar_categoria_short)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.shortt),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_short)
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
                            contentDescription = stringResource(R.string.acessar_categoria_tenis)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.tenis),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_tenis)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_adidas)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_adidas),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_adidas)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_fila)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_fila),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_fila)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_mizuno)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_mizuno),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_mizuno)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_nike)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_nike),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_nike)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_olympikus)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_olympikus),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_olympikus)
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
                            contentDescription = stringResource(R.string.acessar_categoria_produtos_puma)
                        )
                        Spacer(modifier = Modifier.width(tamanhoSpacer))
                        Text(
                            text = stringResource(R.string.produtos_puma),
                            fontWeight = FontWeight.Bold,
                            fontSize = tamanhoFonte
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = stringResource(R.string.acessar_categoria_produtos_puma)
                    )
                }
            }
        }
    }
}