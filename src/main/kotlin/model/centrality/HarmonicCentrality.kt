package model.centrality

import view.GraphView
import view.VertexView

import com.example.demo.logger.log
import model.Edge
import org.jgrapht.Graph
import org.jgrapht.alg.scoring.HarmonicCentrality
import org.jgrapht.graph.*
import view.props

class HarmonicCentrality(graphView: GraphView, radius: Double) {

    val vertices = graphView.vertices()
    val edges = graphView.edges()
    val graphVertices = mutableSetOf<VertexView>()
    val graph: Graph<String, DefaultEdge> = DefaultUndirectedGraph(DefaultEdge::class.java)
    val radius = props.vertex.defaultRadius.doubleValue()

    fun canSelect(graphView: GraphView): Boolean {
        if (graphView.vertices().isEmpty())
            log("Harmonic Centrality: graph is empty")
        else log("Key Vertices Selector started")
        return !graphView.vertices().isEmpty()
    }

    fun selector(graphView: GraphView, radius: Double) {

        val con = addGraph(graphView, radius)

        log("orange")

        val centrality = HarmonicCentrality(con)

        log("banana")

        graphView.vertices().onEach {
            it.radius = props.vertex.defaultRadius.value * (centrality.getScores()[it.vertex.element]!!)
            log(centrality.getScores()[it.vertex.element].toString())
        }

    }

    private fun addGraph(graphView: GraphView, radius: Double): SimpleGraph<String, DefaultEdge> {

        val g = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)

        log("Test is working")

        for (vertexView in vertices) {
            val i = vertexView.vertex.element.toString()
            g.addVertex(i)
            log(i)
        }

        edges.forEach {
            val n = it.first.vertex.element.toString()
            log(n)
            val m = it.second.vertex.element.toString()
            log(m)
            g.addEdge(n, m)
            log("$n, $m added")
        }

        return g

    }

}