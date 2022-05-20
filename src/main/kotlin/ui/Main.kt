package ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ktor.client.*
import kotlinx.coroutines.MainScope
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.renderComposable

val scope = MainScope()
val json = Json { ignoreUnknownKeys = true }
val client = HttpClient()

const val url = "http://localhost:8081"

fun main() {
    renderComposable(rootElementId = "root") {
        Style(MyStyle)

        val screen = remember { mutableStateOf(Screen.LOGIN) }
        when (screen.value) {
            Screen.LOGIN -> LoginScreen { screen.value = it }
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