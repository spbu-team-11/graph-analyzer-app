package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import model.databases.SQLite.SQLiteFileHandler
import view.GraphView

import tornadofx.Controller
import java.io.File

class SQLiteFileHandlingStrategy : Controller(), FileHandlingStrategy {

    override fun open(file: File): Pair<UndirectedGraph, GraphView?> {
        val handler = SQLiteFileHandler()
        return handler.open(file)
    }

    override fun save(file: File, graph: Graph, graphView: GraphView) {
        val handler = SQLiteFileHandler()
        handler.save(file, graph, graphView)
    }
}