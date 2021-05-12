package model.databases.SQLite.dao.vertices

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Vertex(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Vertex>(Vertices)

    var element by Vertices.element
    var community by Vertices.community
}