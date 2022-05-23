package network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

val json = Json { ignoreUnknownKeys = true }

const val url = "http://localhost:8081"
val httpClient = HttpClient() {
    expectSuccess = true

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, request ->
            val clientException =
                exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
            val exceptionResponse = exception.response
            val exceptionResponseText = exceptionResponse.bodyAsText()
            throw Exception(exceptionResponseText)
        }
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
}