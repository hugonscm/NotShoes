package com.ahpp.notshoes.bd

import com.ahpp.notshoes.model.Cliente
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

class ClienteRepository {

    private val httpClient = OkHttpClient()
    private lateinit var cliente: Cliente

    fun getCliente(idClienteLogado: Int): Cliente {

        val jsonObj = JsonObject().apply {
            addProperty("idClienteLogado", idClienteLogado)
        }

        val requestBody = jsonObj.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("http://10.0.2.2:5000/get_cliente")
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        //esse tipo de requisicao precisa ser rodado em um thread
        //por isso o uso do executor
        executor.execute {
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val json = response.body?.string()
                    val gson = Gson()
                    val jsonElement = gson.fromJson(json, JsonElement::class.java)

                    if (jsonElement.isJsonObject) {
                        val clienteJson = jsonElement.asJsonObject
                        cliente = Cliente(
                            //os que tem a verificaçao isJsonNull é porque podem ser nulos
                            idCliente = clienteJson.get("idCliente").asInt,
                            genero = if (clienteJson.get("genero").isJsonNull) "0" else clienteJson.get("genero").asString,
                            nome = clienteJson.get("nome").asString,
                            email = clienteJson.get("email").asString,
                            senha = clienteJson.get("senha").asString,
                            cpf = if (clienteJson.get("cpf").isJsonNull) "" else clienteJson.get("cpf").asString,
                            telefoneContato = if (clienteJson.get("telefoneContato").isJsonNull) "" else clienteJson.get("telefoneContato").asString,
                            idListaDesejos = clienteJson.get("idListaDesejos").asInt,
                            idCarrinho = clienteJson.get("idCarrinho").asInt,
                            idEnderecoPrincipal = if (clienteJson.get("idEnderecoPrincipal").isJsonNull) -1 else clienteJson.get("idEnderecoPrincipal").asInt
                        )
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
            //espera 5 segundos se nao da timeout
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return this.cliente
    }
}