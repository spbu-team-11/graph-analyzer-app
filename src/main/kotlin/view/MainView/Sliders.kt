package view.MainView

import javafx.event.EventDispatchChain
import javafx.event.EventTarget
import tornadofx.slider

class Sliders : EventTarget {

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }

    val sliders = arrayOf(slider {
        min = 0.0
        max = 1000.0
        value = 50.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 200.0
        minWidth = 150.0
    },

        slider {
            min = 0.0
            max = 1.0
            value = 0.5
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 0.2
            minWidth = 150.0
        },

        slider {
            min = 0.0
            max = 20000.0
            value = 50.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 5000.0
            minWidth = 150.0
        },

        slider {
            min = 0.0
            max = 100.0
            value = 1.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 20.0
            minWidth = 150.0
        },

        slider {
            min = 0.0
            max = 10.0
            value = 10.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 2.5
            minWidth = 150.0
        })
}