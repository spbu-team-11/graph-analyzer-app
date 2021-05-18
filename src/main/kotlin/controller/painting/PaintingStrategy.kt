package controller.painting

import model.UndirectedGraph
import view.GraphView

interface PaintingStrategy {

    fun showCommunities(
        graph: UndirectedGraph,
        graphView: GraphView,
        nIteration: String,
        resolution: String
    )
}