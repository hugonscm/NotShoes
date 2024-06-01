package com.ahpp.notshoes.util.funcoes.carrinho

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.ahpp.notshoes.bd.carrinho.CarrinhoRepository
import com.ahpp.notshoes.model.ItemCarrinho
import com.ahpp.notshoes.util.clienteLogado
import java.io.IOException


fun adicionarUnidade(ctx: Context, item: ItemCarrinho, onSuccess: () -> Unit) {
    val carrinhoRepository = CarrinhoRepository(
        item.idProduto,
        clienteLogado.idCliente
    )

    carrinhoRepository.adicionarItemCarrinho(object :
        CarrinhoRepository.Callback {
        override fun onSuccess(codigoRecebido: String) {
            // -1 estoque insuficiente, 0 erro, 1 sucesso
            when (codigoRecebido) {
                "-1" -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        ctx,
                        "Estoque insuficiente.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                "0" -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                        .show()
                }

                "1" -> onSuccess()

                else -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        ctx,
                        "Erro de rede.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        override fun onFailure(e: IOException) {
            // erro de rede
            // não é possível mostrar um Toast de um Thread
            // que não seja UI, então é feito dessa forma
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                    .show()
            }
            Log.e("Erro: ", e.message.toString())
        }
    })
}

fun removerUnidade(ctx: Context, item: ItemCarrinho, onSuccess: () -> Unit) {
    val carrinhoRepository = CarrinhoRepository(
        item.idProduto,
        clienteLogado.idCliente
    )

    carrinhoRepository.removerItemCarrinho(object :
        CarrinhoRepository.Callback {
        override fun onSuccess(codigoRecebido: String) {
            // -1 estoque insuficiente, 0 erro, 1 sucesso
            when (codigoRecebido) {
                "0" -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                        .show()
                }

                "1" -> onSuccess()

                else -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        ctx,
                        "Erro de rede.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        override fun onFailure(e: IOException) {
            // erro de rede
            // não é possível mostrar um Toast de um Thread
            // que não seja UI, então é feito dessa forma
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                    .show()
            }
            Log.e("Erro: ", e.message.toString())
        }
    })
}

fun removerProduto(ctx: Context, item: ItemCarrinho, onSuccess: () -> Unit) {
    val carrinhoRepository = CarrinhoRepository(
        item.idProduto,
        clienteLogado.idCliente
    )

    carrinhoRepository.removerProdutoCarrinho(object :
        CarrinhoRepository.Callback {
        override fun onSuccess(codigoRecebido: String) {
            // -1 erro, 1 sucesso
            Log.i(
                "CODIGO RECEBIDO (REMOVER ITEM DO CARRINHO): ",
                codigoRecebido
            )
            when (codigoRecebido) {

                "1" -> onSuccess()

                else -> Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        ctx,
                        "Erro de rede.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        override fun onFailure(e: IOException) {
            // erro de rede
            // não é possível mostrar um Toast de um Thread
            // que não seja UI, então é feito dessa forma
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(ctx, "Erro de rede.", Toast.LENGTH_SHORT)
                    .show()
            }
            Log.e("Erro (remover produto carrinho): ", e.message.toString())
        }
    })
}
