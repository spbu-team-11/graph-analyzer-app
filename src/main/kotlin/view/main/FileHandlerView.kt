package view.main

import javafx.stage.FileChooser

@ExperimentalStdlibApi
class FileHandlerView(private val mainView: MainView, private val drawer: GraphDrawer) {

    internal fun openGraph() {
        val chooser = FileChooser()
        with(chooser) {
            title = "Open graph"
            extensionFilters.add(FileChooser.ExtensionFilter("SQLite", "*.db"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
        }

        val file = chooser.showOpenDialog(mainView.currentWindow)
        if (file != null) {
            when (file.extension) {
                "db" -> {
                    val data = mainView.SQliteFileHandlingStrategy.open(file)
                    mainView.graph = data.first
                    if (data.second != null) {
                        mainView.graphView = data.second!!
                        drawer.showGraphWithGraphView()
                    } else
                        drawer.showGraphWithoutGraphView()
                }
                "csv" -> {
                    val data = mainView.csvStrategy.open(file)
                    mainView.graph = data.first
                    if (data.second != null) {
                        mainView.graphView = data.second!!
                        var hasCoordinates = false
                        mainView.graphView.vertices().onEach {
                            if (it.centerX != 0.0 || it.centerY != 0.0) {
                                hasCoordinates = true
                                return@onEach
                            }
                        }
                        if (!hasCoordinates) drawer.showGraphWithoutGraphView()
                        else drawer.showGraphWithGraphView()
                    } else drawer.showGraphWithoutGraphView()
                }
            }
            drawer.updateGraphInfo()
        }
    }

    internal fun saveGraph() {
        val chooser = FileChooser()
        with(chooser) {
            title = "Save graph"
            extensionFilters.add(FileChooser.ExtensionFilter("SQLite", "*.db"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
        }

        val file = chooser.showSaveDialog(mainView.currentWindow)
        if (file != null) {
            when (file.extension) {
                "db" -> mainView.SQliteFileHandlingStrategy.save(file, mainView.graph, mainView.graphView)
                "csv" -> mainView.csvStrategy.save(file, mainView.graph, mainView.graphView)
            }
        }
    }
}