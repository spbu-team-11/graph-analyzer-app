package model.databases.SQLite.dao.verticesView

import model.databases.SQLite.dao.vertices.Vertex

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class VertexView(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<VertexView>(VerticesView)

    var vertex by Vertex optionalReferencedOn VerticesView.vertex
    var x by VerticesView.x
    var y by VerticesView.y
    var r by VerticesView.r
    var color by VerticesView.color
}