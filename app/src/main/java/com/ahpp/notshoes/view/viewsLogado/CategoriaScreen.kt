package com.ahpp.notshoes.view.viewsLogado

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.util.screensReutilizaveis.ResultadosBuscaCategoriaScreen
import com.ahpp.notshoes.util.categoriaSelecionada

@Composable
fun CategoriaScreen() {

    // manter a posicao do scroll ao voltar pra tela
    val scrollState = rememberScrollState()

    val tamanhoSpacer = 15.dp
    val tamanhoPaddingBotton = 10.dp
    val tamanhoFonte = 22.sp
    val elevationCards = 2.dp
    val tamanhoPaddingLinha = 5.dp
    val corCards = Color(0xFFFFFFFF)
    val corBackground = (
            Brush.verticalGradient(
                listOf(
                    Color(0xFFFFFFFF),
                    Color(0xFF86D0E2),
                    Color(0xFFFFFFFF)
                )
            ))

    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        //esse onBackPressed() pode ser chamado la no ResultadosBuscaCategoria() para voltar para a tela
        // anterior ele altera o valor de clicked para false, assim caindo no else aqui em baixo
        // e voltando pra tela categoria
        ResultadosBuscaCategoriaScreen(onBackPressed = { clicked = false }, categoriaSelecionada)
    } else {
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
                    .background(Color(0xFF029CCA))
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
                        Color(0xFFFFFFFF)
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
                                categoriaSelecionada = "Regata"
                                clicked = true
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
                            contentDescription = "Acessar categoria Regata"
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
                                categoriaSelecionada = "Camisa Básica"
                                clicked = true
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
                                categoriaSelecionada = "Calça"
                                clicked = true
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
                                categoriaSelecionada = "Cueca"
                                clicked = true
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
                                categoriaSelecionada = "Short"
                                clicked = true
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
                                categoriaSelecionada = "Tênis"
                                clicked = true
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
                                categoriaSelecionada = "Adidas"
                                clicked = true
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
                                categoriaSelecionada = "Fila"
                                clicked = true
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
                                categoriaSelecionada = "Mizuno"
                                clicked = true
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
                                categoriaSelecionada = "Nike"
                                clicked = true
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
                                categoriaSelecionada = "Olympikus"
                                clicked = true
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
                                categoriaSelecionada = "Puma"
                                clicked = true
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
}