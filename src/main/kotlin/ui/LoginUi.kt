package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import common.Login
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*

@Composable
fun LoginScreen(login: Login) {

    val model = login.state.collectAsState()
    val loginError = model.value.loginError

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
            Td { Input(InputType.Text) { onInput { login.onUsernameChanged(it.value) } } }
        }
        Tr {
            Td { Text("Password") }
            Td { Input(InputType.Password) { onInput { login.onPasswordChanged(it.value) } } }
        }
        Tr {
            Td(attrs = { colspan(2) }) {
                Button(attrs = {
                    style { width(100.percent) }
                    onClick { login.login() }
                }) { Text("Login") }

                if (loginError.isNullOrBlank().not()) {
                    Text(loginError.orEmpty())
                }

                if (model.value.isLoading) {
                    Text("Loading")
                }
            }
        }
    }
}