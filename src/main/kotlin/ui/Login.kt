package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import model.request.LoginRequest
import model.response.BaseResponse
import model.response.LoginResponse
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*

@Composable
fun LoginScreen(updateScreen: (screen: Screen) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val loading = remember { mutableStateOf(false) }

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
                        scope.launch {
                            loading.value = true
                            login(username.value, password.value)
                            loading.value = false
                            updateScreen(Screen.HOME)
                        }
                    }
                }) { Text("Login") }

                if (loading.value) {
                    Text("Loading")
                }
            }
        }
    }
}

suspend fun login(username: String, password: String) {
    val response: HttpResponse = client.post("$url/login") {
        contentType(ContentType.Application.Json)
        setBody(json.encodeToString(LoginRequest(username, password)))
    }

    val stringResponse = response.body<String>().toString()

    val loginResponse = json.decodeFromString<BaseResponse<LoginResponse>>(stringResponse).data
    println(loginResponse.name)
}