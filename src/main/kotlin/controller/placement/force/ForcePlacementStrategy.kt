package controller.placement.force

import model.layout.ForceLayout
import view.GraphView

import tornadofx.Controller

class ForcePlacementStrategy : Controller(), ForceRepresentationStrategy {

    override fun place(
        graphView: GraphView,
        nIterations: String,
        gravity: String?,
        isLinLogMode: Boolean,
        isOutboundAttraction: Boolean,
        isStrongGravity: Boolean,
        width: Double,
        height: Double
    ) {
        val forcePlacement = ForceLayout()
        if (!forcePlacement.canLayout(graphView)) return
        forcePlacement.layout(
            graphView,
            nIterations,
            gravity,
            isLinLogMode,
            isOutboundAttraction,
            isStrongGravity,
            width,
            height
        )
    }
}