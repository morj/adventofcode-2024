package dev.morj.adv24.day20

import dev.morj.adv24.lib.loadGrid
import java.util.*
import kotlin.math.abs

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = loadGrid("day-20")
        val (sx, sy) = findChar(data, 'S')
        val (ex, ey) = findChar(data, 'E')
        val ps = findShortestPath(data, sx, sy)
        val pe = findShortestPath(data, ex, ey)
        val cost = ps[Node(ex, ey)]!!
        println("normal cost: $cost")
        var result = 0
        ps.forEach { (ns, cs) ->
            pe.forEach { (ne, ce) ->
                val dist = distance(ns, ne)
                if (dist <= 20) {
                    val adj = cs + ce + dist
                    if (adj < cost) {
                        val saving = cost - adj
                        if (saving >= 100) {
                            result++
                            println("saving: $saving")
                        }
                    }
                }
            }
        }
        println("total worthy cheats: $result")
    }

    private fun distance(ns: Node, ne: Node): Int {
        return abs(ns.x - ne.x) + abs(ns.y - ne.y)
    }

    private fun findChar(data: Array<CharArray>, t: Char): Node {
        data.forEachIndexed { x, l ->
            l.forEachIndexed { y, c ->
                if (c == t) {
                    return Node(x, y)
                }
            }
        }
        throw IllegalStateException()
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
                    val d = distance + 1
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
        return best
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
