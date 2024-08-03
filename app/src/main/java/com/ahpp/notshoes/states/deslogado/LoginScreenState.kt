package com.ahpp.notshoes.states.deslogado

data class LoginScreenState (
    val email: String = "",
    val senha: String = "",
    val emailValido: Boolean = true,
    val senhaValida: Boolean = true,
    val dadosIncorretos: Boolean = false,
    val enabledButton: Boolean = true
)