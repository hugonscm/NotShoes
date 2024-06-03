package com.ahpp.notshoes.util.screensReutilizaveis

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.R
import com.ahpp.notshoes.bd.produto.ProdutoRepository
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.util.cards.CardResultados
import com.ahpp.notshoes.util.clienteLogado
import com.ahpp.notshoes.util.filtros.filtrarProdutos
import com.ahpp.notshoes.util.funcoes.produto.adicionarListaDesejos
import com.ahpp.notshoes.util.produtoSelecionado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//esse ResultadosBuscaNomeScreen é usado quando o usuário busca por nome do produto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadosBuscaNomeScreen(onBackPressed: () -> Unit, nomeProduto: String) {

    BackHandler {
        onBackPressed()
    }

    val coresList = listOf(
        "Cor",
        "Amarelo",
        "Anil",
        "Azul",
        "Azul Claro",
        "Azul Escuro",
        "Bege",
        "Branco",
        "Bronze",
        "Cinza",
        "Ciano",
        "Dourado",
        "Laranja",
        "Laranja Claro",
        "Laranja Escuro",
        "Magenta",
        "Marrom",
        "Preto",
        "Prata",
        "Rosa",
        "Roxo",
        "Turquesa",
        "Verde",
        "Verde Claro",
        "Verde Limão",
        "Verde Oliva",
        "Vermelho",
        "Vermelho Escuro",
        "Violeta"
    )
    var expandedCoresList by remember { mutableStateOf(false) }
    var cor by remember { mutableStateOf(coresList[0]) }

    val precosList = listOf(
        "Preço",
        "Menos de R$ 100",
        "R$ 100 até R$ 299",
        "R$ 300 até R$ 499",
        "R$ 500 até R$ 699",
        "R$ 700 até R$ 899",
        "R$ 900 até R$ 1000",
        "Acima de R$ 1000"
    )
    var expandedPrecosList by remember { mutableStateOf(false) }
    var preco by remember { mutableStateOf(precosList[0]) }

    val tamanhosList = listOf(
        "Tamanho",
        "P",
        "M",
        "G",
        "GG",
        "38",
        "39",
        "40",
        "41",
        "42",
        "43",
        "44",
        "45",
        "46",
        "47",
        "48"
    )
    var expandedTamanhosList by remember { mutableStateOf(false) }
    var tamanho by remember { mutableStateOf(tamanhosList[0]) }

    val tipoOrdenacaoList = listOf(
        "Ordenar por",
        "Menor preço",
        "Maior preço",
        "Ofertas",
    )
    var expandedtipoOrdenacao by remember { mutableStateOf(false) }
    var tipoOrdenacao by remember { mutableStateOf(tipoOrdenacaoList[0]) }

    var expandedFiltro by remember { mutableStateOf(false) }

    val repository = ProdutoRepository()
    var produtosList by remember { mutableStateOf(emptyList<Produto>()) }

    // Gerencie o estado dos favoritos aqui
    val favoritos = remember { mutableStateMapOf<Int, String>() }

    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    fun buscarProduto() {
        scope.launch(Dispatchers.IO) {
            produtosList = repository.buscarProdutoNome(nomeProduto)
            produtosList =
                filtrarProdutos(
                    produtosList,
                    cor,
                    tamanho,
                    preco,
                    tipoOrdenacao
                )

            // verifica quais produtos estao favoritados
            // 1 = sim
            // 0 = não
            produtosList.forEach { produto ->
                repository.verificarProdutoListaDesejos(
                    produto.idProduto,
                    clienteLogado.idListaDesejos
                ) {
                    favoritos[produto.idProduto] = it
                }
            }
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        buscarProduto()
    }

    //clickedProduto é usado para monitorar a tela de produto selecionado
    //ela se torna true quando um produto é clicado lá em CardResultados()
    //e false quando clica em voltar na tela de produto selecionado
    var clickedProduto by remember { mutableStateOf(false) }

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    if (clickedProduto) {
        ProdutoScreen(onBackPressed = { clickedProduto = false },
            favoritado = favoritos[produtoSelecionado.idProduto] ?: "0",
            onFavoritoClick = { favoritado ->
                favoritos[produtoSelecionado.idProduto] =
                    adicionarListaDesejos(favoritado, produtoSelecionado)
            })
    } else {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFFFFFFF),
                                Color(0xFF86D0E2),
                                Color(0xFFFFFFFF)
                            )
                        )
                    )
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White)
                )
                Column(
                    modifier = Modifier.animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF029CCA)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            modifier = Modifier
                                .size(65.dp)
                                .padding(10.dp),
                            contentPadding = PaddingValues(0.dp),
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
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .width(220.dp),
                            text = nomeProduto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (expandedFiltro) {
                                Button(
                                    modifier = Modifier
                                        .size(65.dp)
                                        .padding(10.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    onClick = {
                                        cor = coresList[0]
                                        tamanho = tamanhosList[0]
                                        preco = precosList[0]
                                        tipoOrdenacao = tipoOrdenacaoList[0]
                                        buscarProduto()
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
                                    elevation = ButtonDefaults.buttonElevation(10.dp)
                                ) {
                                    Image(
                                        painterResource(id = R.drawable.baseline_refresh_24),
                                        contentDescription = "Limpar filtro",
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }

                            Button(
                                modifier = Modifier
                                    .size(65.dp)
                                    .padding(10.dp),
                                contentPadding = PaddingValues(0.dp),
                                onClick = { expandedFiltro = !expandedFiltro },
                                colors = if (expandedFiltro) ButtonDefaults.buttonColors(Color.Gray) else ButtonDefaults.buttonColors(
                                    Color.White
                                ),
                                elevation = ButtonDefaults.buttonElevation(10.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.baseline_filter_list_24),
                                    contentDescription = "Exibir filtros",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                    if (expandedFiltro) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF029CCA))
                        ) {
                            Row {
                                ExposedDropdownMenuBox(
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 5.dp)
                                        .weight(1f),
                                    expanded = expandedPrecosList,
                                    onExpandedChange = { expandedPrecosList = it },
                                ) {

                                    OutlinedTextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = preco,
                                        onValueChange = {},
                                        readOnly = true,
                                        singleLine = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedPrecosList
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = Color(0xFFEEF3F5),
                                            focusedContainerColor = Color(0xFFEEF3F5),
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black,
                                            unfocusedBorderColor = Color(0xFFEEF3F5),
                                            focusedBorderColor = Color(0xFFEEF3F5),
                                            focusedLabelColor = Color(0xFF000000),
                                            cursorColor = Color(0xFF029CCA),
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedPrecosList,
                                        onDismissRequest = { expandedPrecosList = false },
                                    ) {
                                        precosList.forEach { precoSelecionadoFiltro ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        precoSelecionadoFiltro,
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    preco = precoSelecionadoFiltro
                                                    expandedPrecosList = false
                                                    buscarProduto()
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                ExposedDropdownMenuBox(
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 5.dp)
                                        .weight(1f),
                                    expanded = expandedTamanhosList,
                                    onExpandedChange = { expandedTamanhosList = it },
                                ) {

                                    OutlinedTextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = tamanho,
                                        onValueChange = {},
                                        readOnly = true,
                                        singleLine = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedTamanhosList
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = Color(0xFFEEF3F5),
                                            focusedContainerColor = Color(0xFFEEF3F5),
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black,
                                            unfocusedBorderColor = Color(0xFFEEF3F5),
                                            focusedBorderColor = Color(0xFFEEF3F5),
                                            focusedLabelColor = Color(0xFF000000),
                                            cursorColor = Color(0xFF029CCA),
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedTamanhosList,
                                        onDismissRequest = { expandedTamanhosList = false },
                                    ) {
                                        tamanhosList.forEach { tamanhoSelecionadoFiltro ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        tamanhoSelecionadoFiltro,
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    tamanho = tamanhoSelecionadoFiltro
                                                    expandedTamanhosList = false
                                                    buscarProduto()
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.padding(bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ExposedDropdownMenuBox(
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 10.dp, end = 5.dp)
                                        .weight(1f),
                                    expanded = expandedCoresList,
                                    onExpandedChange = { expandedCoresList = it },
                                ) {

                                    OutlinedTextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = cor,
                                        onValueChange = {},
                                        readOnly = true,
                                        singleLine = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedCoresList
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = Color(0xFFEEF3F5),
                                            focusedContainerColor = Color(0xFFEEF3F5),
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black,
                                            unfocusedBorderColor = Color(0xFFEEF3F5),
                                            focusedBorderColor = Color(0xFFEEF3F5),
                                            focusedLabelColor = Color(0xFF000000),
                                            cursorColor = Color(0xFF029CCA),
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedCoresList,
                                        onDismissRequest = { expandedCoresList = false },
                                    ) {
                                        coresList.forEach { corSelecionadaFiltro ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        corSelecionadaFiltro,
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    cor = corSelecionadaFiltro
                                                    expandedCoresList = false
                                                    buscarProduto()
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }

                                ExposedDropdownMenuBox(
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 10.dp, end = 5.dp)
                                        .weight(1f),
                                    expanded = expandedtipoOrdenacao,
                                    onExpandedChange = { expandedtipoOrdenacao = it },
                                ) {

                                    OutlinedTextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = tipoOrdenacao,
                                        onValueChange = {},
                                        readOnly = true,
                                        singleLine = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedtipoOrdenacao
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = Color(0xFFEEF3F5),
                                            focusedContainerColor = Color(0xFFEEF3F5),
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black,
                                            unfocusedBorderColor = Color(0xFFEEF3F5),
                                            focusedBorderColor = Color(0xFFEEF3F5),
                                            focusedLabelColor = Color(0xFF000000),
                                            cursorColor = Color(0xFF029CCA),
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedtipoOrdenacao,
                                        onDismissRequest = { expandedtipoOrdenacao = false },
                                    ) {
                                        tipoOrdenacaoList.forEach { ordenacaoSelecionada ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        ordenacaoSelecionada,
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    tipoOrdenacao = ordenacaoSelecionada
                                                    expandedtipoOrdenacao = false
                                                    buscarProduto()
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    if (produtosList.isNotEmpty()) {
                        LazyColumn(state = listState) {
                            items(items = produtosList) { produto ->
                                CardResultados(
                                    onClickProduto = { clickedProduto = true },
                                    produto = produto,
                                    favoritado = favoritos[produto.idProduto] ?: "0",
                                    onFavoritoClick = { favoritado ->
                                        favoritos[produto.idProduto] =
                                            adicionarListaDesejos(favoritado, produto)
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
}