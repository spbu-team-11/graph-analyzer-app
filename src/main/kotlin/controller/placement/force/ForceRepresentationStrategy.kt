package controller.placement.force

import view.GraphView

interface ForceRepresentationStrategy {

    fun place(
        graphView: GraphView,
        nIterations: String,
        gravity: String?,
        isLinLogMode: Boolean,
        isOutboundAttraction: Boolean,
        isStrongGravity: Boolean,
        width: Double,
        height: Double
    )
}