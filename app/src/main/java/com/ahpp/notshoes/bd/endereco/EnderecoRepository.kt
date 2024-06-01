package com.ahpp.notshoes.bd.endereco

import com.ahpp.notshoes.model.Endereco
import com.ahpp.notshoes.model.Produto
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

suspend fun getEnderecos(idClienteLogado: Int): List<Endereco> {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/get_enderecos_cliente"

        var enderecosList: List<Endereco> = emptyList()

        val jsonMessage = JsonObject().apply {
            addProperty("idClienteLogado", idClienteLogado)
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
                        enderecosList = jsonArray.map { enderecoJson ->
                            val enderecoArray = enderecoJson.asJsonArray
                            Endereco(
                                enderecoArray[0].asInt,
                                enderecoArray[1].asString,
                                enderecoArray[2].asString,
                                enderecoArray[3].asString,
                                enderecoArray[4].asString,
                                enderecoArray[5].asString,
                                enderecoArray[6].asInt,
                                enderecoArray[7].asString,
                                enderecoArray[8].asInt,
                            )
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext enderecosList
    }
}