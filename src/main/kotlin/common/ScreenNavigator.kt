package common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.AccountRepository
import ui.scope

interface ScreenNavigator {

    val state: StateFlow<State>

    fun updateScreen(screen: Screen)

    data class State(
        val screen: Screen = Screen.WELCOME
    )
}

class ScreenNavigatorImpl(
    accountRepository: AccountRepository
) : ScreenNavigator {

    private val _state = MutableStateFlow(ScreenNavigator.State())
    override val state: StateFlow<ScreenNavigator.State> = _state

    init {
        if (accountRepository.hasToken()) {
            scope.launch {
                try {
                    accountRepository.getAccount()
                    _state.update { it.copy(screen = Screen.HOME) }
                } catch (e: Exception) {
                    _state.update { it.copy(screen = Screen.LOGIN) }
                }
            }
        } else {
            _state.update { it.copy(screen = Screen.LOGIN) }
        }
    }

    override fun updateScreen(screen: Screen) {
        _state.update { it.copy(screen = screen) }
    }
}

enum class Screen {
    WELCOME,
    LOGIN,
    HOME
}