package com.flemis.score.core.config


import com.flemis.score.core.utils.UserAgent as AppUserAgent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import org.koin.compose.getKoin

/*import org.koin.core.Koin
import org.koin.core.context.loadKoinModules
import org.koin.core.logger.KOIN_TAG
import org.koin.dsl.module*/

object KtorClient {

    private val serializeJson = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }


    val httpClient = HttpClient() {
        /*install(Logging) {
            level = LogLevel.ALL
        }*/

        install(UserAgent) {
            agent = AppUserAgent().getUserAgent()
        }
        install(ContentNegotiation) {
            json(json = serializeJson)
        }

        install(HttpRequestRetry) {
            //function enables retrying a request if a 5xx response is received from a server and specifies the number of retries.
            retryOnServerErrors(5)
            //specifies an exponential delay between retries, which is calculated using the Exponential backoff algorithm.
            exponentialDelay()
            //If you want to add some additional params in header
            modifyRequest { request ->
                request.headers.append("x-retry-count", 2.toString())


            }
        }

        //If you want to change user agent
        install(UserAgent) {
            agent = "Ktor"
        }

        install(DefaultRequest) {

            headers.appendIfNameAbsent("X-custom-header", "Some Value")
            contentType(ContentType.Application.Json)
        }
        /*HttpResponseValidator {
            validateResponse {
                handleResponseExceptionWithRequest { exception, request ->
                    val clientException =
                        exception as? ClientRequestException
                            ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                    if (exceptionResponse.status >= HttpStatusCode.Forbidden) {
                        throw CustomResponseException(exception.message, null, null)
                    }
                }
            }
        }*/

        ResponseObserver {
            val timeDifference = it.responseTime.timestamp - it.requestTime.timestamp
            val protocolVersion = it.version
        }
    }
}