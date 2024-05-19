package com.ahpp.notshoes.model

data class Cliente(
    val idCliente: Int,
    val genero: String,
    var nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val telefoneContato: String,
    val idListaDesejos: Int,
    val idCarrinho: Int
)