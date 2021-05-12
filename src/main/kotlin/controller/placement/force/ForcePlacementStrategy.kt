package controller.placement.force

import model.layout.ForceLayout
import view.GraphView

import tornadofx.Controller

class ForcePlacementStrategy : Controller(), ForceRepresentationStrategy {

    override fun <V, E> place(
        graphView: GraphView<V, E>,
        nIterations: String,
        gravity: String?,
        isLinLogMode: Boolean,
        width: Double,
        height: Double
    ) {
        val forcePlacement = ForceLayout<V, E>()
        if(!forcePlacement.canLayout(graphView)) return
        forcePlacement.layout(graphView, nIterations, gravity, isLinLogMode, width, height)
    }
}