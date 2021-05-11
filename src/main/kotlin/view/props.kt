package view

import model.UndirectedGraph

import tornadofx.booleanProperty
import tornadofx.doubleProperty
import kotlin.random.Random

@Suppress("ClassName")
object props {
    object layout {
        val auto = booleanProperty()
    }

    object vertex {
        var radius = doubleProperty(5.0)
        val bigRadius = doubleProperty(20.0)
        val label = booleanProperty()
        val community = booleanProperty()
    }

    object edge {
        val label = booleanProperty()
    }

    object GUI {
        val darkTheme = booleanProperty(false)
        val leftMenu = booleanProperty(true)
    }

    val SAMPLE_GRAPH = hashMapOf(
        Pair("Example 1",
            UndirectedGraph<String, Long>().apply {
                addVertex("A")
                addVertex("B")
                addVertex("C")
                addVertex("D")
                addVertex("E")
                addVertex("F")
                addVertex("G")

                addEdge("A", "B", 1)
                addEdge("A", "C", 2)
                addEdge("A", "D", 3)
                addEdge("A", "E", 4)
                addEdge("A", "F", 5)
                addEdge("A", "G", 6)

                addVertex("H")
                addVertex("I")
                addVertex("J")
                addVertex("K")
                addVertex("L")
                addVertex("M")
                addVertex("N")

                addEdge("H", "I", 7)
                addEdge("H", "J", 8)
                addEdge("H", "K", 9)
                addEdge("H", "L", 10)
                addEdge("H", "M", 11)
                addEdge("H", "N", 12)

                addEdge("A", "H", 0)
            }),
        Pair("Example 2",
            UndirectedGraph<String, Long>().apply {
                addVertex("A")
                addVertex("B")
                addVertex("C")
                addVertex("D")
                addVertex("E")
                addVertex("F")
                addVertex("G")

                addEdge("A", "B", 1)
                addEdge("A", "C", 2)
                addEdge("B", "C", 3)
                addEdge("F", "E", 4)
                addEdge("G", "F", 5)
                addEdge("E", "G", 6)
                addEdge("C", "D", 7)
                addEdge("E", "D", 0)


            }),
        Pair("Big graph", UndirectedGraph<String, Long>().apply {
            for (i in 0..1000) {
                addVertex(i.toString())
            }

            val random = Random(234)
            for (i in 0..1000) {
                val first = random.nextInt() % 1000
                val second = random.nextInt() % 1000
                addEdge(first.toString(),  second.toString(), first.toLong() * 1000 + second.toLong())
                addEdge(first.toString(),  (second + 1).toString(), first.toLong() * 1000 + second.toLong())
                addEdge((first + 1).toString(),  second.toString(), first.toLong() * 1000 + second.toLong())
            }
        }),
        Pair("Little example", UndirectedGraph<String, Long>().apply {
            addVertex("a")
            addVertex("b")
            addEdge("a", "b", 1)
            addVertex("c")
            addVertex("d")
            addEdge("c", "d", 2)
            addVertex("e")
            addVertex("f")
            addEdge("e", "f", 3)
        })
    )
}

