package controller.highlighter

import model.centrality.HarmonicCentrality
import view.GraphView

import tornadofx.Controller

class HighlighterStrategy : Controller(), HighlightVerticesStrategy {

    override fun highlight(graphView: GraphView, value: Double) {
        val centrality = HarmonicCentrality(graphView)
        if (!centrality.canSelect(graphView)) return
        centrality.selector(graphView, value)
    }
}