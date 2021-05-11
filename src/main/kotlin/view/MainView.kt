package view

import com.example.demo.logger.log
import com.sun.javafx.scene.control.MenuBarButton
import controller.*
import javafx.beans.property.BooleanProperty
import javafx.beans.property.BooleanPropertyBase
import javafx.collections.FXCollections
import javafx.scene.control.*
import model.UndirectedGraph
import tornadofx.*
import utils.Alerter


class MainView : View("Graph visualizer") {

    private val defaultMinWidthLeft = 155.0
    private val defaultMinWidthBottom = 80.0
    private val alerter = Alerter()

    private var graph = readSampleGraph("1")
    private var graphView = GraphView(graph)
    private val circularPlacementStrategy: RepresentationStrategy by inject<CircularPlacementStrategy>()
    private val forcePlacementStrategy: ForceRepresentationStrategy by inject<ForcePlacementStrategy>()
    private val paintingStrategy: PaintingStrategy by inject<PaintingByCommunitiesStrategy>()


    override val root = borderpane {

        var nIteration = textfield { }
        var nIteration2 = textfield {  }
        var gravity = textfield {}
        var resolution = textfield { }
        var index = choicebox<String> { }

        top = setupMenuBar()

        left = vbox(10) {
            hbox(5) {
                nIteration = textfield { maxWidth = 75.0 }
                resolution = textfield { maxWidth = 75.0 }
            }
            button("Find communities") {
                minWidth = defaultMinWidthLeft
                action {
                    showCommunities<String, Long>(nIteration.text, resolution.text)
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
            textfield { maxWidth = defaultMinWidthLeft }
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

        }

        bottom = vbox(5) {
//            hbox(5) {
//                button("Save as") {
//                    minWidth = defaultMinWidthBottom
//                    action {
//                        val dir = chooseDirectory("save")
//                    }
//                }
//                saveInfoDataBase = choicebox {
//                    minWidth = defaultMinWidthBottom
//                    items = FXCollections.observableArrayList("MySQL", "txt", "Neo4j")
//                }
//            }
//            hbox(5) {
//                button("Open") {
//                    minWidth = defaultMinWidthBottom
//                    action {
//                        val file = chooseFile("load", arrayOf())
//                    }
//                }
//            }
        }

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

    private fun setupMenuBar(): MenuBar {
        val menuBar = MenuBar()

        val showMenu = Menu("Settings")

        val checkShowVertexLabel = CheckMenuItem("Vertex label")
        checkShowVertexLabel.setOnAction { e -> props.vertex.label.set(!props.vertex.label.value) }

        val checkShowEdgesLabel = CheckMenuItem("Edges label")
        checkShowEdgesLabel.setOnAction { e -> props.edge.label.set(!props.edge.label.value) }

        val checkShowCommunitiesLabel = CheckMenuItem("Community label")
        checkShowCommunitiesLabel.setOnAction { e -> props.vertex.community.set(!props.vertex.community.value) }

        with(showMenu.items) {
            add(checkShowVertexLabel)
            add(checkShowEdgesLabel)
            add(checkShowCommunitiesLabel)
        }

        val fileMenu = Menu("File")
        val open = MenuItem("Open")
        val close = MenuItem("Close")
        with(fileMenu.items) {
            add(open)
            add(close)
        }

        val helpMenu = Menu("Help")
        val help = MenuItem("Help")
        help.setOnAction { e -> alerter.alertHelp() }
        helpMenu.items.add(help)

        val examplesMenu = Menu("Examples")
        for (exampleName in props.SAMPLE_GRAPH.keys.reversed()) {
            val example = MenuItem(exampleName)
            example.setOnAction { e ->
                graph = props.SAMPLE_GRAPH[exampleName]!!
                showGraph<String, Long>()
            }
            examplesMenu.items.add(example)
        }


        with(menuBar.menus) {
            add(fileMenu)
            add(helpMenu)
            add(examplesMenu)
            add(showMenu)
        }

        return menuBar
    }

}
