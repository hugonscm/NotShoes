package com.ahpp.notshoes.model

data class Produto(
    val idProduto: Int,
    val nomeProduto: String,
    val estoqueProduto: Int,
    val descricao: String,
    val tamanhoProduto: String,
    val corPrincipal: String,
    val preco: String,
    val desconto: String,
    val imagemProduto: String,
    val emOferta: Boolean
)