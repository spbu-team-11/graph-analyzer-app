package model.databases.SQLite

import HandlerLogger
import view.GraphView
import model.Graph
import model.UndirectedGraph
import model.databases.SQLite.dao.edges.Edge
import model.databases.SQLite.dao.edges.Edges
import model.databases.SQLite.dao.vertices.Vertex
import model.databases.SQLite.dao.vertices.Vertices
import model.databases.SQLite.dao.verticesView.VertexView
import model.databases.SQLite.dao.verticesView.VerticesView
import utils.Alerter

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import javafx.scene.paint.Color

class SQLiteFileHandler {

    private val logger = HandlerLogger(javaClass)

    fun save(file: File, graph: Graph, graphView: GraphView) {
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Edges)
            SchemaUtils.create(Vertices)
            SchemaUtils.create(VerticesView)
            graph.vertices().forEach {
                Vertex.new {
                    element = it.element
                    community = it.community
                }
            }
            graph.edges().forEach {
                Edge.new {
                    element = it.element
                    first = Vertex.find { Vertices.element eq it.vertices.first.element }.first()
                    second = Vertex.find { Vertices.element eq it.vertices.second.element }.first()
                }
            }
            graphView.vertices().forEach {
                VertexView.new {
                    vertex = Vertex.find { Vertices.element eq it.vertex.element }.first()
                    color = it.color.red.toString() + "/" + it.color.green.toString() + "/" + it.color.blue.toString()
                    x = it.centerX
                    y = it.centerY
                    r = it.radius
                }
            }
        }

        logger.logSave()
    }

    fun open(file: File): Pair<UndirectedGraph, GraphView?> {
        try {
            Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
            val newGraph = UndirectedGraph()
            var exists = false
            transaction {
                Vertex.all().forEach {
                    newGraph.addVertex(it.element)
                }
                Edge.all().forEach {
                    newGraph.addEdge(it.first!!.element, it.second!!.element, it.element)
                }
                exists = VerticesView.exists()
            }
            if (exists) {
                val newGraphView = GraphView(newGraph)
                transaction {
                    newGraphView.vertices().onEach {
                        val vertex = Vertex.find { Vertices.element eq it.vertex.element }.first()
                        val tmp = VertexView.find { VerticesView.vertex eq vertex.id }.first()
                        it.centerX = tmp.x
                        it.centerY = tmp.y
                        it.vertex.community = vertex.community
                        it.community.text = vertex.community.toString()
                        val rgb = tmp.color.split("/").map { color -> color.toDouble() }
                        it.color = Color.color(rgb[0], rgb[1], rgb[2])
                        it.radius = tmp.r
                    }
                }
                logger.logSave()
                return newGraph to newGraphView
            } else {
                logger.logSave()
                return newGraph to null
            }
        } catch (e: Exception) {
            Alerter().alertIncorrectArgs("Incorrect database")
            return UndirectedGraph() to null
        }

    }
}