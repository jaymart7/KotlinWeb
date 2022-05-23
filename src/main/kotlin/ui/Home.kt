package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import model.Product
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
fun HomeScreen(updateScreen: (screen: Screen) -> Unit) {

    val productList = remember { mutableStateOf(emptyList<Product>()) }

    scope.launch {
        productList.value = getProductList().subList(0, 10)
    }

    H1 { Text("Welcome") }

    productList.value.forEach { product ->
        Text(product.title)
        Br()
    }

    Br()

    Button(attrs = {
        onClick {
            updateScreen(Screen.LOGIN)
        }
    }) {
        Text("Logout")
    }
}

suspend fun getProductList(): List<Product> {
    val response: HttpResponse = httpClient.get("https://jsonplaceholder.typicode.com/posts")

    return json.decodeFromString(
        response.body<String>().toString()
    )
}