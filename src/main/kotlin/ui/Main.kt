package ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import kotlinx.coroutines.MainScope
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.renderComposable
import repository.AccountRepositoryImpl
import ui.login.LoginImpl
import ui.login.LoginScreen

val scope = MainScope()

val json = Json { ignoreUnknownKeys = true }

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

const val url = "http://localhost:8081"

fun main() {
    val accountRepository = AccountRepositoryImpl(httpClient)
    val login = LoginImpl(accountRepository)

    renderComposable(rootElementId = "root") {
        Style(MyStyle)

        val screen = remember { mutableStateOf(Screen.LOGIN) }
        when (screen.value) {
            Screen.LOGIN -> LoginScreen(login)
            Screen.HOME -> HomeScreen { screen.value = it }
        }
    }
}

enum class Screen {
    LOGIN,
    HOME
}

object MyStyle : StyleSheet() {

    init {
        "td, tr table" style {
            padding(16.px)
            textAlign("center")
            width(value = 50.percent)
            border {
                width = 1.px
                style = LineStyle.Solid
                color = Color.aliceblue
            }
        }
    }
}