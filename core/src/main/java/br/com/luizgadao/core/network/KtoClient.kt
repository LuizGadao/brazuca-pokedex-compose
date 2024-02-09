package br.com.luizgadao.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.lang.Exception

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://pokeapi.co/api/v2/pokemon") }

        install(Logging) { logger = Logger.SIMPLE }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

    private inline fun <T> request(apiCall: () -> T): NetworkResult<T> {
        return try {
            NetworkResult.Success(data = apiCall())
        } catch (e: Exception) {
            NetworkResult.Failure(exception = e)
        }
    }
}

sealed interface NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Failure<T>(val exception: Exception) : NetworkResult<T>

    fun success(block: (T) -> Unit): NetworkResult<T> {
        if (this is Success) block(data)
        return this
    }

    fun failure(block: (Exception) -> Unit) : NetworkResult<T> {
        if (this is Failure) block(exception)
        return this
    }
}