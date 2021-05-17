package view.MainView

import tornadofx.add
import tornadofx.center
import view.GraphView
import view.props

@ExperimentalStdlibApi
class GraphDrawer(private val mainView: MainView) {
    internal fun arrangeVertices() {
        mainView.currentStage?.apply {
            mainView.circularPlacementStrategy.place(
                width - props.vertex.radius.get() * 10,
                height - props.vertex.radius.get() * 10,
                mainView.graphView.vertices(),
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
        mainView.currentStage?.apply {
            mainView.forcePlacementStrategy.place(
                mainView.graphView,
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
        mainView.currentStage?.apply {
            mainView.highlightVerticesStrategy.highlight(mainView.graphView, value)
        }
    }

    internal fun showCommunities(nIteration: String, resolution: String) {
        mainView.currentStage?.apply {
            mainView.paintingStrategy.showCommunities(mainView.graph, mainView.graphView, nIteration, resolution)
            updateGraphInfo()
        }
    }

    internal fun updateGraphInfo() {
        var maxCommunity = -1
        mainView.graph.vertices().forEach {
            if (it.community > maxCommunity) maxCommunity = it.community
        }
        mainView.graphInfo.text = " Vertices: ${mainView.graph.vertices().size} \n Edges: ${mainView.graph.edges().size} " +
                "\n Communities: ${if (maxCommunity == -1) "-" else (maxCommunity + 1)}"
    }

    internal fun showGraphWithGraphView() {
        mainView.graphView.edges()
            .onEach {
                if (it.first.color == it.second.color)
                    it.stroke = it.first.color
            }
        mainView.root.center {
            add(mainView.graphView)
        }
        updateGraphInfo()
    }

    internal fun showGraphWithoutGraphView() {
        mainView.graphView = GraphView(mainView.graph)
        mainView.root.center {
            add(mainView.graphView)
        }
        arrangeVertices()
        updateGraphInfo()
    }
}