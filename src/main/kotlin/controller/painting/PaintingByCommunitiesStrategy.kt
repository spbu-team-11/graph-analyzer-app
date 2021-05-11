package controller.painting

import com.example.demo.logger.log
import javafx.scene.paint.Color
import model.UndirectedGraph
import model.Community.CommunitiesFinder
import tornadofx.Controller
import view.GraphView
import kotlin.random.Random

class PaintingByCommunitiesStrategy : Controller(), PaintingStrategy {

    override fun <V, E> showCommunities(
        graph: UndirectedGraph<String, Long>,
        graphView: GraphView<String, Long>,
        nIteration: String,
        resolution: String
    ) {
        log("community finding starting...")

        val finder = CommunitiesFinder<V, E>()
        val returnCode = finder.findCommunity(graph, nIteration, resolution)
        if (!returnCode) return
        graphView.vertices()
            .onEach {
                val com = it.vertex.community
                it.community.text = com.toString()
                it.color = generateRandomColor(com * 100)
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