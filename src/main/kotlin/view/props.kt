package view

import model.UndirectedGraph

import tornadofx.booleanProperty
import tornadofx.doubleProperty
import kotlin.random.Random

@Suppress("ClassName")
object props {
    object layout {
        val auto = booleanProperty()
    }

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
        val darkTheme = booleanProperty(false)
        val leftMenu = booleanProperty(true)
    }
}

