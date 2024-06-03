package com.ahpp.notshoes.bd.cliente

import com.ahpp.notshoes.model.Cliente
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

suspend fun getCliente(idClienteLogado: Int): Cliente {
    return withContext(Dispatchers.IO) {
        lateinit var cliente: Cliente
        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/get_cliente"

        val jsonObj = JsonObject().apply {
            addProperty("idClienteLogado", idClienteLogado)
        }

        val requestBody = jsonObj.toString().toRequestBody("application/json".toMediaType())
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

                    if (jsonElement.isJsonObject) {
                        val clienteJson = jsonElement.asJsonObject
                        cliente = Cliente(
                            //os que tem a verificaçao isJsonNull é porque podem ser nulos
                            idCliente = clienteJson.get("idCliente").asInt,
                            genero = if (clienteJson.get("genero").isJsonNull) "0" else clienteJson.get(
                                "genero"
                            ).asString,
                            nome = clienteJson.get("nome").asString,
                            email = clienteJson.get("email").asString,
                            senha = clienteJson.get("senha").asString,
                            cpf = if (clienteJson.get("cpf").isJsonNull) "" else clienteJson.get("cpf").asString,
                            telefoneContato = if (clienteJson.get("telefoneContato").isJsonNull) "" else clienteJson.get(
                                "telefoneContato"
                            ).asString,
                            idListaDesejos = clienteJson.get("idListaDesejos").asInt,
                            idCarrinho = clienteJson.get("idCarrinho").asInt,
                            idEnderecoPrincipal = if (clienteJson.get("idEnderecoPrincipal").isJsonNull) -1 else clienteJson.get(
                                "idEnderecoPrincipal"
                            ).asInt
                        )
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext cliente
    }
}