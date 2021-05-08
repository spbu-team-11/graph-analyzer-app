package controller


import view.GraphView
import view.VertexView

interface RepresentationStrategy {
    fun <V> place(width: Double, height: Double, vertices: Collection<VertexView<V>>)

    fun <V, E> showCommunities(graph: GraphView<String, Long>, nIteration: String, resolution: String)
}
