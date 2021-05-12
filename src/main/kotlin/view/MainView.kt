package view

import com.sun.glass.ui.CommonDialogs
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


class MainView : View("Graph visualizer") {

    private val defaultMinWidthLeft = 155.0
    private val defaultMinWidthBottom = 80.0
    private val alerter = Alerter()

    private var graph = readSampleGraph("1")
    private var graphView = GraphView(graph)
    private var graphInfo =text( "")

    private val circularPlacementStrategy: CircularRepresentationStrategy by inject<CircularPlacementStrategy>()
    private val forcePlacementStrategy: ForceRepresentationStrategy by inject<ForcePlacementStrategy>()
    private val paintingStrategy: PaintingStrategy by inject<PaintingByCommunitiesStrategy>()

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

    override val root = borderpane {
        top = setupMenuBar()

        left = vbox(10) {
            add(nIteration)
            add(resolution)

            button("Find communities") {
                minWidth = defaultMinWidthLeft
                action {
                    showCommunities<String, Long>(nIteration.value.toInt().toString(), resolution.value.toString())
                }
            }

            hbox(5 / 3) {
                nIteration2 = textfield { maxWidth = 50.0 }
                textfield { maxWidth = 50.0 }
                gravity = textfield { maxWidth = 50.0 }
            }

            button("Make layout") {
                minWidth = defaultMinWidthLeft
                action {
                    forceLayout(nIteration2.text, gravity.text)
                }

            }

            button("Find ...") {
                minWidth = defaultMinWidthLeft
                action {

                }
            }

            button("Reset default settings") {
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

    private fun forceLayout(nIteration: String, gravity: String?) {
        currentStage?.apply {
            forcePlacementStrategy.place(
                graphView,
                nIteration,
                gravity,
                width - props.vertex.radius.get() * 5,
                height - props.vertex.radius.get() * 5,
            )
        }
    }

    private fun <V, E> showCommunities(nIteration: String, resolution: String) {
        currentStage?.apply {
            paintingStrategy.showCommunities<V, E>(graph, graphView, nIteration, resolution)
        }
    }

    private fun readSampleGraph(i: String): UndirectedGraph<String, Long> {
        return props.SAMPLE_GRAPH[i] ?: UndirectedGraph()
    }

    private fun <V, E> showGraph() {
        graphView = GraphView(graph)
        root.center {
            add(graphView)
        }
        arrangeVertices()
    }

    private fun openGraph(){
        val chooser = FileChooser()
        with(chooser){
            title = "Open graph"
            extensionFilters.add(FileChooser.ExtensionFilter("kek","*.pdf"))
        }

        val file = chooser.showOpenDialog(this.currentWindow)
    }

    private fun saveGraph(){
        val chooser = FileChooser()
        with(chooser){
            title = "Save graph"
            extensionFilters.add(FileChooser.ExtensionFilter("kek","*.pdf"))
        }

        val file = chooser.showSaveDialog(this.currentWindow)
    }

    private fun setupMenuBar(): MenuBar {
        val menuBar = MenuBar()

        val showMenu = Menu("Labels")

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

        val fileMenu = Menu("File")
        val open = MenuItem("Open")
        open.setOnAction { openGraph() }
        val save = MenuItem("Save")
        save.setOnAction { saveGraph() }
        with(fileMenu.items) {
            add(open)
            add(save)
        }

        val helpMenu = Menu("Help")
        val help = MenuItem("Help")
        help.setOnAction { alerter.alertHelp() }
        helpMenu.items.add(help)

        val examplesMenu = Menu("Examples")
        for (exampleName in props.SAMPLE_GRAPH.keys.reversed()) {
            val example = MenuItem(exampleName)
            example.setOnAction {
                graph = props.SAMPLE_GRAPH[exampleName]!!
                showGraph<String, Long>()
            }
            examplesMenu.items.add(example)
        }

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

        with(menuBar.menus) {
            add(fileMenu)
            add(helpMenu)
            add(examplesMenu)
            add(showMenu)
            add(settingsMenu)
        }

        return menuBar
    }
}
