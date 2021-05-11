package controller

import model.UndirectedGraph
import view.GraphView

interface ForceRepresentationStrategy {

    fun <V, E> place(graphView: GraphView<V, E>, nIterations: String, gravity: String?, width: Double, height: Double)
}