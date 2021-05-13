package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import model.databases.SQLite.SQLiteFileHandler
import tornadofx.Controller
import view.GraphView
import java.io.File

class SQLiteFileHandlingStrategy<V ,E>: Controller(), FileHandlingStrategy<V, E> {

    override fun open(file: File): Pair<UndirectedGraph<String, Long>, GraphView<String, Long>?> {
        val kek = SQLiteFileHandler()
        return kek.open(file)
    }

    override fun save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>) {
        val kek = SQLiteFileHandler()
        kek.save(file, graph, graphView)
    }
}