package dev.morj.adv24.day18

import dev.morj.adv24.lib.consumeInput
import java.util.*

object Main1 {
    const val w = 71
    const val max = 1024

    @JvmStatic
    fun main(args: Array<String>) {
        val data = (0..<w).map { CharArray(w) { '.' } }.toTypedArray()
        consumeInput("day-18") { counter, line ->
            if (counter < max) {
                val (x, y) = line.split(",").map(String::toInt)
                data[x][y] = '#'
            }
        }
        val result = findShortestPath(data, 0, 0)
        data.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, c ->
                if (result.containsKey(Node(x, y))) {
                    print('O')
                } else {
                    print(c)
                }
            }
            println()
        }
        println(result)
    }

    private fun findShortestPath(data: Array<CharArray>, startX: Int, startY: Int): Map<Node, Int> {
        val start = Node(startX, startY)
        val best = mutableMapOf<Node, Int>()
        val height = data.size
        val width = data[0].size
        best[start] = 0
        val queue = PriorityQueue<Node> { a, b ->
            best[a]!!.compareTo(best[b]!!)
        }
        queue.add(start)
        var found = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            val distance = best[node]!!
            node.next(width, height).forEach { next ->
                val x = next.x
                val y = next.y
                if (data[x][y] != '#') {
                    val d = distance + 1 // data[x][y]
                    if (d < found) {
                        val t = best[next]
                        if (t == null || t > d) {
                            best[next] = d
                            queue.add(next)
                        }
                        val finish = x == height - 1 && y == width - 1
                        if (finish) {
                            found = d
                        }
                    }
                }
            }
        }
        // return best
        return best.filter { it.key.x == height - 1 && it.key.y == width - 1 }
    }

    data class Node(val x: Int, val y: Int) {
        fun next(width: Int, height: Int): List<Node> {
            val result = mutableListOf<Node>()
            if (x > 0) result.add(Node(x - 1, y))
            if (x < height - 1) result.add(Node(x + 1, y))
            if (y > 0) result.add(Node(x, y - 1))
            if (y < width - 1) result.add(Node(x, y + 1))
            return result
        }
    }
}
