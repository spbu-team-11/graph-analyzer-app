package model.databases.CSV

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.blackmo18.grass.dsl.grass
import model.Graph
import view.GraphView

import java.io.File

@ExperimentalStdlibApi
class CSVFileHandler() {

    fun save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>) {
        val data: MutableList<MutableList<String>> = mutableListOf(mutableListOf())

        var i = 0
        graphView.vertices().onEach {
            data.add(mutableListOf(
                "true",
                it.vertex.element,
                it.centerX.toString(),
                it.centerY.toString(),
                it.color.red.toString(),
                it.community.text.toString(),
                "",
                ""
            ))
        }
        graphView.edges().onEach {
            data.add(mutableListOf(
                "false",
                "",
                "",
                "",
                "",
                "",
                it.first.vertex.element,
                it.second.vertex.element
            ))
        }

        val csvWriter = csvWriter { delimiter = ',' }
        val header = listOf("isNode", "name", "x", "y", "color", "community", "from", "to")

        csvWriter.writeAll(listOf(header), file)
        csvWriter.writeAll(data, file, append = true)
    }

    fun open(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>) {
        val csvContents = csvReader().readAllWithHeader(file)
        val data = grass<CSVGraphData>().harvest(csvContents)

        data.onEach {
            if (it.isNode) graph.addVertex(it.name)
        }

        data.onEach {
            if (!it.isNode) graph.addEdge(it.from!!, it.to!!, it.name.toLong())
        }

        graphView.vertices()
    }
}