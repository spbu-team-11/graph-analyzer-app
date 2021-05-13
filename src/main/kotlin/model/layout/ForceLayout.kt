package model.layout

import view.GraphView
import view.VertexView
import view.EdgeView

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

class ForceLayout {

    fun canLayout(graphView: GraphView): Boolean {
        if (graphView.vertices().isEmpty())
            log("Force Atlas 2: there is nothing to place")
        else log("Force Atlas 2 has started")
        return !graphView.vertices().isEmpty()
    }

    fun layout(
        graphView: GraphView,
        nIterations: String,
        gravity: String?,
        isLinLogMode: Boolean,
        width: Double,
        height: Double
    ) {
        val center = Point2D(width / 2, height / 2)

        val vertices = graphView.vertices()
        val edges = graphView.edges()

        val forcePlacement = ForceAtlas2(ForceAtlas2Builder(), false, false)
        val graphModel = GraphModelImpl(Configuration())
        forcePlacement.initLayout(graphModel, initGravity(gravity), isLinLogMode, vertices.size)

        val graphVertices = mutableSetOf<VertexView>()

        graphModel.translateFromGraphView(graphVertices, vertices, edges, center)

        forcePlacement.runAlgo(initIterations(nIterations))

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

    private fun ForceAtlas2.initLayout(
        graphModel: GraphModelImpl,
        gravity: Double,
        isLinLogMode: Boolean,
        countOfVertices: Int
    ) {
        setGraphModel(graphModel)
        this.gravity = gravity
        this.isLinLogMode = isLinLogMode
        if (countOfVertices >= 1000) isBarnesHutOptimize = true
    }

    private fun GraphModelImpl.translateToGraphView(graphVertices: MutableCollection<VertexView>, center: Point2D) {
        val nodes = graph.nodes.toArray()

        var maxX = 0.0
        nodes.onEach {
            maxX = maxOf(maxX, abs(it.x().toDouble()))
        }
        var maxY = 0.0
        nodes.onEach {
            maxY = maxOf(maxY, abs(it.y().toDouble()))
        }

        val max = maxX to maxY
        log("$max $center")
        val coefficient = maxOf(calcCoefficient(max.first.toFloat(), center.x), calcCoefficient(max.second.toFloat(), center.y))

        var i = 0
        graphVertices.onEach {
            it.position =
                nodes[i].x() / coefficient + center.x to nodes[i].y() / coefficient + center.y
            i++
        }
    }

    private fun GraphModelImpl.translateFromGraphView(
        graphVertices: MutableCollection<VertexView>,
        vertices: Collection<VertexView>,
        edges: Collection<EdgeView>,
        center: Point2D
    ) {
        val allNodes = hashMapOf<String, NodeImpl>()
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
            graph.addNode(i.value)
        }
        graph.addAllEdges(allEdges)
    }

    private fun calcCoefficient(first: Float, second: Double) =
        if (first > second) first / second * 1.05 else 1.0
}