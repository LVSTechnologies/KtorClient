package com.example.ktorclient.network

import com.example.ktorclient.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class KtorClient {

    companion object {
        fun getClient(): HttpClient = HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 3000
                requestTimeoutMillis = 3000
                connectTimeoutMillis = 3000
            }

            install(DefaultRequest) {
                url {
                    host = "jsonplaceholder.typicode.com"
                    protocol = URLProtocol.HTTPS
                    headers {
                        append(HttpHeaders.Authorization, "notimportantforthisusecase")
                    }
                }
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

        }

    }

    suspend fun getPosts(): List<Post> {
        return getClient().get("/posts").body<List<Post>>()
    }
}