package view

import controller.VertexDragController
import model.Graph
import model.UndirectedGraph

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.add
import tornadofx.find

class GraphView(private val graph: Graph = UndirectedGraph()) : Pane() {

    private val dragger = find(VertexDragController::class)

    private val vertices by lazy {
        graph.vertices().associateWith {
            VertexView(it, 0.0, 0.0, props.vertex.radius.value, Color.BLACK)
        }
    }

    private val edges by lazy {
        graph.edges().associateWith {
            val first = vertices.getValue(it.vertices.first)
            val second = vertices.getValue(it.vertices.second)
            EdgeView(it, first, second)
        }
    }

    fun vertices(): Collection<VertexView> = vertices.values

    fun edges(): Collection<EdgeView> = edges.values

    init {
        vertices().forEach { v ->
            add(v)
            add(v.label)
            add(v.community)
            v.setOnMouseEntered { e -> e?.let { dragger.entered(it) } }
            v.setOnMousePressed { e -> e?.let { dragger.pressed(it) } }
            v.setOnMouseDragged { e -> e?.let { dragger.dragged(it) } }
            v.setOnMouseReleased { e -> e?.let { dragger.released(it) } }
            v.setOnMouseExited { e -> e?.let { dragger.exited(it) } }
        }

        edges().forEach {
            add(it)
            add(it.label)
        }
    }
}
