package dev.morj.adv24.day23

import dev.morj.adv24.lib.consumeInput

object Main2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val nodes = hashSetOf<String>()
        val routes = hashSetOf<Pair<String, String>>()
        consumeInput("day-23") { _, line ->
            val (from, to) = line.split("-")
            nodes.add(from)
            nodes.add(to)
            routes.add(to to from)
            routes.add(from to to)
        }
        println(nodes)
        println(routes)
        var count = 0L
        val candidates = mutableListOf<MutableSet<String>>()
        nodes.forEachIndexed { i, x ->
            nodes.forEachIndexed { j, y ->
                if (j > i) {
                    nodes.forEachIndexed { k, z ->
                        if (k > j) {
                            if (routes.contains(x to y) && routes.contains(y to z) && routes.contains(z to x)) {
                                candidates.add(mutableSetOf(x, y, z))
                                count++
                            }
                        }
                    }
                }
            }
        }
        println("triples found: $count")
        candidates.forEach { set ->
            nodes.forEach { node ->
                if (!set.contains(node) && set.all { s -> routes.contains(s to node) }) {
                    set.add(node)
                }
            }
        }
        println(candidates.maxBy { it.size }.sorted().joinToString(","))
    }
}
