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
        val candidates = mutableListOf<MutableSet<String>>()
        candidates.addAll(nodes.map { mutableSetOf(it) })
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
