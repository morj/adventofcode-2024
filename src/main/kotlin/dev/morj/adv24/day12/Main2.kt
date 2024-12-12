package dev.morj.adv24.day12

import dev.morj.adv24.lib.loadGrid

object Main2 {
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
        // data.forEach { println(it.joinToString("")) }
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
        val sides = mutableMapOf<SideKey, Int>()
        // var counter = 'a'
        region.sortedBy { it.first }.forEach { (x, y) ->
            val deltas = listOf(0 to 1, 0 to -1)
            deltas.forEach { (dx, dy) ->
                if (oob(x + dx, y + dy, data, w, c)) {
                    val key = SideKey(x, y, dx, dy)
                    // if (counter == 'k') { println('?') }
                    if (sides.containsKey(SideKey(x - 1, y, dx, dy))) {
                        sides[key] = sides[SideKey(x - 1, y, dx, dy)]!!
                    } else if (sides.containsKey(SideKey(x + 1, y, dx, dy))) {
                        sides[key] = sides[SideKey(x + 1, y, dx, dy)]!!
                    } else {
                        sides[key] = result++
                        // counter++
                    }
                    // if (c == 'R') { data[x + dx][y + dy] = sides[SideKey(x, y, dx, dy)]!! }
                }
            }
        }
        region.sortedBy { it.second }.forEach { (x, y) ->
            val deltas = listOf(1 to 0, -1 to 0)
            deltas.forEach { (dx, dy) ->
                if (oob(x + dx, y + dy, data, w, c)) {
                    val key = SideKey(x, y, dx, dy)
                    // if (counter == 'k') { println('?') }
                    if (sides.containsKey(SideKey(x, y - 1, dx, dy))) {
                        sides[key] = sides[SideKey(x, y - 1, dx, dy)]!!
                    } else if (sides.containsKey(SideKey(x, y + 1, dx, dy))) {
                        sides[key] = sides[SideKey(x, y + 1, dx, dy)]!!
                    } else {
                        sides[key] = result++
                        // counter++
                    }
                    // if (c == 'R') { data[x + dx][y + dy] = sides[SideKey(x, y, dx, dy)]!! }
                }
            }
        }
        return result
    }

    data class SideKey(val x: Int, val y: Int, val dx: Int, val dy: Int)

    private fun oob(x: Int, y: Int, data: Array<CharArray>, w: Int, c: Char): Boolean {
        return x < 0 || y < 0 || x >= data.size || y >= w || data[x][y] != c
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
