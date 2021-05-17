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
import view.GraphView
import view.props

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
                add(texts[0])
                add(sliders[0])
            }
            hbox(10) {
                add(texts[1])
                add(sliders[1])
            }

            button("Detect communities") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.showCommunities(sliders[0].value.toInt().toString(), sliders[1].value.toString())
                }
            }

            hbox(10) {
                add(texts[2])
                add(sliders[2])
            }
            hbox(10) {
                add(texts[3])
                add(sliders[3])
            }
            hbox(10) {
                add(texts[4])
                add(checkboxes[0])
            }
            hbox(10) {
                add(texts[5])
                add(checkboxes[1])
            }
            hbox(10) {
                add(texts[6])
                add(checkboxes[2])
            }

            button("Layout") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.forceLayout(
                        sliders[2].value.toInt().toString(),
                        sliders[3].value.toString(),
                        checkboxes[0].isSelected,
                        checkboxes[1].isSelected,
                        checkboxes[2].isSelected
                    )
                }
            }
            hbox(10) {
                add(texts[7])
                add(sliders[4])
            }

            button("Highlight vertices") {
                minWidth = defaultMinWidthLeft
                action {
                    drawer.highlight(sliders[4].value)
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
