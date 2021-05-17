package view.MainView


import javafx.event.EventDispatchChain
import javafx.event.EventTarget
import javafx.scene.text.Text
import tornadofx.*
import view.props

class Texts: EventTarget{

    override fun buildEventDispatchChain(tail: EventDispatchChain?): EventDispatchChain {
        throw UnsupportedOperationException("not implemented")
    }

    var texts: Array<Text> = arrayOf(
        text(" Iteration: "),
        text(" Resolution:"),
        text(" Iteration:"),
        text(" Gravity:  "),
        text(" Logarithmic attraction mode:    "),
        text(" Outbound attraction mode:       "),
        text(" Strong gravity mode:            "),
        text(" SR-coef: ")
    )

    init{
        for(i in texts){
            i.fillProperty().bind(props.GUI.darkThemeText)
        }
    }



}