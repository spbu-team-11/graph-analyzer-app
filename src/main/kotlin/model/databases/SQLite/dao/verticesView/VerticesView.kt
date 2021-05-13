package model.databases.SQLite.dao.verticesView

import model.databases.SQLite.dao.vertices.Vertices

import org.jetbrains.exposed.dao.id.IntIdTable

object VerticesView : IntIdTable() {

    val vertex = reference("vertex", Vertices).nullable()
    val x = double("x")
    val y = double("y")
    val color = varchar("color", 255)
}