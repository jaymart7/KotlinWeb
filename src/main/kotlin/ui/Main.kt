package ui

import androidx.compose.runtime.collectAsState
import common.LoginImpl
import network.httpClient
import kotlinx.coroutines.MainScope
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import repository.AccountRepositoryImpl
import common.Screen
import common.ScreenNavigatorImpl
import util.MyStyle

val scope = MainScope()

fun main() {
    val accountRepository = AccountRepositoryImpl(httpClient)
    val screenNavigator = ScreenNavigatorImpl()
    val login = LoginImpl(accountRepository, screenNavigator)

    renderComposable(rootElementId = "root") {
        Style(MyStyle)
        when (screenNavigator.state.collectAsState().value.screen) {
            Screen.LOGIN -> LoginScreen(login)
            Screen.HOME -> HomeScreen { screenNavigator.updateScreen(it) }
        }
    }
}