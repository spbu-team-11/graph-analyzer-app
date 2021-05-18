package model.centrality

import CentralityLogger
import view.props
import view.GraphView

import org.jgrapht.alg.scoring.HarmonicCentrality
import org.jgrapht.graph.*
import kotlin.math.pow

class HarmonicCentrality(graphView: GraphView) {

    private val logger = CentralityLogger(javaClass)

    val vertices = graphView.vertices()
    val edges = graphView.edges()

    fun canSelect(graphView: GraphView): Boolean {

        if (graphView.vertices().isEmpty())
            logger.logEmptyGraph()
        else logger.logStart()

        return !graphView.vertices().isEmpty()
    }

    fun selector(graphView: GraphView, value: Double) {

        val graph = addGraph()
        val centrality = HarmonicCentrality(graph)
        logger.logInitialisation()

        graphView.vertices().onEach {
            val cent = (centrality.scores[it.vertex.element]!!)
            it.radius =
                props.vertex.radius.value * ((2.7 + value).pow(cent) - (2.7 + value / 2).pow(cent)) / (value / 2) * 5
        }
        logger.logFinish()
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