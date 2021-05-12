package controller.fileHandler

import model.Graph
import model.databases.SQLite.SQLiteFileHandler
import tornadofx.Controller
import view.GraphView
import java.io.File

class SQLiteFileHandlingStrategy<V ,E>: Controller(), FileHandlingStrategy<V, E> {
    override fun open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>) {
        TODO("Not yet implemented")
    }

    override fun save(file: File, graph: Graph<String, Long>, graphView: GraphView<V, E>) {
        val kek = SQLiteFileHandler()
        kek.save(file, graph)
    }
}