package controller.highlighter

import model.centrality.HarmonicCentrality
import tornadofx.Controller
import view.GraphView

class HighlighterStrategy : Controller(), HighlightVerticesStrategy {

    override fun highlight(graphView: GraphView) {
        val centrality = HarmonicCentrality(graphView)
        if(!centrality.canSelect(graphView)) return
        centrality.selector(graphView)
    }

}