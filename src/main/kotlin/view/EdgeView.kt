package view

import model.Edge

import javafx.scene.shape.Line
import tornadofx.text

class EdgeView(

    edge: Edge,
    val first: VertexView,
    val second: VertexView,
) : Line() {

    init {
        startXProperty().bind(first.centerXProperty())
        startYProperty().bind(first.centerYProperty())
        endXProperty().bind(second.centerXProperty())
        endYProperty().bind(second.centerYProperty())
    }

    val label = text(edge.element.toString()) {
        visibleProperty().bind(props.edge.label)
        xProperty().bind(
            startXProperty().add(endXProperty()).divide(2).subtract(layoutBounds.width / 2)
        )
        yProperty().bind(
            startYProperty().add(endYProperty()).divide(2).add(layoutBounds.height / 1.5)
        )
    }
}
