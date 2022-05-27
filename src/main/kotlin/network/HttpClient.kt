package network

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val json = Json { ignoreUnknownKeys = true }

const val url = "http://127.0.0.1:8081"
val httpClient = HttpClient(Js) {
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

    install(ContentNegotiation) { json(Json) }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
}