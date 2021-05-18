package model

class UndirectedGraph : Graph {

    private val vertices = hashMapOf<String, Vertex>()
    private val edges = hashMapOf<String, Edge>()

    override fun vertices(): Collection<Vertex> = vertices.values

    override fun edges(): Collection<Edge> = edges.values

    override fun addVertex(v: String): Vertex = vertices.getOrPut(v) { UndirectedVertex(v, -1) }

    override fun addEdge(u: String, v: String, e: String): Edge {
        val first = addVertex(u)
        val second = addVertex(v)
        return edges.getOrPut(e) { UndirectedEdge(e, first, second) }
    }

    private data class UndirectedVertex(override var element: String, override var community: Int) : Vertex

    private data class UndirectedEdge(
        override var element: String,
        var first: Vertex,
        var second: Vertex,
    ) : Edge {
        override val vertices
            get() = first to second
    }
}
