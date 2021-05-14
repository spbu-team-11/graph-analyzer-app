package model.databases.CSV

import com.example.demo.logger.log
import view.GraphView
import model.UndirectedGraph
import model.databases.CSV.data.CSVGraphData
import model.databases.CSV.data.VertexViewData
import utils.Alerter

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.blackmo18.grass.dsl.grass
import javafx.scene.paint.Color
import javafx.scene.text.Text
import java.io.File

@ExperimentalStdlibApi
class CSVFileHandler {

    fun save(file: File, graphView: GraphView) {
        val data: MutableList<MutableList<String>> = mutableListOf(mutableListOf())
        graphView.addVerticesToData(data)
        graphView.addEdgesToData(data)

        val csvWriter = csvWriter { delimiter = ',' }
        val header = listOf("isNode", "name", "x", "y", "color", "radius", "community", "from", "to")

        csvWriter.writeAll(listOf(header), file)
        csvWriter.writeAll(data, file, append = true)
    }

    fun open(file: File): Pair<UndirectedGraph, GraphView?> {
        try {
            val reader = csvReader { skipEmptyLine = true }
            val csvContents = reader.readAllWithHeader(file)
            val data = grass<CSVGraphData>().harvest(csvContents)

            val vertices = hashMapOf<String, VertexViewData>()
            val newGraph = UndirectedGraph()
            data.onEach {
                if (it.isNode) {
                    newGraph.addVertex(it.name)
                    val rgb = it.color?.split("/")?.map { color -> color.toDouble() } ?: listOf(0.0, 0.0, 0.0)
                    val vertex = VertexViewData(
                        it.x,
                        it.y,
                        Text(it.community?.toString() ?: "-1"),
                        it.radius ?: 2.5,
                        Color.color(rgb[0], rgb[1], rgb[2])
                    )
                    vertices[it.name] = vertex
                }

            }
            data.onEach { if (!it.isNode) newGraph.addEdge(it.from!!, it.to!!, it.name) }

            val newGraphView = GraphView(newGraph)
            newGraphView.vertices().onEach {
                val vertex = vertices[it.vertex.element]!!
                vertex.x?.let { x -> it.centerX = x }
                vertex.y?.let { y -> it.centerY = y }
                it.vertex.community = vertex.community.text.toInt()
                it.community.text = vertex.community.text
                it.radius = vertex.radius ?: 2.5
                it.color = vertex.color
            }
            return newGraph to newGraphView
        } catch (e: Exception) {
            Alerter().alertIncorrectArgs("Incorrect .csv file")
            throw(e)
            return UndirectedGraph() to null
        }
    }

    private fun GraphView.addVerticesToData(data: MutableList<MutableList<String>>) {
        vertices().onEach {
            val csvRow = mutableListOf(
                "true",
                it.vertex.element,
                it.centerX.toString(),
                it.centerY.toString(),
                it.color.red.toString() + "/" + it.color.green.toString() + "/" + it.color.blue.toString(),
                it.radius.toString(),
                it.community.text.toString(),
                "",
                ""
            )
            data.add(csvRow)
        }
    }

    private fun GraphView.addEdgesToData(data: MutableList<MutableList<String>>) {
        edges().onEach {
            val csvRow = mutableListOf(
                "false",
                it.label.text,
                "",
                "",
                "",
                "",
                "",
                it.first.vertex.element,
                it.second.vertex.element
            )
            data.add(csvRow)
        }
    }
}