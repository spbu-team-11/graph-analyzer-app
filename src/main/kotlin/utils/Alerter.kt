package utils

import javafx.scene.control.Alert
import tornadofx.alert

class Alerter {

    fun alertIncorrectArgs(message: String) {
        alert(Alert.AlertType.ERROR, "$message, for details click [Help]")
    }
}