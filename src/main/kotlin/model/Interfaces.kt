package model

interface Vertex {
    var community: Int
    var element: String
}

interface Edge {
    var element: String
    val vertices: Pair<Vertex, Vertex>
}

interface Graph {
    fun vertices(): Collection<Vertex>
    fun edges(): Collection<Edge>

    fun addVertex(v: String): Vertex
    fun addEdge(u: String, v: String, e: String): Edge
}
