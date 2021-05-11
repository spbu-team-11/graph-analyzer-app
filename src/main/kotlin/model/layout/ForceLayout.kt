package model.layout

import view.GraphView
import view.VertexView

import com.example.demo.logger.log
import javafx.geometry.Point2D
import kco.forceatlas2.ForceAtlas2
import kco.forceatlas2.ForceAtlas2Builder
import org.gephi.graph.api.Configuration
import org.gephi.graph.api.Edge
import org.gephi.graph.impl.EdgeImpl
import org.gephi.graph.impl.GraphModelImpl
import org.gephi.graph.impl.NodeImpl
import kotlin.math.abs

class ForceLayout<V, E> {

    fun canLayout(graphView: GraphView<V, E>): Boolean {
        if (graphView.vertices().isEmpty())
            log("Force Atlas 2: there is nothing to place")
        else log("Force Atlas 2 has started")
        return !graphView.vertices().isEmpty()
    }

    fun layout(
        graphView: GraphView<V, E>,
        nIterations: String,
        gravity: String?,
        width: Double,
        height: Double
    ) {
        val center = Point2D(width / 2, height / 2)

        val vertices = graphView.vertices()
        val edges = graphView.edges()

        val countOfIterations = initIterations(nIterations)
        val forcePlacement = ForceAtlas2(ForceAtlas2Builder(), false, false)
        val graphModel = GraphModelImpl(Configuration())
        forcePlacement.setGraphModel(graphModel)
        forcePlacement.gravity = initGravity(gravity)
        if (vertices.size > 2000) forcePlacement.isBarnesHutOptimize = true

        val graphVertices = mutableSetOf<VertexView<V>>()
        val allNodes = hashMapOf<V, NodeImpl>()
        val allEdges = mutableSetOf<Edge>()

        for (i in vertices) {
            val node = NodeImpl(i.vertex.element)
            node.setX(i.centerX.toFloat() - center.x.toFloat())
            node.setY(i.centerY.toFloat() - center.y.toFloat())

            if (!allNodes.containsKey(i.vertex.element)) allNodes[i.vertex.element] = node
            if (!graphVertices.contains(i)) graphVertices += i
        }

        for (i in edges) {
            val edge = EdgeImpl(
                i.label,
                allNodes[i.first.vertex.element],
                allNodes[i.second.vertex.element],
                1, 1.0, false
            )
            allEdges += edge
        }

        for (i in allNodes) {
            graphModel.graph.addNode(i.value)
        }
        graphModel.graph.addAllEdges(allEdges)

        forcePlacement.runAlgo(countOfIterations)

        graphModel.translateToGraphView(graphVertices, center)
    }

    private fun initIterations(nIterations: String) = nIterations.toInt()

    private fun initGravity(gravity: String?) = gravity?.toDoubleOrNull() ?: 1.0

    private fun ForceAtlas2.runAlgo(countOfIterations: Int) {
        initAlgo()
        var i = 0
        while (i < countOfIterations) {
            goAlgo()
            i++
            log("Force Atlas 2: $i iteration" + (if (i > 1) "s" else "") + " behind")
        }

        endAlgo()
        log("Force Atlas 2 has finished")
    }

    private fun GraphModelImpl.translateToGraphView(
        graphVertices: MutableCollection<VertexView<V>>,
        center: Point2D
    ) {
        val nodes = store.nodes.toArray()
        val max = nodes.maxOf { abs(it.x()) / 2 } to nodes.maxOf { abs(it.y()) / 2 }
        val coefficient =
            (if (max.first > center.x) max.first / center.x * 2.25 else 1.0) to (if (max.second > center.y) max.second / center.y * 2.25 else 1.0)
        val maxCoefficient = maxOf(coefficient.first, coefficient.second)

        var i = 0
        graphVertices.onEach {
            it.position =
                nodes[i].x() / maxCoefficient + center.x to nodes[i].y() / maxCoefficient + center.y
            i++
        }
    }
}