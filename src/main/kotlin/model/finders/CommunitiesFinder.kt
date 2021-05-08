package model.finders

import com.example.demo.logger.log
import model.UndirectedGraph
import model.Vertex
import javafx.scene.control.Alert
import nl.cwts.networkanalysis.Clustering
import nl.cwts.networkanalysis.LeidenAlgorithm
import nl.cwts.networkanalysis.Network
import tornadofx.alert
import java.util.*
import kotlin.collections.HashMap


class CommunitiesFinder<V, E> {
    fun findCommunity(graph: UndirectedGraph<V, E>,nIterations: String, resolution: String): Boolean{
        val doubleResolution = resolution.toDoubleOrNull()
        val intNIterations = nIterations.toIntOrNull()
        if(doubleResolution == null || intNIterations == null){
            alert(Alert.AlertType.ERROR, "Конченный?")
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

    private fun<V, E> UndirectedGraph<V, E>.toNetwork(): Network { // Затычка
        val size = vertices().size

        val dict = makeDict()

        val edges= Array(2) { IntArray(size) { 0 } }
        for((i,edge) in edges().withIndex()){
            edges[0][i] = dict[edge.vertices.first]!!
            edges[1][i] = dict[edge.vertices.second]!!
        }

        return Network(size, false, edges, false, true)
    }

    private fun<V, E> UndirectedGraph<V, E>.makeDict(): HashMap<Vertex<V>, Int> {
        val dict = hashMapOf<Vertex<V>, Int>()
        for((i, vertex) in vertices().withIndex()){
            dict[vertex] = i
        }

        return dict
    }

    private fun<V, E> UndirectedGraph<V, E>.setCommunity(clustering: Clustering){
        for((i,vertex) in vertices().withIndex()){
            vertex.community = clustering.clusters[i]
        }
    }
}