package controller.highlighter

import model.centrality.HarmonicCentrality
import tornadofx.Controller
import view.GraphView

class HighlighterStrategy : Controller(), HighlightVerticesStrategy {

    override fun highlight(graphView: GraphView, radius: Double) {
        val centrality = HarmonicCentrality(graphView, radius)
        if(!centrality.canSelect(graphView)) return
        centrality.selector(graphView, radius)
    }

}