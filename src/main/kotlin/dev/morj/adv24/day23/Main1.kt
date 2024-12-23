package dev.morj.adv24.day23

import dev.morj.adv24.lib.consumeInput

object Main1 {

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
        nodes.forEachIndexed { i, x ->
            nodes.forEachIndexed { j, y ->
                if (j > i) {
                    nodes.forEachIndexed { k, z ->
                        if (k > j) {
                            if (routes.contains(x to y) && routes.contains(y to z) && routes.contains(z to x)) {
                                if (x.startsWith("t") || y.startsWith("t") || z.startsWith("t")) {
                                    count++
                                }
                            }
                        }
                    }
                }
            }
        }
        println(count)
    }
}
