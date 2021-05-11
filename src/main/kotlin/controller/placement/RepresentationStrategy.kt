package controller.placement

import view.VertexView

interface RepresentationStrategy {

    fun <V> place(width: Double, height: Double, vertices: Collection<VertexView<V>>)
}
