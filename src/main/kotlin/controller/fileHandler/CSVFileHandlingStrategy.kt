package controller.fileHandler

import model.Graph
import model.databases.CSV.CSVFileHandler
import view.GraphView

import tornadofx.Controller
import java.io.File

@ExperimentalStdlibApi
class CSVFileHandlingStrategy : Controller(), FileHandlingStrategy {
    override fun <V, E> open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>) {

    }

    override fun <V, E> save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>) {
        val handler = CSVFileHandler()
        handler.save(file, graph, graphView)
    }

}