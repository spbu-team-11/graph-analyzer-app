package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import model.databases.CSV.CSVFileHandler
import view.GraphView

import tornadofx.Controller
import java.io.File

@ExperimentalStdlibApi
class CSVFileHandlingStrategy : Controller(), FileHandlingStrategy {

    override fun save(file: File, graph: Graph, graphView: GraphView) {
        val handler = CSVFileHandler()
        handler.save(file, graphView)
    }

    override fun open(file: File): Pair<UndirectedGraph, GraphView?> {
        val handler = CSVFileHandler()
        return handler.open(file)
    }
}