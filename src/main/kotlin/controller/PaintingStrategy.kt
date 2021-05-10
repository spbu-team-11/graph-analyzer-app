package controller

import model.UndirectedGraph
import view.GraphView

interface PaintingStrategy {

    fun <V, E> showCommunities(
        graph: UndirectedGraph<String, Long>,
        graphView: GraphView<String, Long>,
        nIteration: String,
        resolution: String
    )
}