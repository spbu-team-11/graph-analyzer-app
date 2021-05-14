package controller.painting

import view.GraphView
import model.UndirectedGraph
import model.community.CommunitiesFinder

import com.example.demo.logger.log
import javafx.scene.paint.Color
import tornadofx.Controller
import kotlin.random.Random

class PaintingByCommunitiesStrategy : Controller(), PaintingStrategy {

    override fun showCommunities(
        graph: UndirectedGraph,
        graphView: GraphView,
        nIteration: String,
        resolution: String
    ) {
        log("community finding starting...")

        val finder = CommunitiesFinder()
        val returnCode = finder.findCommunity(graph, nIteration, resolution)
        if (!returnCode) return
        graphView.vertices()
            .onEach {
                val com = it.vertex.community
                it.community.text = com.toString()
                it.color = generateRandomColor(com * 100)
            }
        graphView.edges()
            .onEach {
                if(it.first.color == it.second.color)
                    it.stroke = it.first.color
            }
    }

    private fun generateRandomColor(base: Int): Color {
        // This is the base color which will be mixed with the generated one
        val mRandom = Random(base);
        val red: Int = (base + mRandom.nextInt(256)) / 2
        val green: Int = (base + mRandom.nextInt(256)) / 2
        val blue: Int = (base + mRandom.nextInt(256)) / 2
        return Color.rgb(red % 256, green % 256, blue % 256)
    }
}