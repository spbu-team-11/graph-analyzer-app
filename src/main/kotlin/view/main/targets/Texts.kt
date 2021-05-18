package view.main.targets

import view.props

import tornadofx.*

class Texts : Targets {

    var texts = mapOf(
        Constants.communitiesIterations to text(" Iteration: "),
        Constants.communitiesResolution to text(" Resolution:"),
        Constants.layoutIterations to text(" Iteration:"),
        Constants.layoutGravity to text(" Gravity:  "),
        Constants.linLog to text(" Logarithmic attraction mode:    "),
        Constants.outboundAttraction to text(" Outbound attraction mode:       "),
        Constants.strongGravity to text(" Strong gravity mode:            "),
        Constants.srCoefficient to text(" SR-coef: ")
    )

    init {
        for (i in texts) {
            i.value.fillProperty().bind(props.GUI.darkThemeText)
        }
    }
}