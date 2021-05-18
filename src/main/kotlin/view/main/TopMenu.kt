package view.main

import view.props

import com.example.demo.logger.log
import javafx.scene.control.CheckMenuItem
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.paint.Color
import java.io.File

@ExperimentalStdlibApi
class TopMenu(
    private val mainView: MainView,
    private val drawer: GraphDrawer,
    private val fileHandlerView: FileHandlerView
) {

    fun setupMenuBar(): MenuBar {
        val menuBar = MenuBar()

        with(menuBar.menus) {
            add(setupFileMenu())
            add(setupExamplesMenu())
            add(setupShowMenu())
            add(setupSettingsMenu())
            add(setupHelpMenu())
        }

        return menuBar
    }

    private fun setupShowMenu(): Menu {
        val showMenu = Menu("Labels")

        val checkShowVertexLabel = CheckMenuItem("Vertex")
        checkShowVertexLabel.setOnAction { props.vertex.label.set(!props.vertex.label.value) }

        val checkShowEdgesLabel = CheckMenuItem("Edge")
        checkShowEdgesLabel.setOnAction { props.edge.label.set(!props.edge.label.value) }

        val checkShowCommunitiesLabel = CheckMenuItem("Community")
        checkShowCommunitiesLabel.setOnAction { props.vertex.community.set(!props.vertex.community.value) }

        with(showMenu.items) {
            add(checkShowVertexLabel)
            add(checkShowEdgesLabel)
            add(checkShowCommunitiesLabel)
        }

        return showMenu
    }

    private fun setupFileMenu(): Menu {
        val fileMenu = Menu("File")

        val open = MenuItem("Open")
        open.setOnAction { fileHandlerView.openGraph() }

        val save = MenuItem("Save")
        save.setOnAction { fileHandlerView.saveGraph() }
        with(fileMenu.items) {
            add(open)
            add(save)
        }

        return fileMenu
    }

    private fun setupHelpMenu(): Menu {
        val helpMenu = Menu("Help")

        val help = MenuItem("Help")
        help.setOnAction {
            mainView.hostServices.showDocument(File("help.pdf").absolutePath)
        }

        val exit = MenuItem("Exit")
        exit.setOnAction { mainView.close() }

        helpMenu.items.add(help)
        helpMenu.items.add(exit)

        return helpMenu
    }

    private fun setupExamplesMenu(): Menu {
        val examplesMenu = Menu("Examples")

        val exampleDir = "examples"
        for (exampleFileName in File(exampleDir).list()) {
            val example = MenuItem(exampleFileName.substringBefore("."))
            log(exampleFileName)
            example.setOnAction {
                when (exampleFileName.substringAfter(".")) {
                    "csv" -> mainView.graph = mainView.csvStrategy.open(File("$exampleDir\\$exampleFileName")).first
                    "db" -> mainView.graph =
                        mainView.SQliteFileHandlingStrategy.open(File("$exampleDir\\$exampleFileName")).first
                }
                drawer.showGraphWithoutGraphView()
            }

            examplesMenu.items.add(example)
        }
        return examplesMenu
    }

    private fun setupSettingsMenu(): Menu {
        val settingsMenu = Menu("Settings")

        val checkLeftMenu = CheckMenuItem("Hide menu")
        checkLeftMenu.setOnAction {
            props.GUI.leftMenu.set(!props.GUI.leftMenu.value)

        }

        val checkDarkTheme = CheckMenuItem("Dark theme")
        checkDarkTheme.setOnAction {
            if (props.GUI.darkTheme.value == "-fx-base:black") {
                props.GUI.darkTheme.set("-fx-base:white")
                props.GUI.darkThemeText.set(Color.BLACK)
            } else {
                props.GUI.darkTheme.set("-fx-base:black")
                props.GUI.darkThemeText.set(Color.WHITE)
            }
        }

        with(settingsMenu.items) {
            add(checkLeftMenu)
            add(checkDarkTheme)
        }

        return settingsMenu
    }
}