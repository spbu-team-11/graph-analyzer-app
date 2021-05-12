package controller.fileHandler

import model.Graph
import view.GraphView

import tornadofx.Controller
import java.io.File

class CSVFileHandlingStrategy<V, E> : Controller(), FileHandlingStrategy<V, E> {
    override fun open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>) {
        TODO("Not yet implemented")
    }

    override fun save(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>) {
        TODO("Not yet implemented")
    }

}