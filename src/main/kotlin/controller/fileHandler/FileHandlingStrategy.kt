package controller.fileHandler

import model.Graph
import view.GraphView
import java.io.File

interface FileHandlingStrategy<V ,E> {
    fun open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>)
    fun save(file: File, graph: Graph<String, Long>, graphView: GraphView<V, E>)
}