package controller.fileHandler

import model.Graph
import model.UndirectedGraph
import view.GraphView

import java.io.File

interface FileHandlingStrategy {

    fun open(file: File): Pair<UndirectedGraph, GraphView?>

    fun save(file: File, graph: Graph, graphView: GraphView)
}