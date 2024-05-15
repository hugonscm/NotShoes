package com.ahpp.notshoes.model

data class Produto(
    val idProduto: String,
    val nomeProduto: String,
    val estoqueProduto: String,
    val descricao: String,
    val preco: String,
    val desconto: String,
    val fotoProduto: String,
    val emOferta: Boolean
)