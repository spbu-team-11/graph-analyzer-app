package model.databases.CSV

import view.GraphView
import model.UndirectedGraph
import model.databases.CSV.data.CSVGraphData
import model.databases.CSV.data.VertexViewData

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.blackmo18.grass.dsl.grass
import javafx.scene.paint.Color
import javafx.scene.text.Text
import java.io.File

@ExperimentalStdlibApi
class CSVFileHandler() {

    fun save(file: File, graphView: GraphView) {
        val data: MutableList<MutableList<String>> = mutableListOf(mutableListOf())

        var i = 0
        graphView.vertices().onEach {
            data.add(
                mutableListOf(
                    "true",
                    it.vertex.element,
                    it.centerX.toString(),
                    it.centerY.toString(),
                    it.color.red.toString() + "/" + it.color.green.toString() + "/" + it.color.blue.toString(),
                    it.community.text.toString(),
                    "",
                    ""
                )
            )
        }
        graphView.edges().onEach {
            data.add(
                mutableListOf(
                    "false",
                    it.label.text,
                    "",
                    "",
                    "",
                    "",
                    it.first.vertex.element,
                    it.second.vertex.element
                )
            )
        }

        val csvWriter = csvWriter { delimiter = ',' }
        val header = listOf("isNode", "name", "x", "y", "color", "community", "from", "to")

        csvWriter.writeAll(listOf(header), file)
        csvWriter.writeAll(data, file, append = true)
    }

    fun open(file: File): Pair<UndirectedGraph, GraphView> {
        val reader = csvReader { skipEmptyLine = true }
        val csvContents = reader.readAllWithHeader(file)
        val data = grass<CSVGraphData>().harvest(csvContents)

        val vertices = hashMapOf<String, VertexViewData>()
        val newGraph = UndirectedGraph()
        data.onEach {
            if (it.isNode) {
                newGraph.addVertex(it.name)
                val rgb = it.color!!.split("/").map { color -> color.toDouble() }
                val vertex = VertexViewData(
                    it.x!!,
                    it.y!!,
                    Text(it.community?.toString() ?: "-1"),
                    Color.color(rgb[0], rgb[1], rgb[2])
                )
                vertices[it.name] = vertex
            }

        }
        data.onEach { if (!it.isNode) newGraph.addEdge(it.from!!, it.to!!, it.name) }

        val newGraphView = GraphView(newGraph)
        newGraphView.vertices().onEach {
            val vertex = vertices[it.vertex.element]!!
            it.centerX = vertex.x
            it.centerY = vertex.y
            it.community = vertex.community
            it.color = vertex.color
        }
        return newGraph to newGraphView
    }
}