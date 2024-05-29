package com.ahpp.notshoes.util

object ValidarCamposDados {

    fun validarNome(nome: String): Boolean {
        return nome.isNotEmpty()
    }

    fun validarEmail(email: String): Boolean {
        return if (email.isNotEmpty()) {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else false
    }

    fun validarSenha(senha: String): Boolean {
        return senha.isNotEmpty()
    }

    fun validarCpf(cpf: String): Boolean {
        return cpf.isNotEmpty()
    }

    fun validarTelefone(telefone: String): Boolean {
        return telefone.isNotEmpty()
    }
}