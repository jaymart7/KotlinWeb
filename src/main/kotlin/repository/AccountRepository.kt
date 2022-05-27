package repository

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import model.request.LoginRequest
import model.response.AccountResponse
import model.response.TokenResponse
import network.json
import network.url

interface AccountRepository {

    suspend fun login(username: String, password: String)

    suspend fun getAccount(): AccountResponse

    fun logout()

    fun hasToken(): Boolean
}

class AccountRepositoryImpl(
    private val client: HttpClient,
    private val sessionRepository: SessionRepository
) : AccountRepository {

    override suspend fun login(
        username: String,
        password: String
    ) {
        val response: HttpResponse = client.post("$url/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }
        val token = json.decodeFromString<TokenResponse>(response.bodyAsText()).token
        sessionRepository.setToken(token)
    }

    override suspend fun getAccount(): AccountResponse {
        val token = sessionRepository.getToken()
        val response = client.get("$url/account") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

        return json.decodeFromString(response.bodyAsText())
    }

    override fun logout() {
        sessionRepository.clearSession()
    }

    override fun hasToken(): Boolean = sessionRepository.getToken().isNullOrBlank().not()

}