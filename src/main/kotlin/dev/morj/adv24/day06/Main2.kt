package dev.morj.adv24.day06

import dev.morj.adv24.lib.consumeInput

object Main2 {
    private val turn = mapOf(
        (-1 to 0) to (0 to 1),
        (0 to 1) to (1 to 0),
        (1 to 0) to (0 to -1),
        (0 to -1) to (-1 to 0)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val linesList = mutableListOf<CharArray>()
        lateinit var start: Pair<Int, Int>
        consumeInput("day-06") { x, line ->
            linesList.add(line.toCharArray())
            val y = line.indexOf("^")
            if (y >= 0) {
                start = x to y
            }
        }
        val data = linesList.toTypedArray()
        // data.forEach { println(it) }
        println(start)
        var result = 0
        data.forEachIndexed { x, line ->
            line.mapIndexed { y, c ->
                if (c != '^') {
                    data[x][y] = '#'
                    if (!wander(start, data)) {
                        println(x to y)
                        result++
                    }
                    data[x][y] = c
                }
            }
        }
        println(result)
    }

    private fun wander(start: Pair<Int, Int>, data: Array<CharArray>): Boolean {
        var (x, y) = start
        var (dx, dy) = -1 to 0
        val visited = mutableSetOf<Pair<Int, Int>>()
        val max = data.size * data[0].size
        var i = 0
        while (true) {
            visited.add(x to y)
            val (nx, ny) = x + dx to y + dy
            if (nx < 0 || ny < 0 || nx >= data.size || ny >= data.size) {
                break
            }
            while (data[x + dx][y + dy] == '#') {
                val t = turn[dx to dy]!!
                dx = t.first
                dy = t.second
            }
            x += dx
            y += dy
            i++
            if (i >= max) {
                return false
            }
        }
        return true
    }
}
