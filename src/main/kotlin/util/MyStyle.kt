package util

import org.jetbrains.compose.web.css.*

object MyStyle : StyleSheet() {

    init {
        "td, tr table" style {
            padding(16.px)
            textAlign("center")
            width(value = 50.percent)
            border {
                width = 1.px
                style = LineStyle.Solid
                color = Color.aliceblue
            }
        }
    }
}