package com.ahpp.notshoes.model

data class Endereco(
    val idEndereco: Int,
    val estado: String,
    val cidade: String,
    val cep: String,
    val endereco: String,
    val bairro: String,
    val numero: Int,
    val complemento: String,
    val idCliente: Int
)