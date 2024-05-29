package com.ahpp.notshoes.util

object ValidarCamposEndereco {
    fun validarCep(cep: String): Boolean {
        return cep.isNotEmpty()
    }

    fun validarEndereco(endereco: String): Boolean {
        return endereco.isNotEmpty()
    }

    fun validarNumero(numero: String): Boolean {
        return numero.isNotEmpty()
    }

    fun validarBairro(bairro: String): Boolean {
        return bairro.isNotEmpty()
    }

    fun validarEstado(estado: String): Boolean {
        return estado != "Estado"
    }

    fun validarCidade(cidade: String): Boolean {
        return cidade.isNotEmpty()
    }
}