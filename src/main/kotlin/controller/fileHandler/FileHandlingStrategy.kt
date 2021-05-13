package controller.fileHandler

import model.Graph
import view.GraphView
import java.io.File

interface FileHandlingStrategy {

    fun <V, E> open(file: File, graph: Graph<V, E>, graphView: GraphView<V, E>)
    fun <V, E> save(file: File, graph: Graph<String, Long>, graphView: GraphView<String, Long>)
}