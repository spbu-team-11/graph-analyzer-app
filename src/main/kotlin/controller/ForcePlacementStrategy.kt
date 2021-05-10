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

class ForcePlacementStrategy : Controller(), ForceRepresentationStrategy {
    override fun <V, E> place(
        graphView: GraphView<V, E>,
        nIterations: String,
        width: Double,
        height: Double
    ) {
        if (graphView.edges().isEmpty()) {
            log("Force Atlas 2: there is nothing to place")
        }

        val center = Point2D(width / 2, height / 2)

        val edges = graphView.edges()
        val forcePlacement = ForceAtlas2(ForceAtlas2Builder(), false, false)
        val graphModel = GraphModelImpl(Configuration())
        forcePlacement.setGraphModel(graphModel)

        val allNodes = setOf<Node>()
        val allEdges = setOf<Edge>()

        for(i in edges) {
            val from = NodeImpl(i.first.label.toString())
            from.setX(i.first.position.first.toFloat())
            from.setY(i.first.position.second.toFloat())

            val to = NodeImpl(i.second.label.toString())
            to.setX(i.second.position.first.toFloat())
            to.setY(i.second.position.second.toFloat())

            allNodes.plus(arrayOf(from, to))

            val edge = EdgeImpl(i.label, from, to, 1, 1.0, false)
            allEdges.plus(edge)
        }

        graphModel.graph.addAllNodes(allNodes)
        graphModel.graph.addAllEdges(allEdges)

        forcePlacement.initAlgo()
        var i = 0
        while(i < nIterations.toInt()) {
            forcePlacement.goAlgo()
            i++
        }
        forcePlacement.endAlgo()


    }
}