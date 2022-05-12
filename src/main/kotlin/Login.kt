import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*

@Composable
fun LoginScreen(updateScreen: (screen: Screen) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Table(attrs = {
        id("myTable")
    }) {
        Tr {
            Td(attrs = { colspan(2) }) {
                H1 { Text("Login Screen") }
            }
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