package view

import controller.fileHandler.CSVFileHandlingStrategy
import controller.fileHandler.FileHandlingStrategy
import controller.fileHandler.SQLiteFileHandlingStrategy

import controller.painting.PaintingByCommunitiesStrategy
import controller.painting.PaintingStrategy
import controller.placement.circular.CircularPlacementStrategy
import controller.placement.force.ForcePlacementStrategy
import controller.placement.force.ForceRepresentationStrategy
import controller.placement.circular.CircularRepresentationStrategy
import model.UndirectedGraph
import utils.Alerter

import javafx.scene.control.*
import javafx.stage.FileChooser
import tornadofx.*

@ExperimentalStdlibApi
class MainView : View("Graph visualizer") {

    private val defaultMinWidthLeft = 155.0
    private val defaultMinWidthBottom = 80.0
    private val alerter = Alerter()

    private var graph = readSampleGraph("1")
    private var graphView = GraphView(graph)
    private var graphInfo = text("")

    private val circularPlacementStrategy: CircularRepresentationStrategy by inject<CircularPlacementStrategy>()
    private val forcePlacementStrategy: ForceRepresentationStrategy by inject<ForcePlacementStrategy>()
    private val paintingStrategy: PaintingStrategy by inject<PaintingByCommunitiesStrategy>()

    private val SQliteFileHandlingStrategy: FileHandlingStrategy by inject<SQLiteFileHandlingStrategy>()
    private val csvStrategy: FileHandlingStrategy by inject<CSVFileHandlingStrategy>()

    private var nIteration = slider {
        min = 0.0
        max = 100.0
        value = 50.0
    }

    private var resolution = slider {
        min = 0.0
        max = 1.0
        value = 0.5
    }

    private var nIteration2 = textfield { }
    private var gravity = textfield { }
    private var isLinLogMode = checkbox("LinLog Mode") {
        isIndeterminate = false
    }

    override val root = borderpane {
        top = setupMenuBar()

        left = vbox(10) {
            add(nIteration)
            add(resolution)

            button("Detect communities") {
                minWidth = defaultMinWidthLeft
                action {
                    showCommunities(nIteration.value.toInt().toString(), resolution.value.toString())
                }
            }

            hbox(5 / 3) {
                nIteration2 = textfield { maxWidth = 50.0 }
                gravity = textfield { maxWidth = 50.0 }
                isLinLogMode = checkbox("LinLog Mode") { }
            }

            button("Layout") {
                minWidth = defaultMinWidthLeft
                action {
                    forceLayout(nIteration2.text, gravity.text, isLinLogMode.isSelected)
                }

            }

            button("Reset default properties") {
                minWidth = defaultMinWidthLeft
                action {
                    arrangeVertices()
                }
            }

            add(graphInfo)

        }
        left.visibleProperty().bind(props.GUI.leftMenu)
    }

    init {
        arrangeVertices()
    }

    private fun arrangeVertices() {
        currentStage?.apply {
            circularPlacementStrategy.place(
                width - props.vertex.radius.get() * 5,
                height - props.vertex.radius.get() * 5,
                graphView.vertices(),
            )
        }
    }

    private fun forceLayout(nIteration: String, gravity: String?, isLinLogMode: Boolean) {
        currentStage?.apply {
            forcePlacementStrategy.place(
                graphView,
                nIteration,
                gravity,
                isLinLogMode,
                width - props.vertex.radius.get() * 5,
                height - props.vertex.radius.get() * 5,
            )
        }
    }

    private fun showCommunities(nIteration: String, resolution: String) {
        currentStage?.apply {
            paintingStrategy.showCommunities(graph, graphView, nIteration, resolution)
        }
    }

    private fun readSampleGraph(i: String): UndirectedGraph {
        return props.SAMPLE_GRAPH[i] ?: UndirectedGraph()
    }

    private fun showGraphWithGraphView() {
        root.center {
            add(graphView)
        }
    }

    private fun showGraphWithoutGraphView() {
        graphView = GraphView(graph)
        root.center {
            add(graphView)
        }
        arrangeVertices()
    }

    private fun openGraph() {
        val chooser = FileChooser()
        with(chooser) {
            title = "Open graph"
            extensionFilters.add(FileChooser.ExtensionFilter("SQLite", "*.db"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
        }

        val file = chooser.showOpenDialog(this.currentWindow)
        if (file != null) {
            when (file.extension) {
                "db" -> {
                    val data = SQliteFileHandlingStrategy.open(file)
                    graph = data.first
                    if (data.second != null) {
                        graphView = data.second!!
                        showGraphWithGraphView()
                    } else
                        showGraphWithoutGraphView()
                }
            }
        }
    }

    private fun saveGraph() {
        val chooser = FileChooser()
        with(chooser) {
            title = "Save graph"
            extensionFilters.add(FileChooser.ExtensionFilter("SQLite", "*.db"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
        }

        val file = chooser.showSaveDialog(this.currentWindow)
        if (file != null) {
            when (file.extension) {
                "db" -> SQliteFileHandlingStrategy.save(file, graph, graphView)
            }
        }
    }

    private fun setupMenuBar(): MenuBar {
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
        val showMenu = Menu("Show")

        val checkShowVertexLabel = CheckMenuItem("Vertex label")
        checkShowVertexLabel.setOnAction { props.vertex.label.set(!props.vertex.label.value) }

        val checkShowEdgesLabel = CheckMenuItem("Edges label")
        checkShowEdgesLabel.setOnAction { props.edge.label.set(!props.edge.label.value) }

        val checkShowCommunitiesLabel = CheckMenuItem("Community label")
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
        open.setOnAction { openGraph() }

        val save = MenuItem("Save")
        save.setOnAction { saveGraph() }
        with(fileMenu.items) {
            add(open)
            add(save)
        }

        return fileMenu
    }

    private fun setupHelpMenu(): Menu {
        val helpMenu = Menu("Help")
        val help = MenuItem("Help")
        help.setOnAction { alerter.alertHelp() }
        helpMenu.items.add(help)

        return helpMenu
    }

    private fun setupExamplesMenu(): Menu {
        val examplesMenu = Menu("Examples")

        for (exampleName in props.SAMPLE_GRAPH.keys.reversed()) {
            val example = MenuItem(exampleName)
            example.setOnAction {
                graph = props.SAMPLE_GRAPH[exampleName]!!
                showGraphWithoutGraphView()
            }

            examplesMenu.items.add(example)
        }
        return examplesMenu
    }

    private fun setupSettingsMenu(): Menu {
        val settingsMenu = Menu("Settings")

        val checkLeftMenu = CheckMenuItem("Hide left menu")
        checkLeftMenu.setOnAction { props.GUI.leftMenu.set(!props.GUI.leftMenu.value) }

        val checkDarkTheme = CheckMenuItem("Dark theme")
        checkDarkTheme.setOnAction {
            props.GUI.darkTheme.set(!props.GUI.darkTheme.value)
            root.style = if (props.GUI.darkTheme.value) "-fx-base:black" else ""
        }

        with(settingsMenu.items) {
            add(checkLeftMenu)
            add(checkDarkTheme)
        }

        return settingsMenu
    }
}
