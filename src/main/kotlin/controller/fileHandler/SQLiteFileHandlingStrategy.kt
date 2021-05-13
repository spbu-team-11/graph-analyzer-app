package controller.fileHandler

import model.Graph
import tornadofx.Controller
import view.GraphView
import java.io.File

class SQLiteFileHandlingStrategy: Controller(), FileHandlingStrategy {

    override fun <V, E>open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>) {
        TODO("Not yet implemented")
    }

    override fun <V, E> save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>) {
        TODO("Not yet implemented")
    }
}