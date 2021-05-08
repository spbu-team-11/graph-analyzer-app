package view

import controller.CircularPlacementStrategy
import controller.RepresentationStrategy
import com.example.demo.logger.log
import model.Graph
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import tornadofx.*


class MainView : View("Graph visualizer") {
    private var graph = readGraph()
    private val defaultMinWidthLeft = 150.0
    private val defaultMinWidthBottom = 70.0
    private val graphView = GraphView(graph)
    private val strategy: RepresentationStrategy by inject<CircularPlacementStrategy>()

    override val root = borderpane {
        var nIteration: TextField = textfield {  }
        var resolution: TextField = textfield {  }

        center {
            add(graphView)
        }
        left = vbox(10) {

            button(" Find communities") {
                minWidth = defaultMinWidthLeft
                action {
                    showCommunities<String, Long>(nIteration.text, resolution.text)
                }
            }
            hbox{
                nIteration = textfield {  maxWidth = 75.0}
                resolution = textfield {  maxWidth = 75.0}
            }


            button(" Find ....") {
                minWidth = defaultMinWidthLeft
                action {

                }
            }
            button("Make layout") {
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
            button("Help") {
                minWidth = defaultMinWidthLeft
                action {
                    alert(Alert.AlertType.INFORMATION, "Ссылка на гит с инфой")
                }
            }
        }
        right = vbox(10) {
            checkbox("Show vertices labels ", props.vertex.label) {
                action {
                    log("vertex labels are ${if (isSelected) "enabled" else "disabled"}")
                }
            }
            checkbox("Show edges labels ", props.edge.label) {
                action {
                    log("edges labels are ${if (isSelected) "enabled" else "disabled"}")
                }
            }
            checkbox("Show communities labels ", props.vertex.community) {
                action {
                    log("communities labels are ${if (isSelected) "enabled" else "disabled"}")
                }
            }
        }
        bottom = hbox(5) {
            vbox(10) {
                button("Save as") {
                    minWidth = defaultMinWidthBottom
                    action {
                        val dir = chooseDirectory("save")
                    }
                }
                button("Load from") {
                    minWidth = defaultMinWidthBottom
                    action {
                        val file = chooseFile("load", arrayOf())
                    }
                }
            }
            vbox(10) {
                choicebox<String> {
                    minWidth = defaultMinWidthBottom
                    items = FXCollections.observableArrayList("MySQL", "txt", "Neo4j")
                }
                choicebox<String> {
                    minWidth = defaultMinWidthBottom
                    items = FXCollections.observableArrayList("MySQL", "txt", "Neo4j")
                }
            }
        }
    }


    init {
        arrangeVertices()
    }

    private fun arrangeVertices() {
        currentStage?.apply {
            strategy.place(
                width - props.vertex.radius.get() * 5,
                height - props.vertex.radius.get() * 5,
                graphView.vertices(),
            )
        }
    }

    private fun <V,E> showCommunities(nIteration: String, resolution: String) {
        currentStage?.apply {
            strategy.showCommunities<V, E>(graphView, nIteration, resolution)
        }
    }

    private fun readGraph(): Graph<String, Long> {
        return props.SAMPLE_GRAPH
    }
}
