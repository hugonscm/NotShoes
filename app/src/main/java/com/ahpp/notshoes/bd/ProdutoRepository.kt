package com.ahpp.notshoes.bd

import android.util.Log
import com.ahpp.notshoes.model.Produto
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ProdutoRepository {

    private val client = OkHttpClient()
    private var produtosList: List<Produto> = emptyList()

    fun getPromocoes(): List<Produto> {
        val request = Request.Builder().url("http://10.0.2.2:5000/get_promocoes").build()

        val executor = Executors.newSingleThreadExecutor()
        //esse tipo de requisicao precisa ser rodado em um thread
        //por isso o uso do executor
        executor.execute {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val json = response.body?.string()
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    //se estiver vazia, ele vai retornar uma emptyList(), vc tem q tratar isso
                    //la no card de promocoes ainda
                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asInt,
                                produtoArray[1].asString,
                                produtoArray[2].asInt,
                                produtoArray[3].asString,
                                produtoArray[4].asString,
                                produtoArray[5].asString,
                                produtoArray[6].asString,
                                produtoArray[7].asBoolean
                            )
                        }
                    }
                } catch (e: XmlPullParserException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    //desliga o executor para carregar a tela
                    executor.shutdown()
                }
            }
        }
        try {
            //espera 5 segundos se nao da timeout
            // a minha ideia aqui é, se der timeout, fechar essa tela e mostrar uma tela de erro de rede
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this.produtosList
    }

    fun filtrarProdutoCategoria(categoria: String): List<Produto> {
        val request =
            Request.Builder().url("http://10.0.2.2:5000/filtrar_produto_categoria/$categoria")
                .build()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val json = response.body?.string()
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asInt,
                                produtoArray[1].asString,
                                produtoArray[2].asInt,
                                produtoArray[3].asString,
                                produtoArray[4].asString,
                                produtoArray[5].asString,
                                produtoArray[6].asString,
                                produtoArray[7].asBoolean
                            )
                        }
                    }

                } catch (e: XmlPullParserException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    executor.shutdown()
                }
            }
        }

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this.produtosList
    }

    fun buscarProdutoNome(nome: String): List<Produto> {
        val request =
            Request.Builder().url("http://10.0.2.2:5000/busca_produto/$nome")
                .build()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val json = response.body?.string()
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asInt,
                                produtoArray[1].asString,
                                produtoArray[2].asInt,
                                produtoArray[3].asString,
                                produtoArray[4].asString,
                                produtoArray[5].asString,
                                produtoArray[6].asString,
                                produtoArray[7].asBoolean
                            )
                        }
                    }

                } catch (e: XmlPullParserException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    executor.shutdown()
                }
            }
        }

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this.produtosList
    }

    fun getProdutosListaDesejos(idListaDesejos: Int): List<Produto> {

        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/get_lista_desejos"

        val jsonMessage = JsonObject().apply {
            addProperty("idListaDesejos", idListaDesejos)
        }

        val requestBody = jsonMessage.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val json = response.body?.string()
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asInt,
                                produtoArray[1].asString,
                                produtoArray[2].asInt,
                                produtoArray[3].asString,
                                produtoArray[4].asString,
                                produtoArray[5].asString,
                                produtoArray[6].asString,
                                produtoArray[7].asBoolean
                            )
                        }
                    }

                } catch (e: XmlPullParserException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    executor.shutdown()
                }
            }
        }

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this.produtosList
    }

    fun removerProdutoListaDesejos(idProduto: Int, idCliente: Int) {

        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/remover_lista_desejos"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idCliente", idCliente)
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
                val code = response.body?.string().toString()
                Log.e("Codigo recebido: ", code)
            } catch (e: IOException) {
                Log.e("Erro", "Erro de rede.", e)
            } finally {
                executor.shutdown()
            }
        }
    }

    fun adicionarProdutoListaDesejos(idProduto: Int, idCliente: Int) {

        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/adicionar_lista_desejos"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idCliente", idCliente)
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
                val code = response.body?.string().toString()
                Log.e("Codigo recebido: ", code)
            } catch (e: IOException) {
                Log.e("Erro", "Erro de rede.", e)
            } finally {
                executor.shutdown()
            }
        }
    }

    fun verificarProdutoListaDesejos(idProduto: Int, idListaDesejos: Int, callback: (String) -> Unit) {
        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/verificar_produto_lista_desejos"

        val jsonMessage = JsonObject().apply {
            addProperty("idProduto", idProduto)
            addProperty("idListaDesejos", idListaDesejos)
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
                val resultado = response.body?.string().toString()
                callback(resultado)
            } catch (e: IOException) {
                Log.e("Erro", "Erro de rede.", e)
            } finally {
                executor.shutdown()
            }
        }
    }
}