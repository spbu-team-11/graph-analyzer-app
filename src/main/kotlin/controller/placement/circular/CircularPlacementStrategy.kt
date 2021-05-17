package controller.placement.circular

import view.VertexView

import javafx.geometry.Point2D
import javafx.scene.paint.Color
import model.layout.CircularLayout
import tornadofx.Controller
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularPlacementStrategy : Controller(), CircularRepresentationStrategy {

    override fun place(width: Double, height: Double, vertices: Collection<VertexView>) {
        val circularPlacement = CircularLayout()
        if(!circularPlacement.canLayout(vertices)) return

        circularPlacement.layout(width, height, vertices)
    }
}
