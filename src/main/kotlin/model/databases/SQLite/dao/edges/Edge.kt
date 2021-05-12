package model.databases.SQLite.dao.edges

import model.databases.SQLite.dao.vertices.Vertex
import model.databases.SQLite.dao.vertices.Vertices
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Edge(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Edge>(Edges)

    var element by Edges.element
    var first by Vertex optionalReferencedOn Edges.first
    var second by Vertex optionalReferencedOn Edges.second
}