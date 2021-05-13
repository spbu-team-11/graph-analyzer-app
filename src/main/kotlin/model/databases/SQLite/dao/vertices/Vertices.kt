package model.databases.SQLite.dao.vertices

import org.jetbrains.exposed.dao.id.IntIdTable

object Vertices: IntIdTable() {
    val element = varchar("element", 255)
    val community = integer("community")
}