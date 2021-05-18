package view.main.targets

import javafx.event.EventDispatchChain
import javafx.event.EventTarget

interface Targets : EventTarget {

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }
}