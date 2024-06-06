package com.ahpp.notshoes.view.viewsLogado.viewsPerfil.viewsPedidos

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahpp.notshoes.bd.cliente.getPedidos
import com.ahpp.notshoes.model.Venda
import com.ahpp.notshoes.util.cards.CardPedidos
import com.ahpp.notshoes.view.clienteLogado
import com.ahpp.notshoes.util.funcoes.possuiConexao

@Composable
fun PedidosScreen(onBackPressed: () -> Unit) {

    val ctx = LocalContext.current

    var pedidosList by remember { mutableStateOf(emptyList<Venda>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (!possuiConexao(ctx)) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ctx, "Sem conexão com a internet.", Toast.LENGTH_SHORT).show()
            }
        } else {
            pedidosList = getPedidos(clienteLogado.idCliente)
        }

        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White
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
                    Color.White
                )
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .size(45.dp),
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
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = "Seus pedidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                if (pedidosList.isNotEmpty()) {
                    LazyColumn {
                        items(items = pedidosList) { pedido ->
                            CardPedidos(pedido)
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
                            text = "Nenhum pedido realizado.",
                            fontSize = 25.sp,
                            style = TextStyle(
                                Color(0xFF029CCA)
                            )
                        )
                    }
                }
            }
        }
    }
}