package com.ahpp.notshoes.bd.endereco

import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.Executors

class RemoverEnderecoCliente(
    private val idEndereco: Int,
    private val idCliente: Int,
) {

    private val client = OkHttpClient()

    interface Callback {
        fun onSuccess(code: String)
        fun onFailure(e: IOException)
    }

    fun sendRemoverEnderecoCliente(callback: Callback) {

        val url = "http://10.0.2.2:5000/remover_endereco_cliente"

        val jsonMessage = JsonObject().apply {
            addProperty("idEndereco", idEndereco)
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