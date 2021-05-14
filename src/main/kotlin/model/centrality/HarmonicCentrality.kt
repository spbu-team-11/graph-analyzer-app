package model.centrality

import view.GraphView
import view.VertexView

import com.example.demo.logger.log
import model.Edge
import org.jgrapht.Graph
import org.jgrapht.alg.scoring.HarmonicCentrality
import org.jgrapht.graph.*
import view.props
import kotlin.math.pow

class HarmonicCentrality(graphView: GraphView, value: Double) {

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

    fun selector(graphView: GraphView, value: Double) {

        val con = addGraph(graphView, radius)

        log("orange")

        val centrality = HarmonicCentrality(con)

        log("banana")

        graphView.vertices().onEach {
            val cent = (centrality.getScores()[it.vertex.element]!!)
            it.radius = props.vertex.defaultRadius.value * ( (2.7 + value).pow(cent) - (2.7 + value / 2).pow(cent) ) / (value / 2) * 5
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