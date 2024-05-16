package com.ahpp.notshoes.model

data class Cliente(
    val idCliente: Int,
    val genero: String,
    var nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val idEndereco: Int,
    val idListaDesejos: Int,
    val idCarrinho: Int
)