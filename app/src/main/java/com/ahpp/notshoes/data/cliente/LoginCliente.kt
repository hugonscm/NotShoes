package com.ahpp.notshoes.data.cliente

import com.ahpp.notshoes.api.apiUrl
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.Executors

class LoginCliente(
    private val email: String,
    private val senha: String
) {

    interface Callback {
        fun onSuccess(idUsuarioRecebido: String)
        fun onFailure(e: IOException)
    }

    fun sendLoginData(callback: Callback) {

        val client = OkHttpClient()
        val url = "$apiUrl/validar_login_cliente"

        val json = JsonObject().apply {
            addProperty("email", email)
            addProperty("senha", senha)
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
                val userId = response.body?.string()

                if (userId != null) {
                    callback.onSuccess(userId)
                }
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                executor.shutdown()
            }
        }
    }
}