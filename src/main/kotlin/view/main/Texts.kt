package view.main

import view.props

import javafx.event.EventDispatchChain
import javafx.event.EventTarget
import javafx.scene.text.Text
import tornadofx.*

class Texts : EventTarget {

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }

    var texts = mapOf(
        "Communities iterations" to text(" Iteration: "),
        "Communities resolution" to text(" Resolution:"),
        "Layout iterations" to text(" Iteration:"),
        "Layout gravity" to text(" Gravity:  "),
        "Is linLog" to text(" Logarithmic attraction mode:    "),
        "Is outbound attraction" to text(" Outbound attraction mode:       "),
        "Is strong gravity" to text(" Strong gravity mode:            "),
        "SR-coefficient" to text(" SR-coef: ")
    )

    init {
        for (i in texts) {
            i.value.fillProperty().bind(props.GUI.darkThemeText)
        }
    }
}