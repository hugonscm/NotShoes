package com.ahpp.notshoes.view.screensReutilizaveis

import android.os.Handler
import android.os.Looper
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahpp.notshoes.R
import com.ahpp.notshoes.data.produto.ProdutoRepository
import com.ahpp.notshoes.model.Produto
import com.ahpp.notshoes.ui.theme.azulEscuro
import com.ahpp.notshoes.ui.theme.branco
import com.ahpp.notshoes.util.cards.CardResultados
import com.ahpp.notshoes.util.filtros.filtrarProdutos
import com.ahpp.notshoes.util.funcoes.conexao.possuiConexao
import com.ahpp.notshoes.util.funcoes.produto.adicionarListaDesejos
import com.ahpp.notshoes.view.viewsLogado.produtoSelecionado
import com.ahpp.notshoes.view.viewsDeslogado.clienteLogado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadosScreen(
    navController: NavController,
    valorBusca: String?,
    tipoBusca: String?,
    fromScreen: String?
) {

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
    var expandedtipoOrdenacaoList by remember { mutableStateOf(false) }
    var tipoOrdenacao by remember { mutableStateOf(tipoOrdenacaoList[0]) }

    var expandedFiltro by remember { mutableStateOf(false) }

    val repository = ProdutoRepository()
    var produtosList by remember { mutableStateOf(emptyList<Produto>()) }

    var clickedProduto by remember { mutableStateOf(false) }

    // Gerencie o estado dos favoritos aqui
    val favoritos = remember { mutableStateMapOf<Int, String>() }

    var isLoading by remember { mutableStateOf(true) }

    val ctx = LocalContext.current

    val scope = rememberCoroutineScope()
    fun buscarProduto() {
        scope.launch(Dispatchers.IO) {
            if (!possuiConexao(ctx)) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(ctx, R.string.verifique_conexao_internet, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                produtosList = when (tipoBusca) {
                    "nome" -> {
                        repository.buscarProdutoNome(valorBusca.toString())
                    }

                    "categoria" -> {
                        repository.filtrarProdutoCategoria(valorBusca.toString())
                    }

                    else -> {
                        //buscar produtos por intervalo de preço
                        repository.getProdutosFiltroIntervalo(valorBusca.toString())
                    }
                }

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
            }
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        buscarProduto()
    }

    // manter a posicao do scroll ao voltar pra tela
    val listState = rememberLazyListState()

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (clickedProduto) {
        ProdutoScreen(onBackPressed = { clickedProduto = false },
            favoritado = favoritos[produtoSelecionado.idProduto] ?: "0",
            onFavoritoClick = { favoritado ->
                favoritos[produtoSelecionado.idProduto] =
                    adicionarListaDesejos(favoritado, produtoSelecionado)
            })
    } else {
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
                        .background(azulEscuro),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        modifier = Modifier
                            .size(65.dp)
                            .padding(10.dp),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            when (fromScreen) {
                                "inicioScreen" -> navController.navigate("inicioScreen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                }

                                "categoriaScreen" -> navController.navigate("categoriaScreen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(branco),
                        elevation = ButtonDefaults.buttonElevation(10.dp)
                    ) {
                        Image(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.toque_para_voltar_description),
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        modifier = Modifier
                            .width(220.dp),
                        text = valorBusca.toString(),
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
                                colors = ButtonDefaults.buttonColors(branco),
                                elevation = ButtonDefaults.buttonElevation(10.dp)
                            ) {
                                Image(
                                    painterResource(id = R.drawable.baseline_refresh_24),
                                    contentDescription = stringResource(id = R.string.limpar_filtros_description),
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
                                contentDescription = stringResource(id = R.string.exibir_filtros_description),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
                if (expandedFiltro) {

                    val colorsTextField = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFEEF3F5),
                        focusedContainerColor = Color(0xFFEEF3F5),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedBorderColor = Color(0xFFEEF3F5),
                        focusedBorderColor = Color(0xFFEEF3F5),
                        focusedLabelColor = Color(0xFF000000),
                        cursorColor = azulEscuro
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(azulEscuro)
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
                                    colors = colorsTextField
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
                                    colors = colorsTextField
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
                                    colors = colorsTextField
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
                                expanded = expandedtipoOrdenacaoList,
                                onExpandedChange = { expandedtipoOrdenacaoList = it },
                            ) {

                                OutlinedTextField(
                                    modifier = Modifier.menuAnchor(),
                                    value = tipoOrdenacao,
                                    onValueChange = {},
                                    readOnly = true,
                                    singleLine = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedtipoOrdenacaoList
                                        )
                                    },
                                    colors = colorsTextField
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedtipoOrdenacaoList,
                                    onDismissRequest = { expandedtipoOrdenacaoList = false },
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
                                                expandedtipoOrdenacaoList = false
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
                                onClickProduto = {
                                    produtoSelecionado = produto
                                    clickedProduto = true
                                },
                                produto = produto,
                                favoritado = favoritos[produto.idProduto] ?: "0",
                                onFavoritoClick = { favoritado ->
                                    val novoFavoritado =
                                        adicionarListaDesejos(favoritado, produto)
                                    favoritos[produto.idProduto] = novoFavoritado
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
                            text = stringResource(id = R.string.nenhum_produto_encontrado),
                            fontSize = 28.sp,
                            color = azulEscuro
                        )
                    }
                }
            }

        }
    }

}