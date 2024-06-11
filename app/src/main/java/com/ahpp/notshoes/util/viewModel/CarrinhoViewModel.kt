package com.ahpp.notshoes.util.viewModel

import androidx.lifecycle.ViewModel
import com.ahpp.notshoes.model.ItemCarrinho
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CarrinhoViewModel : ViewModel() {

    private val _itemListSelecionada = MutableStateFlow<List<ItemCarrinho>>(emptyList())
    val itemListSelecionada: StateFlow<List<ItemCarrinho>> = _itemListSelecionada

    private val _detalhesPedidoSelecionado = MutableStateFlow("")
    val detalhesPedidoSelecionado: StateFlow<String> = _detalhesPedidoSelecionado

    private val _valorTotalComDescontoSelecionado = MutableStateFlow(0.0)
    val valorTotalComDescontoSelecionado: StateFlow<Double> = _valorTotalComDescontoSelecionado

    fun selecionaritemList(itemList: List<ItemCarrinho>) {
        _itemListSelecionada.value = itemList
    }

    fun selecionardetalhesPedido(detalhesPedido: String) {
        _detalhesPedidoSelecionado.value = detalhesPedido
    }

    fun selecionarvalorTotalComDesconto(valorTotalComDesconto: Double) {
        _valorTotalComDescontoSelecionado.value = valorTotalComDesconto
    }
}