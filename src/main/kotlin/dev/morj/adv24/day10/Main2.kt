package dev.morj.adv24.day10

import dev.morj.adv24.lib.loadGrid

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = loadGrid("day-10")
        val w = data[0].size
        var result = 0
        val trails = mutableSetOf<String>()
        data.forEachIndexed { x, column ->
            column.forEachIndexed { y, c ->
                if (c == '0') {
                    val t = walk(x, y, w, trails, data, mutableListOf(), c.digitToInt())
                    result += t
                    // println(t)
                }
            }
        }
        // trails.forEach { println(it) }
        println(result)
        // println(trails.size)
    }

    private fun walk(x: Int, y: Int, w: Int, trails: MutableSet<String>, data: Array<CharArray>, visited: MutableList<Pair<Int, Int>>, expect: Int): Int {
        var result = 0
        if (x >= 0 && y >= 0 && x < data.size && y < w && data[x][y] != '.' && data[x][y].digitToInt() == expect) {
            // visited.add(x to y)
            if (expect == 9) {
                // val trail = visited.joinToString()
                // trails.add(trail)
                return 1
            } else {
                result += walk(x + 1, y, w, trails, data, visited, expect + 1)
                result += walk(x, y + 1, w, trails, data, visited, expect + 1)
                result += walk(x - 1, y, w, trails, data, visited, expect + 1)
                result += walk(x, y - 1, w, trails, data, visited, expect + 1)
            }
        }
        return result
    }
}
