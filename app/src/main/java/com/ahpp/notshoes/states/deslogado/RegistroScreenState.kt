package com.ahpp.notshoes.states.deslogado

data class RegistroScreenState (
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val nomeValido: Boolean = true,
    val emailValido: Boolean = true,
    val senhaValida: Boolean = true,
    val enabledButton: Boolean = true,
    val codigoStatusRegistro: String = "-1"
)