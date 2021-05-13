package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import model.databases.SQLite.SQLiteFileHandler

import tornadofx.Controller
import view.GraphView
import java.io.File

class SQLiteFileHandlingStrategy : Controller(), FileHandlingStrategy {

    override fun open(file: File): Pair<UndirectedGraph, GraphView?> {
        val kek = SQLiteFileHandler()
        return kek.open(file)
    }

    override fun save(file: File, graph: Graph, graphView: GraphView) {
        val kek = SQLiteFileHandler()
        kek.save(file, graph, graphView)
    }
}