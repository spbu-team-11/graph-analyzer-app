package model.databases.SQLite

import model.Edge
import model.Vertex
import view.VertexView
import java.io.Closeable
import java.sql.DriverManager
import java.sql.SQLException

class SQLiteConnection<V ,E>(private val dbPath: String) : Closeable {
    private val connection = DriverManager.getConnection("$DB_DRIVER:$dbPath")
        ?: throw SQLException("Cannot connect to database")

    private val addEdge by lazy { connection.prepareStatement("") }
    private val addVertex by lazy { connection.prepareStatement("") }
    private val addVertexView by lazy { connection.prepareStatement("") }
    private val addInfo by lazy { connection.prepareStatement("") }
    private val getInfo by lazy { connection.prepareStatement("") }
    private val getAllEdges by lazy { connection.prepareStatement("") }
    private val getAllVertices by lazy { connection.prepareStatement("") }
    private val getAllVerticesView by lazy { connection.prepareStatement("") }

    fun createDb(){
        connection.createStatement()
            .also { stmt ->
                try {
                    stmt.execute("")
                    stmt.execute("")
                    stmt.execute("")
                    stmt.execute("")

                } catch (ex: Exception) {

                } finally {
                    stmt.close()
                }
            }
    }

    fun addEdge(edge: Edge<E, V>){

    }

    fun addVertex(vertex: Vertex<V>){

    }

    fun addVertexView(vertexView: VertexView<V>){

    }

    fun addInfo(){

    }

    fun getAllEdges(){

    }

    fun getAllVertices(){

    }

    fun getAllVerticesView(){

    }

    override fun close() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DB_DRIVER = "jdbc:sqlite"
    }
}