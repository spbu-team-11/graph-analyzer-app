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
        width: Double,
        height: Double
    ) {
        if (graphView.vertices().isEmpty()) {
            log("Force Atlas 2: there is nothing to place")
        }
        log("Force Atlas 2 has started")
        val center = Point2D(width / 2, height / 2)
        log(center.toString())

        val edges = graphView.edges()
        val forcePlacement = ForceAtlas2(ForceAtlas2Builder(), false, false)
        val graphModel = GraphModelImpl(Configuration())
        forcePlacement.setGraphModel(graphModel)
        forcePlacement.gravity = 10.0

        val graphVertices = mutableSetOf<VertexView<V>>()
        val allNodes = hashMapOf<V, NodeImpl>()
        val allEdges = mutableSetOf<Edge>()

        for(i in edges) {
            var from = NodeImpl(i.first.vertex.element)
            from.setX((i.first.position.first - center.x).toFloat())
            from.setY((i.first.position.second - center.y).toFloat())

            var to = NodeImpl(i.second.vertex.element)
            to.setX((i.second.position.first - center.x).toFloat())
            to.setY((i.second.position.second - center.y).toFloat())

            if(allNodes.containsKey(i.first.vertex.element)) from = allNodes[i.first.vertex.element]!!
            else allNodes[i.first.vertex.element] = from
            if(allNodes.containsKey(i.second.vertex.element)) to = allNodes[i.second.vertex.element]!!
            else allNodes[i.second.vertex.element] = to
//            allNodes += arrayOf(from, to)
            if(!graphVertices.contains(i.first)) graphVertices += i.first
            if(!graphVertices.contains(i.second)) graphVertices += i.second
//            graphVertices += arrayOf(i.first, i.second)

            val edge = EdgeImpl(i.label, from, to, 1, 1.0, false)
            log("\n" + edge.source.toString() + edge.target.toString())
            allEdges += edge
        }

        log(allNodes.isEmpty().toString())
        log(allEdges.isEmpty().toString())

        for (i in allNodes) {
            graphModel.graph.addNode(i.value)
        }

        graphModel.graph.addAllEdges(allEdges)

        graphVertices.onEach {
            log(it.position.toString())
        }

        forcePlacement.initAlgo()
        var i = 0
        while(i < nIterations.toInt()) {
            forcePlacement.goAlgo()
            i++
        }
        forcePlacement.endAlgo()

        val nodes = graphModel.graph.nodes.toArray()
        i = 0
        graphVertices.onEach {
            it.position = nodes[i].x().toDouble() + center.x to nodes[i].y().toDouble() + center.y
            log(it.position.toString())
            i++
        }
    }
}