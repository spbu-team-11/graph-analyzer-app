package view.main.targets

import tornadofx.checkbox

class Checkboxes : Targets {

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