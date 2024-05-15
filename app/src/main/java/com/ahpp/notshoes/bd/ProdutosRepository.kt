package com.ahpp.notshoes.bd

import com.ahpp.notshoes.model.Produto
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ProdutosRepository {

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

                    //verifica se a lista nao esta vazia antes de transformar em List<Produto>

                    //se estiver vazia, ele vai retornar uma emptyList(), vc tem q tratar isso
                    //la no card de promocoes ainda

                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asString,
                                produtoArray[1].asString,
                                produtoArray[2].asString,
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
            // a minha ideia aqui é, se der timeout, fechar essa tela e mostrar uma tela de erro
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

                    //verifica se a lista nao esta vazia antes de transformar em List<Produto>
                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asString,
                                produtoArray[1].asString,
                                produtoArray[2].asString,
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

                    //verifica se a lista nao esta vazia antes de transformar em List<Produto>
                    if (jsonElement.isJsonArray) {
                        val jsonArray = jsonElement.asJsonArray
                        produtosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Produto(
                                produtoArray[0].asString,
                                produtoArray[1].asString,
                                produtoArray[2].asString,
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
}

