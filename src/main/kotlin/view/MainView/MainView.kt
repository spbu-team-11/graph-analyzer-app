package view.MainView

import controller.fileHandler.CSVFileHandlingStrategy
import controller.fileHandler.FileHandlingStrategy
import controller.fileHandler.SQLiteFileHandlingStrategy
import controller.highlighter.HighlightVerticesStrategy
import controller.highlighter.HighlighterStrategy

import controller.painting.PaintingByCommunitiesStrategy
import controller.painting.PaintingStrategy
import controller.placement.circular.CircularPlacementStrategy
import controller.placement.force.ForcePlacementStrategy
import controller.placement.force.ForceRepresentationStrategy
import controller.placement.circular.CircularRepresentationStrategy
import model.UndirectedGraph

import javafx.scene.control.*
import javafx.stage.FileChooser
import tornadofx.*
import view.GraphView
import view.props

@ExperimentalStdlibApi
class MainView : View("Graph visualizer") {

    internal val defaultMinWidthLeft = 155.0

    internal var graph = UndirectedGraph()
    internal var graphView = GraphView(graph)

    internal val circularPlacementStrategy: CircularRepresentationStrategy by inject<CircularPlacementStrategy>()
    internal val forcePlacementStrategy: ForceRepresentationStrategy by inject<ForcePlacementStrategy>()
    internal val highlightVerticesStrategy: HighlightVerticesStrategy by inject<HighlighterStrategy>()
    internal val paintingStrategy: PaintingStrategy by inject<PaintingByCommunitiesStrategy>()

    internal val SQliteFileHandlingStrategy: FileHandlingStrategy by inject<SQLiteFileHandlingStrategy>()
    internal val csvStrategy: FileHandlingStrategy by inject<CSVFileHandlingStrategy>()

    internal var nIterationLeiden = slider {
        min = 0.0
        max = 1000.0
        value = 50.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 200.0
        minWidth = 150.0
    }

    internal var resolution = slider {
        min = 0.0
        max = 1.0
        value = 0.5
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 0.2
        minWidth = 150.0
    }

    internal var nIteration2 = slider {
        min = 0.0
        max = 20000.0
        value = 50.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 5000.0
        minWidth = 150.0
    }

    internal var gravity = slider {
        min = 0.0
        max = 100.0
        value = 1.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 20.0
        minWidth = 150.0
    }

    internal var highlightValue = slider {
        min = 0.0
        max = 10.0
        value = 10.0
        isShowTickMarks = true
        isShowTickLabels = true
        majorTickUnit = 2.5
        minWidth = 150.0
    }

    internal var isLinLogMode = checkbox {
        isIndeterminate = false
    }

    internal var isOutboundAttraction = checkbox {
        isIndeterminate = false
    }

    internal var isStrongGravity = checkbox {
        isIndeterminate = false
    }

    internal var texts = Texts().texts

    internal var graphInfo = text(" Vertices: - \n Edges: - \n Communities: -")

    override val root = borderpane {
        top = TopMenu(this@MainView).setupMenuBar()

        left = vbox(10) {
            hbox(10) {
                add(texts[0])
                add(nIterationLeiden)
            }
            hbox(10) {
                add(texts[1])
                add(resolution)
            }

            button("Detect communities") {
                minWidth = defaultMinWidthLeft
                action {
                    showCommunities(nIterationLeiden.value.toInt().toString(), resolution.value.toString())
                }
            }

            hbox(10) {
                add(texts[2])
                add(nIteration2)
            }
            hbox(10) {
                add(texts[3])
                add(gravity)
            }
            hbox(10) {
                add(texts[4])
                add(isLinLogMode)
            }
            hbox(10) {
                add(texts[5])
                add(isOutboundAttraction)
            }
            hbox(10) {
                add(texts[6])
                add(isStrongGravity)
            }

            button("Layout") {
                minWidth = defaultMinWidthLeft
                action {
                    forceLayout(
                        nIteration2.value.toInt().toString(),
                        gravity.value.toString(),
                        isLinLogMode.isSelected,
                        isOutboundAttraction.isSelected,
                        isStrongGravity.isSelected
                    )
                }
            }
            hbox(10) {
                add(texts[7])
                add(highlightValue)
            }

            button("Highlight vertices") {
                minWidth = defaultMinWidthLeft
                action {
                    highlight(highlightValue.value)
                }
            }
        }

        bottom = graphInfo

        left.visibleProperty().bind(props.GUI.leftMenu)
        graphInfo.fillProperty().bind(props.GUI.darkThemeText)
        this.styleProperty().bind(props.GUI.darkTheme)
    }

    internal fun arrangeVertices() {
        currentStage?.apply {
            circularPlacementStrategy.place(
                width - props.vertex.radius.get() * 10,
                height - props.vertex.radius.get() * 10,
                graphView.vertices(),
            )
        }
    }

    internal fun forceLayout(
        nIteration: String,
        gravity: String?,
        isLinLogMode: Boolean,
        isOutboundAttraction: Boolean,
        isStrongGravity: Boolean,
    ) {
        currentStage?.apply {
            forcePlacementStrategy.place(
                graphView,
                nIteration,
                gravity,
                isLinLogMode,
                isOutboundAttraction,
                isStrongGravity,
                width - props.vertex.radius.get() * 10,
                height - props.vertex.radius.get() * 10
            )
        }
    }

    internal fun highlight(value: Double) {
        currentStage?.apply {
            highlightVerticesStrategy.highlight(graphView, value)
        }
    }

    internal fun showCommunities(nIteration: String, resolution: String) {
        currentStage?.apply {
            paintingStrategy.showCommunities(graph, graphView, nIteration, resolution)
            updateGraphInfo()
        }
    }

    internal fun updateGraphInfo() {
        var maxCommunity = -1
        graph.vertices().forEach {
            if (it.community > maxCommunity) maxCommunity = it.community
        }
        graphInfo.text = " Vertices: ${graph.vertices().size} \n Edges: ${graph.edges().size} " +
                "\n Communities: ${if (maxCommunity == -1) "-" else (maxCommunity + 1)}"
    }

    internal fun showGraphWithGraphView() {
        graphView.edges()
            .onEach {
                if (it.first.color == it.second.color)
                    it.stroke = it.first.color
            }
        root.center {
            add(graphView)
        }
        updateGraphInfo()
    }

    internal fun showGraphWithoutGraphView() {
        graphView = GraphView(graph)
        root.center {
            add(graphView)
        }
        arrangeVertices()
        updateGraphInfo()
    }

    internal fun openGraph() {
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
                "csv" -> {
                    val data = csvStrategy.open(file)
                    graph = data.first
                    if (data.second != null) {
                        graphView = data.second!!
                        var hasCoordinates = false
                        graphView.vertices().onEach {
                            if (it.centerX != 0.0 || it.centerY != 0.0) {
                                hasCoordinates = true
                                return@onEach
                            }
                        }
                        if (!hasCoordinates) showGraphWithoutGraphView()
                        else showGraphWithGraphView()
                    } else showGraphWithoutGraphView()
                }
            }
            updateGraphInfo()
        }
    }

    internal fun saveGraph() {
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
                "csv" -> csvStrategy.save(file, graph, graphView)
            }
        }
    }
}
