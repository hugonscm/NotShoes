package com.ahpp.notshoes.model

import java.time.LocalDate

data class Venda (
    val idVenda: Int? = null,
    val dataPedido: LocalDate,
    val status: String,
    val detalhesPedido: String,
    val formaPagamento: String,
    val idCarrinho: Int
)