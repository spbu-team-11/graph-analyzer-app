package view.main.targets

import tornadofx.checkbox

class Checkboxes : Targets {

    val checkboxes = mapOf(
        Constants.linLog to checkbox {
            isIndeterminate = false
        },

        Constants.outboundAttraction to checkbox {
            isIndeterminate = false
        },

        Constants.strongGravity to checkbox {
            isIndeterminate = false
        }
    )
}