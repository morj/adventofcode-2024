package dev.morj.adv24.day04

import dev.morj.adv24.lib.consumeInput

object Main1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val linesList = mutableListOf<CharArray>()
        consumeInput("day-04") { _, line ->
            linesList.add(line.toCharArray())
        }
        val data = linesList.toTypedArray()
        var result = 0
        val word = "XMAS".toCharArray().toList()
        linesList.forEachIndexed { x, line ->
            line.forEachIndexed { y, _ ->
                result += search(word, data, x, y, 1, -1, mutableListOf())
                result += search(word, data, x, y, 1, 1, mutableListOf())
                result += search(word, data, x, y, 1, 0, mutableListOf())
                result += search(word, data, x, y, -1, -1, mutableListOf())
                result += search(word, data, x, y, -1, 1, mutableListOf())
                result += search(word, data, x, y, -1, 0, mutableListOf())
                result += search(word, data, x, y, 0, 1, mutableListOf())
                result += search(word, data, x, y, 0, -1, mutableListOf())
            }
        }
        println(result)
    }

    private fun search(
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
            println(coords)
            return 1
        }

        debug.add(Pair(x, y))

        val w = data[0].size
        var result = 0
        val suffix = word.drop(1)

        val nx = x + dx
        val ny = y + dy
        if (nx >= 0 && ny >= 0 && nx < data.size && ny < w) {
            result += search(suffix, data, nx, ny, dx, dy, debug)
        }

        debug.removeLast()

        return result
    }
}
