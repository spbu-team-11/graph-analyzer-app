package controller

import tornadofx.Controller
import view.VertexView

class ForcePlacementStrategy : Controller(), RepresentationStrategy {

    override fun <V> place(width: Double, height: Double, vertices: Collection<VertexView<V>>) {
        TODO("Not yet implemented")
    }
}