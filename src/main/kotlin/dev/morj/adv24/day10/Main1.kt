package dev.morj.adv24.day10

import dev.morj.adv24.lib.loadGrid

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = loadGrid("day-10")
        val w = data[0].size
        var result = 0
        data.forEachIndexed { x, column ->
            column.forEachIndexed { y, c ->
                if (c == '0') {
                    val t = walk(x, y, w, data, mutableSetOf(), c.digitToInt())
                    result += t
                    // println(t)
                }
            }
        }
        println(result)
    }

    private fun walk(x: Int, y: Int, w: Int, data: Array<CharArray>, visited: MutableSet<Pair<Int, Int>>, expect: Int): Int {
        var result = 0
        if (!visited.contains(x to y) && x >= 0 && y >= 0 && x < data.size && y < w && data[x][y] != '.' && data[x][y].digitToInt() == expect) {
            visited.add(x to y)
            if (expect == 9) {
                return 1
            } else {
                result += walk(x + 1, y, w, data, visited, expect + 1)
                result += walk(x, y + 1, w, data, visited, expect + 1)
                result += walk(x - 1, y, w, data, visited, expect + 1)
                result += walk(x, y - 1, w, data, visited, expect + 1)
            }
            visited.remove(x to y)
        }
        return result
    }
}
