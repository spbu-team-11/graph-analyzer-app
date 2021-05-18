package view.main.targets

import view.props

import tornadofx.*

class Texts : Targets {

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