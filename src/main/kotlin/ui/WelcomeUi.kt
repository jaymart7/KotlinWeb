package ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.Text

@Composable
fun WelcomeUi() {
    H1 { Text("Welcome") }

    H5 { Text("Loading") }

}