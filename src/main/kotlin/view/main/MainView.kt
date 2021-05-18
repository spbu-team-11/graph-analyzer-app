package view.main

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
import view.GraphView
import view.props
import view.main.targets.Checkboxes
import view.main.targets.Constants
import view.main.targets.Sliders
import view.main.targets.Texts

import javafx.scene.control.*
import tornadofx.*

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

    private val sliders = Sliders().sliders
    private val checkboxes = Checkboxes().checkboxes
    private val texts = Texts().texts
    private val drawer = GraphDrawer(this@MainView)
    private val fileHandlerView = FileHandlerView(this@MainView, drawer)

    val graphInfo = text(" Vertices: - \n Edges: - \n Communities: -")


    override val root = borderpane {
        top = TopMenu(this@MainView, drawer, fileHandlerView).setupMenuBar()

        left = vbox(10) {
            hbox(10) {
                add(texts[Constants.communitiesIterations]!!)
                add(sliders[Constants.communitiesIterations]!!)
            }
            hbox(10) {
                add(texts[Constants.communitiesResolution]!!)
                add(sliders[Constants.communitiesResolution]!!)
            }

            button("Detect communities") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.showCommunities(
                        sliders[Constants.communitiesIterations]!!.value.toInt().toString(),
                        sliders[Constants.communitiesResolution]!!.value.toString()
                    )
                }
            }

            hbox(10) {
                add(texts[Constants.layoutIterations]!!)
                add(sliders[Constants.layoutIterations]!!)
            }
            hbox(10) {
                add(texts[Constants.layoutGravity]!!)
                add(sliders[Constants.layoutGravity]!!)
            }
            hbox(10) {
                add(texts[Constants.linLog]!!)
                add(checkboxes[Constants.linLog]!!)
            }
            hbox(10) {
                add(texts[Constants.outboundAttraction]!!)
                add(checkboxes[Constants.outboundAttraction]!!)
            }
            hbox(10) {
                add(texts[Constants.strongGravity]!!)
                add(checkboxes[Constants.strongGravity]!!)
            }

            button("Layout") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.forceLayout(
                        sliders[Constants.layoutIterations]!!.value.toInt().toString(),
                        sliders[Constants.layoutGravity]!!.value.toString(),
                        checkboxes[Constants.linLog]!!.isSelected,
                        checkboxes[Constants.outboundAttraction]!!.isSelected,
                        checkboxes[Constants.strongGravity]!!.isSelected
                    )
                }
            }
            hbox(10) {
                add(texts[Constants.srCoefficient]!!)
                add(sliders[Constants.srCoefficient]!!)
            }

            button("Highlight vertices") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.highlight(sliders[Constants.srCoefficient]!!.value)
                }
            }
        }
        left.visibleProperty().bind(props.GUI.leftMenu)

        bottom = graphInfo

        left.visibleProperty().bind(props.GUI.leftMenu)
        graphInfo.fillProperty().bind(props.GUI.darkThemeText)
        this.styleProperty().bind(props.GUI.darkTheme)
    }
}
