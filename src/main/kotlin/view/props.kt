package view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color
import tornadofx.booleanProperty
import tornadofx.doubleProperty
import tornadofx.stringProperty

@Suppress("ClassName")
object props {
    object vertex {
        val radius = doubleProperty(5.0)
        val pressedRadius = doubleProperty(15.0)
        val label = booleanProperty()
        val community = booleanProperty()
    }

    object edge {
        val label = booleanProperty()
    }

    object GUI {
        val darkThemeText = SimpleObjectProperty(Color.BLACK)
        val darkTheme = stringProperty("-fx-base:white")
        val leftMenu = booleanProperty(true)
    }
}

