package controller.placement.circular

import view.VertexView

import model.layout.CircularLayout
import tornadofx.Controller

class CircularPlacementStrategy : Controller(), CircularRepresentationStrategy {

    override fun place(width: Double, height: Double, vertices: Collection<VertexView>) {
        val circularPlacement = CircularLayout()
        if (!circularPlacement.canLayout(vertices)) return

        circularPlacement.layout(width, height, vertices)
    }
}
