package com.flemis.score.features.app.data.datasource.remote

import com.flemis.score.core.config.KtorClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import kotlinx.serialization.json.Json

interface ApiService {

    suspend fun get(url: String, headers: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.get(url) {
            headers {
                append("Accept", "application/json")
                //append("x-custom-header","value")
                if (!headers.isNullOrEmpty()) {
                    for ((key, value) in headers) {
                        append(key, value)
                    }
                }
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun post(urlString: String, body: Map<String, String>?, headers: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.post {
            url {
                urlString
            }
            if (body != null) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header","value")
                if (!headers.isNullOrEmpty()) {
                    for ((key, value) in headers) {
                        append(key, value)
                    }
                }
            }
            contentType(ContentType.Application.Json)
        }
        return response;
    }

    suspend fun put(urlString: String, body: Map<String, String>?, headers: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.put {
            url {
                urlString
            }
            if (body != null) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header","value")
                if (!headers.isNullOrEmpty()) {
                    for ((key, value) in headers) {
                        append(key, value)
                    }
                }
            }
            contentType(ContentType.Application.Json)
        }
        return response;
    }

    suspend fun delete(urlString: String, body: Map<String, String>?, headers: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.delete {
            url {
                urlString
            }
            if (body != null) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header","value")
                if (!headers.isNullOrEmpty()) {
                    for ((key, value) in headers) {
                        append(key, value)
                    }
                }
            }
            contentType(ContentType.Application.Json)
        }
        return response;
    }
}