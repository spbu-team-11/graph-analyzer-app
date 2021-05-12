package controller.placement.force

import view.GraphView

interface ForceRepresentationStrategy {

    fun <V, E> place(
        graphView: GraphView<V, E>,
        nIterations: String,
        gravity: String?,
        isLinLogMode: Boolean,
        width: Double,
        height: Double
    )
}