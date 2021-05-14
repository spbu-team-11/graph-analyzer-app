package controller.highlighter

import view.GraphView

interface HighlightVerticesStrategy {

    fun highlight(graphView: GraphView, value: Double)

}