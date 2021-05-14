package model.centrality

import view.GraphView

import com.example.demo.logger.log
import org.jgrapht.alg.scoring.HarmonicCentrality
import org.jgrapht.graph.*
import view.props
import kotlin.math.pow

class HarmonicCentrality(graphView: GraphView) {

    val vertices = graphView.vertices()
    val edges = graphView.edges()

    fun canSelect(graphView: GraphView): Boolean {

        if (graphView.vertices().isEmpty())
            log("Harmonic Centrality: graph is empty")
        else log("Key Vertices Selector started")

        return !graphView.vertices().isEmpty()
    }

    fun selector(graphView: GraphView) {

        val graph = addGraph()
        val centrality = HarmonicCentrality(graph)

        graphView.vertices().onEach {
            val cent = (centrality.scores[it.vertex.element]!!)
            it.radius = props.vertex.radius.value * ( (2.7 + value).pow(cent) - (2.7 + value / 2).pow(cent) ) / (value / 2) * 5
        }
    }

    private fun addGraph(): SimpleGraph<String, DefaultEdge> {

        val graph = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)

        for (vertexView in vertices) {
            val i = vertexView.vertex.element
            graph.addVertex(i)
        }

        edges.forEach {
            val n = it.first.vertex.element
            val m = it.second.vertex.element
            graph.addEdge(n, m)
        }

        return graph
    }
}