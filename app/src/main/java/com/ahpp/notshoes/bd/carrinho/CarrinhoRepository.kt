package com.ahpp.notshoes.bd.carrinho

import com.ahpp.notshoes.model.ItemCarrinho
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.Executors

class CarrinhoRepository(
    private val idProduto: Int,
    private val idClienteLogado: Int
) {

    interface Callback {
        fun onSuccess(codigoRecebido: String)
        fun onFailure(e: IOException)
    }

    private val client = OkHttpClient()

    fun adicionarItemCarrinho(callback: Callback) {

        val url = "http://10.0.2.2:5000/adicionar_item_carrinho"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idCliente", idClienteLogado)
        }

        val requestBody = jsonMessage.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val response = client.newCall(request).execute()
                val code = response.body?.string()

                if (code != null) {
                    callback.onSuccess(code)
                }
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                executor.shutdown()
            }
        }
    }

    fun removerItemCarrinho(callback: Callback) {

        val url = "http://10.0.2.2:5000/remover_item_carrinho"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idCliente", idClienteLogado)
        }

        val requestBody = jsonMessage.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val response = client.newCall(request).execute()
                val code = response.body?.string()

                if (code != null) {
                    callback.onSuccess(code)
                }
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                executor.shutdown()
            }
        }
    }

    fun removerProdutoCarrinho(callback: Callback) {

        val url = "http://10.0.2.2:5000/remover_produto_carrinho"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idCliente", idClienteLogado)
        }

        val requestBody = jsonMessage.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val response = client.newCall(request).execute()
                val code = response.body?.string()

                if (code != null) {
                    callback.onSuccess(code)
                }
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                executor.shutdown()
            }
        }

    }
}

// ok nao mexa mais, faça igual, nos outros se possivel
suspend fun getItensCarrinho(idCarrinho: Int): List<ItemCarrinho> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/get_itens_carrinho_cliente"

        var itensList: List<ItemCarrinho> = emptyList()

        val jsonMessage = JsonObject().apply {
            addProperty("idCarrinho", idCarrinho)
        }

        val requestBody = jsonMessage.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()?.let { json ->
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        itensList = jsonArray.map { itemJson ->
                            val itemArray = itemJson.asJsonArray
                            ItemCarrinho(
                                itemArray[0].asInt,
                                itemArray[1].asInt,
                                itemArray[2].asInt,
                                itemArray[3].asInt
                            )
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext itensList
    }
}