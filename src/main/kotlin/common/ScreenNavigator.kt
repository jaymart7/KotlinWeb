package common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ScreenNavigator {

    val state: StateFlow<State>

    fun updateScreen(screen: Screen)

    data class State(
        val screen: Screen = Screen.LOGIN
    )
}

class ScreenNavigatorImpl : ScreenNavigator {

    private val _state = MutableStateFlow(ScreenNavigator.State())
    override val state: StateFlow<ScreenNavigator.State> = _state

    override fun updateScreen(screen: Screen) {
        _state.update { it.copy(screen = screen) }
    }
}

enum class Screen {
    LOGIN,
    HOME
}