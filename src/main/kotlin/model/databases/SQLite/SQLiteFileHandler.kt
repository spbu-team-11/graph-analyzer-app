package model.databases.SQLite

import com.example.demo.logger.log
import model.Graph
import model.databases.SQLite.dao.edges.Edges
import model.databases.SQLite.dao.vertices.Vertex
import model.databases.SQLite.dao.vertices.Vertices
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.Connection

class SQLiteFileHandler {
    fun save(file: File, graph: Graph<String, Long>){
//        log("File: $file")
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Edges)
            SchemaUtils.create(Vertices)
            graph.vertices().forEach {
                Vertex.new{
                    element = it.element
                    community = it.community
                }
            }
        }
    }
}