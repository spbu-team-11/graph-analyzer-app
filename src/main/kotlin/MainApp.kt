import styles.Styles
import view.MainView

import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.App
import tornadofx.launch
import com.example.demo.logger.log

class MainApp : App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {
        log("Starting the application...")

        with(stage) {
            initStyle(StageStyle.UNDECORATED)
            width = 800.0
            height = 600.0
            isMaximized = true
        }

        super.start(stage)

        log("The application has started")
    }
}

fun main(args: Array<String>) {
    launch<MainApp>(args)
}
