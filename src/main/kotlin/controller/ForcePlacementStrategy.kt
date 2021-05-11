package controller

import com.example.demo.logger.log
import javafx.geometry.Point2D
import kco.forceatlas2.ForceAtlas2
import kco.forceatlas2.ForceAtlas2Builder
import org.gephi.graph.api.Configuration
import org.gephi.graph.api.Edge
import org.gephi.graph.api.Node
import org.gephi.graph.impl.EdgeImpl
import org.gephi.graph.impl.GraphModelImpl
import org.gephi.graph.impl.NodeImpl
import tornadofx.Controller
import view.GraphView
import view.VertexView

class ForcePlacementStrategy : Controller(), ForceRepresentationStrategy {
    override fun <V, E> place(
        graphView: GraphView<V, E>,
        nIterations: String,
        gravity: String?,
        width: Double,
        height: Double
    ) {
        if (graphView.vertices().isEmpty()) {
            log("Force Atlas 2: there is nothing to place")
        }
        log("Force Atlas 2 has started")
        val center = Point2D(width / 2, height / 2)

        val vertices = graphView.vertices()
        val edges = graphView.edges()
        val forcePlacement = ForceAtlas2(ForceAtlas2Builder(), false, false)
        val graphModel = GraphModelImpl(Configuration())
        forcePlacement.setGraphModel(graphModel)
        forcePlacement.gravity = gravity?.toDoubleOrNull()?: 1.0

        val graphVertices = mutableSetOf<VertexView<V>>()
        val allNodes = hashMapOf<V, NodeImpl>()
        val allEdges = mutableSetOf<Edge>()

        for(i in vertices) {
            val node = NodeImpl(i.vertex.element)
            node.setX(i.centerX.toFloat())
            node.setY(i.centerY.toFloat())

            if(!allNodes.containsKey(i.vertex.element)) allNodes[i.vertex.element] = node
            if(!graphVertices.contains(i)) graphVertices += i
        }

        for(i in edges) {
            val edge = EdgeImpl(
                i.label,
                allNodes[i.first.vertex.element],
                allNodes[i.second.vertex.element],
                1, 1.0, false)
            allEdges += edge
        }

        for (i in allNodes) {
            graphModel.graph.addNode(i.value)
        }

        graphModel.graph.addAllEdges(allEdges)

        forcePlacement.initAlgo()
        var i = 0
        while(i < nIterations.toInt()) {
            forcePlacement.goAlgo()
            i++
        }
        forcePlacement.endAlgo()

        val nodes = graphModel.store.nodes.toArray()
        var theirCenter = 0.0 to 0.0
        nodes.onEach {
            theirCenter = theirCenter.first - center.x + it.x().toDouble() to theirCenter.second - center.y + it.y().toDouble()
        }
        theirCenter = (theirCenter.first) / nodes.size / 2 to (theirCenter.second) / nodes.size / 2
        i = 0
        graphVertices.onEach {
            it.position = nodes[i].x().toDouble() - theirCenter.first to nodes[i].y().toDouble() - theirCenter.second
            i++
        }
    }
}