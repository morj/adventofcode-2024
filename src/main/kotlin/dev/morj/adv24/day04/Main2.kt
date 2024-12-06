package dev.morj.adv24.day04

import dev.morj.adv24.lib.consumeInput

object Main2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val linesList = mutableListOf<CharArray>()
        consumeInput("day-04") { _, line ->
            linesList.add(line.toCharArray())
        }
        val data = linesList.toTypedArray()
        var result = 0
        val word = "MAS".toCharArray().toList()
        val midpoints = mutableSetOf<Pair<Int, Int>>()
        linesList.forEachIndexed { x, line ->
            line.forEachIndexed { y, _ ->
                result += search(midpoints, word, data, x, y, 1, -1, mutableListOf())
                result += search(midpoints, word, data, x, y, 1, 1, mutableListOf())
                result += search(midpoints, word, data, x, y, -1, -1, mutableListOf())
                result += search(midpoints, word, data, x, y, -1, 1, mutableListOf())
            }
        }
        println(result)
    }

    private fun search(
        midpoints: MutableSet<Pair<Int, Int>>,
        word: List<Char>,
        data: Array<CharArray>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        debug: MutableList<Pair<Int, Int>>
    ): Int {
        if (data[x][y] != word[0]) {
            return 0
        }
        if (word.size == 1) {
            val coords = debug + Pair(x, y)
            // println(coords.map { data[it.first][it.second] })
            if (midpoints.contains(coords[1])) {
                println(coords[1])
                return 1
            } else {
                midpoints.add(coords[1])
                return 0
            }
        }

        debug.add(Pair(x, y))

        val w = data[0].size
        var result = 0
        val suffix = word.drop(1)

        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && ny >= 0 && nx < data.size && ny < w) {
            result += search(midpoints, suffix, data, nx, ny, dx, dy, debug)
        }

        debug.removeLast()

        return result
    }
}
