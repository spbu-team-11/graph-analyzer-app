package model.databases.SQLite.dao.edges

import model.databases.SQLite.dao.vertices.Vertices
import org.jetbrains.exposed.dao.id.IntIdTable

object Edges: IntIdTable() {
    val element = varchar("element", 255)
    val first = reference("first", Vertices).nullable()
    val second = reference("second", Vertices).nullable()

}