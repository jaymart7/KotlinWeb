import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

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

@Composable
fun LoginScreen(updateScreen: (screen: Screen) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Table(attrs = {
        id("myTable")
    }) {
        Tr {
            Td(attrs = { colspan(2) }) { H1 { Text("Login Screen") } }
        }
        Tr {
            Td { Text("Username") }
            Td { Input(InputType.Text) { onInput { username.value = it.value } } }
        }
        Tr {
            Td { Text("Password") }
            Td { Input(InputType.Password) { onInput { password.value = it.value } } }
        }
        Tr {
            Td(attrs = { colspan(2) }) {
                Button(attrs = {
                    style { width(100.percent) }
                    onClick {
                        updateScreen(Screen.HOME)
                    }
                }) { Text("Login") }
            }
        }
    }
}

@Composable
fun HomeScreen(updateScreen: (screen: Screen) -> Unit) {
    Button(attrs = {
        onClick {
            updateScreen(Screen.LOGIN)
        }
    }) { Text("Logout") }

    H1 { Text("Welcome") }
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