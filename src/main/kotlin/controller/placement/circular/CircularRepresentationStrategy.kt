package controller.placement.circular

import view.VertexView

interface CircularRepresentationStrategy {

    fun <V> place(width: Double, height: Double, vertices: Collection<VertexView<V>>)
}
