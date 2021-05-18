package view.main.targets

import tornadofx.slider

class Sliders : Targets {

    val sliders = hashMapOf(
        "Communities iterations" to slider {
        min = 0.0
        max = 1000.0
        value = 50.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 200.0
        minWidth = 150.0
    },

        "Communities resolution" to slider {
            min = 0.0
            max = 1.0
            value = 0.5
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 0.2
            minWidth = 150.0
        },

        "Layout iterations" to slider {
            min = 0.0
            max = 20000.0
            value = 50.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 5000.0
            minWidth = 150.0
        },

        "Layout gravity" to slider {
            min = 0.0
            max = 100.0
            value = 1.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 20.0
            minWidth = 150.0
        },

        "SR-coefficient" to slider {
            min = 0.0
            max = 10.0
            value = 10.0
            isShowTickMarks = true
            isShowTickLabels = true
            majorTickUnit = 2.5
            minWidth = 150.0
        })
}