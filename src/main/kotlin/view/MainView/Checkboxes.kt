package view.MainView

import javafx.event.EventDispatchChain
import javafx.event.EventTarget
import tornadofx.checkbox

class Checkboxes : EventTarget {

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }

    val checkboxes = arrayOf(

        checkbox {
            isIndeterminate = false
        },

        checkbox {
            isIndeterminate = false
        },

        checkbox {
            isIndeterminate = false
        }
    )
}