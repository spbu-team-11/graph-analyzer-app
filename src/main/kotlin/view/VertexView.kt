package view

import model.Vertex

import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.text

class VertexView(

    val vertex: Vertex,
    x: Double,
    y: Double,
    radius: Double,
    color: Color,
) : Circle(x, y, radius, color) {

    var position: Pair<Double, Double>
        get() = centerX to centerY
        set(value) {
            centerX = value.first
            centerY = value.second
        }

    var vertexRadius: Double
        get() = radius
        set(value) {
            radius = value
        }

    var color: Color
        get() = fill as Color
        set(value) {
            fill = value
        }

    val label = text(vertex.element.toString()) {
        visibleProperty().bind(props.vertex.label)
        xProperty().bind(centerXProperty().subtract(layoutBounds.width / 2))
        yProperty().bind(centerYProperty().add(radiusProperty()).add(layoutBounds.height))
    }

    var community = text {
        visibleProperty().bind(props.vertex.community)
        xProperty().bind(centerXProperty().subtract(layoutBounds.width / 2 + 15))
        yProperty().bind(centerYProperty().add(radiusProperty()).add(layoutBounds.height + 15))
    }
}
