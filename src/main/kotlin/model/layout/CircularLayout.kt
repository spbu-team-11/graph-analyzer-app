package model.layout

import view.VertexView

import javafx.geometry.Point2D
import javafx.scene.paint.Color
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularLayout {

    fun canLayout(vertices: Collection<VertexView>) = !vertices.isEmpty()

    fun layout(width: Double, height: Double, vertices: Collection<VertexView>) {
        val center = Point2D(width / 2, height / 2)
        val angle = -360.0 / vertices.size

        val sortedVertices = vertices.sortedBy { it.vertex.element.toLowerCase() }
        var point = Point2D(center.x, center.y - min(width, height) / 2 + sortedVertices[0].radius * 2)

        sortedVertices.onEach {
            it.position = point.x to point.y
            it.color = Color.BLACK
            point = point.rotate(center, angle)
        }
    }

    private fun Point2D.rotate(pivot: Point2D, degrees: Double): Point2D {
        val angle = Math.toRadians(degrees)
        val sin = sin(angle)
        val cos = cos(angle)

        val diff = subtract(pivot)
        val rotated = Point2D(
            diff.x * cos - diff.y * sin,
            diff.x * sin + diff.y * cos,
        )
        return rotated.add(pivot)
    }
}