package dev.morj.adv24.day16

import dev.morj.adv24.lib.loadGrid

object Main2 {
    private val turns = mapOf(
        (-1 to 0) to (0 to 1),
        (0 to 1) to (1 to 0),
        (1 to 0) to (0 to -1),
        (0 to -1) to (-1 to 0)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val data = loadGrid("day-16")
        val (sx, sy, ex, ey) = findSE(data)
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                print(c)
            }
            println()
        }
        val costs = mutableMapOf<Key, Long>()
        println("$sx, $sy, $ex, $ey")
        walk(sx, sy, 0, 1, data, 0, costs)
        val finals = turns.keys.map { Key(ex, ey, it.first, it.second) }
        val result = finals.minOf { costs[it] ?: Long.MAX_VALUE }
        println("Min cost: $result")
        val paths = mutableSetOf<Pair<Int, Int>>()
        finals.forEach { (x, y, dx, dy) ->
            val stack = arrayListOf<Pair<Int, Int>>()
            walkBack(x, y, dx, dy, data, stack, result, paths, costs)
        }
        println(paths.size)
    }

    private fun walk(
        x: Int, y: Int, dx: Int, dy: Int, data: Array<CharArray>, cost: Long, costs: MutableMap<Key, Long>
    ) {
        if (data[x][y] == '#') { return }
        val key = Key(x, y, dx, dy)
        val maybeCost = costs[key]
        if (maybeCost == null || maybeCost > cost) {
            costs[key] = cost
            walk(x + dx, y + dy, dx, dy, data, cost + 1, costs)
            val (tx, ty) = turns[dx to dy]!!
            walk(x, y, tx, ty, data, cost + 1000, costs)
            walk(x, y, -tx, -ty, data, cost + 1000, costs)
        }
    }

    private fun walkBack(
        x: Int, y: Int, dx: Int, dy: Int, data: Array<CharArray>, stack: MutableList<Pair<Int, Int>>,
        cost: Long, paths: MutableSet<Pair<Int, Int>>, costs: MutableMap<Key, Long>
    ) {
        if (data[x][y] == '#') { return }
        val key = Key(x, y, dx, dy)
        val maybeCost = costs[key]!!
        if (maybeCost == cost) {
            stack.add(x to y)
            if (data[x][y] == 'S') {
                paths.addAll(stack)
            }
            walkBack(x - dx, y - dy, dx, dy, data, stack, cost - 1, paths, costs)
            val (tx, ty) = turns[dx to dy]!!
            walkBack(x, y, tx, ty, data, stack, cost - 1000, paths, costs)
            walkBack(x, y, -tx, -ty, data, stack, cost - 1000, paths, costs)
            stack.removeLast()
        }
    }

    private fun findSE(data: Array<CharArray>): MutableList<Int> {
        val result = mutableListOf<Int>()
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c == 'S') {
                    result.add(x)
                    result.add(y)
                }
            }
        }
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c == 'E') {
                    result.add(x)
                    result.add(y)
                }
            }
        }
        return result
    }

    private data class Key(val x: Int, val y: Int, val dx: Int, val dy: Int)
}
