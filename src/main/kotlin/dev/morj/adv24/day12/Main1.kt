package dev.morj.adv24.day12

import dev.morj.adv24.lib.loadGrid

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = loadGrid("day-12")
        // data.forEach { println(it.joinToString("")) }
        val w = data[0].size
        val regions = mutableListOf<Set<Pair<Int, Int>>>()
        var result = 0L
        data.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, c ->
                if (!regions.any { it.contains(x to y) }) {
                    val region = mutableSetOf<Pair<Int, Int>>()
                    region(data, c, w, x, y, region)
                    regions.add(region)
                    result += region.size * perimeter(data, w, region)
                }
            }
        }
        // debugRegions(regions, data)
        println(result)
    }

    private fun region(data: Array<CharArray>, c: Char, w: Int, x: Int, y: Int, result: MutableSet<Pair<Int, Int>>) {
        if (!result.contains(x to y)) {
            if (x >= 0 && y >= 0 && x < data.size && y < w && data[x][y] == c) {
                result.add(x to y)
                region(data, c, w, x + 1, y, result)
                region(data, c, w, x, y + 1, result)
                region(data, c, w, x - 1, y, result)
                region(data, c, w, x, y - 1, result)
            }
        }
    }

    private fun perimeter(data: Array<CharArray>, w: Int, region: Set<Pair<Int, Int>>): Int {
        val (sx, sy) = region.first()
        val c = data[sx][sy]
        var result = 0
        region.forEach { (x, y) ->
            result += oob(x + 1, y, data, w, c)
            result += oob(x, y + 1, data, w, c)
            result += oob(x - 1, y, data, w, c)
            result += oob(x, y - 1, data, w, c)
        }
        return result
    }

    private fun oob(x: Int, y: Int, data: Array<CharArray>, w: Int, c: Char): Int {
        return if (x < 0 || y < 0 || x >= data.size || y >= w || data[x][y] != c) {
            1
        } else {
            0
        }
    }

    private fun debugRegions(regions: MutableList<Set<Pair<Int, Int>>>, data: Array<CharArray>) {
        val w = data[0].size
        regions.forEach { region ->
            data.forEachIndexed { x, chars ->
                chars.forEachIndexed { y, c ->
                    if (region.contains(x to y)) {
                        print(c)
                    } else {
                        print('.')
                    }
                }
                println()
            }
            println("perimeter: ${perimeter(data, w, region)}")
        }
    }
}
