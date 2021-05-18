package view.main

import javafx.event.EventDispatchChain
import javafx.event.EventTarget
import tornadofx.checkbox

class Checkboxes : EventTarget {

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }

    val checkboxes = mapOf(

        "Is linLog" to checkbox {
            isIndeterminate = false
        },
        "Is outbound attraction" to checkbox {
            isIndeterminate = false
        },

        "Is strong gravity" to checkbox {
            isIndeterminate = false
        }
    )
}