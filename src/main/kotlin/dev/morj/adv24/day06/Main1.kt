package dev.morj.adv24.day06

import dev.morj.adv24.lib.consumeInput

object Main1 {
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
        val visited = wander(start, data)
        data.forEachIndexed { x, line ->
            println(line.mapIndexed { y, c ->
                if (visited.contains(x to y)) {
                    'X'
                } else {
                    c
                }
            }.joinToString(""))
        }
        println(visited.size)
    }

    private fun wander(start: Pair<Int, Int>, data: Array<CharArray>): MutableSet<Pair<Int, Int>> {
        var (x, y) = start
        var (dx, dy) = -1 to 0
        val visited = mutableSetOf<Pair<Int, Int>>()
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
        }
        return visited
    }
}
