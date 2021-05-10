package model.finders

import com.example.demo.logger.log
import model.UndirectedGraph
import model.Vertex
import nl.cwts.networkanalysis.Clustering
import nl.cwts.networkanalysis.LeidenAlgorithm
import nl.cwts.networkanalysis.Network
import utils.Alerter
import java.util.*
import kotlin.collections.HashMap


class CommunitiesFinder<V, E> {

    fun findCommunity(graph: UndirectedGraph<String, Long>, nIterations: String, resolution: String): Boolean {
        val doubleResolution = resolution.toDoubleOrNull()
        val intNIterations = nIterations.toIntOrNull()
        if (doubleResolution == null || intNIterations == null) {
            Alerter().alertIncorrectArgs("Incorrect arguments for find community")
            return false
        }
        log("community finding started $doubleResolution, $intNIterations")
        val network = graph.toNetwork()

        val algorithm = LeidenAlgorithm(doubleResolution, intNIterations, LeidenAlgorithm.DEFAULT_RANDOMNESS, Random())
        val ans = algorithm.findClustering(network)

        graph.setCommunity(ans)
        log("community finding finished...")
        return true
    }

    private fun <V, E> UndirectedGraph<V, E>.toNetwork(): Network { // Затычка

        val dict = makeDict()

        val edges = Array(2) { IntArray(edges().size) { 0 } }
        for ((i, edge) in edges().withIndex()) {
            edges[0][i] = dict[edge.vertices.first]!!
            edges[1][i] = dict[edge.vertices.second]!!
        }

        return Network(vertices().size, false, edges, false, true)
    }

    private fun <V, E> UndirectedGraph<V, E>.makeDict(): HashMap<Vertex<V>, Int> {
        val dict = hashMapOf<Vertex<V>, Int>()
        for ((i, vertex) in vertices().withIndex()) {
            dict[vertex] = i
        }

        return dict
    }

    private fun <V, E> UndirectedGraph<V, E>.setCommunity(clustering: Clustering) {
        for ((i, vertex) in vertices().withIndex()) {
            vertex.community = clustering.clusters[i]
        }
    }
}