package com.ahpp.notshoes.bd

import com.ahpp.notshoes.util.clienteLogado
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.Executors

class AtualizarSenhaCliente(
    private var senhaNova: String,
) {

    interface Callback {
        fun onSuccess(code: String)
        fun onFailure(e: IOException)
    }

    fun sendAtualizarData(callback: Callback) {

        val client = OkHttpClient()
        val url = "http://10.0.2.2:5000/atualizar_senha_cliente"

        val json = JsonObject().apply {
            addProperty("senhaNova", senhaNova)
            addProperty("idCliente", clienteLogado.idCliente)
        }

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        //esse tipo de requisicao precisa ser rodado em um thread
        //por isso o uso do executor

        executor.execute {
            try {
                val response = client.newCall(request).execute()
                callback.onSuccess(response.code.toString())
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                executor.shutdown()
            }
        }
    }
}