package model.community

import CommunityLogger
import model.UndirectedGraph
import model.Vertex
import utils.Alerter

import nl.cwts.networkanalysis.Clustering
import nl.cwts.networkanalysis.LeidenAlgorithm
import nl.cwts.networkanalysis.Network
import java.util.*
import kotlin.collections.HashMap

class CommunitiesFinder {

    private val logger = CommunityLogger(javaClass)

    fun findCommunity(graph: UndirectedGraph, nIterations: String, resolution: String): Boolean {
        val doubleResolution = resolution.toDoubleOrNull()
        val intNIterations = nIterations.toIntOrNull()
        if (doubleResolution == null || intNIterations == null || graph.vertices().isEmpty()) {
            logger.logCantFind()
            return false
        }
        logger.logStart()
        val network = graph.toNetwork()

        val algorithm = LeidenAlgorithm(doubleResolution, intNIterations, LeidenAlgorithm.DEFAULT_RANDOMNESS, Random())
        logger.logInitialisation()
        val ans = algorithm.findClustering(network)

        graph.setCommunity(ans)
        logger.logFinish()
        return true
    }

    private fun UndirectedGraph.toNetwork(): Network {

        val dict = makeDict()

        val edges = Array(2) { IntArray(edges().size) { 0 } }
        for ((i, edge) in edges().withIndex()) {
            edges[0][i] = dict[edge.vertices.first]!!
            edges[1][i] = dict[edge.vertices.second]!!
        }

        return Network(vertices().size, false, edges, false, false)
    }

    private fun UndirectedGraph.makeDict(): HashMap<Vertex, Int> {
        val dict = hashMapOf<Vertex, Int>()
        for ((i, vertex) in vertices().withIndex()) {
            dict[vertex] = i
        }

        return dict
    }

    private fun UndirectedGraph.setCommunity(clustering: Clustering) {
        for ((i, vertex) in vertices().withIndex()) {
            vertex.community = clustering.clusters[i]
        }
    }
}