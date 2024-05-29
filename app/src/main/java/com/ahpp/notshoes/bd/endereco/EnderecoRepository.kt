package com.ahpp.notshoes.bd.endereco

import com.ahpp.notshoes.model.Endereco
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

class EnderecoRepository {
    private val client = OkHttpClient()
    private var enderecosList: List<Endereco> = emptyList()

    fun getEnderecos(idClienteLogado: Int): List<Endereco> {

        val url = "http://10.0.2.2:5000/get_enderecos_cliente"

        val jsonMessage = JsonObject().apply {
            addProperty("idClienteLogado", idClienteLogado)
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
                        enderecosList = jsonArray.map { produtoJson ->
                            val produtoArray = produtoJson.asJsonArray
                            Endereco(
                                produtoArray[0].asInt,
                                produtoArray[1].asString,
                                produtoArray[2].asString,
                                produtoArray[3].asString,
                                produtoArray[4].asString,
                                produtoArray[5].asString,
                                produtoArray[6].asInt,
                                produtoArray[7].asString,
                                produtoArray[8].asInt,
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
        return this.enderecosList
    }
}