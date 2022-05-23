package repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import model.request.LoginRequest
import model.response.BaseResponse
import model.response.LoginResponse
import ui.json
import ui.url

interface AccountRepository {

    suspend fun login(username: String, password: String): LoginResponse
}

class AccountRepositoryImpl(
    private val client: HttpClient
) : AccountRepository {

    override suspend fun login(
        username: String,
        password: String
    ): LoginResponse {
        val response: HttpResponse = client.post("$url/login") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(LoginRequest(username, password)))
        }

        val stringResponse = response.body<String>().toString()

        return json.decodeFromString<BaseResponse<LoginResponse>>(stringResponse).data
    }

}