package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import view.GraphView
import java.io.File

interface FileHandlingStrategy<V ,E> {
    fun open(file: File): Pair<UndirectedGraph<String, Long>, GraphView<String, Long>?>
    fun save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>)
}