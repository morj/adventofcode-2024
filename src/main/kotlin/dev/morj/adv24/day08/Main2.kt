package dev.morj.adv24.day08

import dev.morj.adv24.lib.consumeInput
import dev.morj.adv24.lib.gcd

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val linesList = mutableListOf<CharArray>()
        consumeInput("day-08") { _, line ->
            linesList.add(line.toCharArray())
        }
        val data = linesList.toTypedArray()
        var result = 0
        val maxX = data.size
        val maxY = data[0].size
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        data.forEachIndexed { x1, line ->
            line.forEachIndexed { y1, c1 ->
                if (data[x1][y1] != '.') {
                    if (!antiNodes.contains(x1 to y1)) {
                        result++
                        antiNodes.add(x1 to y1)
                    }
                    data.forEachIndexed { x2, line ->
                        line.forEachIndexed { y2, c2 ->
                            if (c1 == c2) {
                                val dx = x2 - x1
                                val dy = y2 - y1
                                if (dx != 0 || dy != 0) {
                                    val gcd = gcd(dx, dy)
                                    val sx = dx / gcd
                                    val sy = dy / gcd
                                    for (i in -50..50) {
                                        val xa = x2 + i * sx
                                        val ya = y2 + i * sy
                                        if ((xa in (0..<maxX)) && (ya in (0..<maxY))) {
                                            if (!antiNodes.contains(xa to ya)) {
                                                result++
                                                antiNodes.add(xa to ya)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        data.forEachIndexed { x1, line ->
            line.forEachIndexed { y1, c ->
                if (antiNodes.contains(x1 to y1)) {
                    print('#')
                } else {
                    print(c)
                }
            }
            println()
        }
        println(result)
    }
}