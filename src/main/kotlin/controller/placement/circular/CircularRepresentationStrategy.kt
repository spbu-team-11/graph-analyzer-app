package controller.placement.circular

import view.VertexView

interface CircularRepresentationStrategy {

    fun place(width: Double, height: Double, vertices: Collection<VertexView>)
}
