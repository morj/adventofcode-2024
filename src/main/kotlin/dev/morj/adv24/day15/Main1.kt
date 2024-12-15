package dev.morj.adv24.day15

import dev.morj.adv24.lib.consumeInput

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (data, commands) = loadGrid2("day15")
        var (rx, ry) = printData(data)
        println()
        commands.forEach { commandline ->
            commandline.forEach { command ->
                when (command) {
                    '<' -> getNextFree(data, 0, -1, rx, ry)
                    '>' -> getNextFree(data, 0, 1, rx, ry)
                    '^' -> getNextFree(data, -1, 0, rx, ry)
                    'v' -> getNextFree(data, 1, 0, rx, ry)
                    else -> throw IllegalArgumentException("Unknown command $command")
                }?.let { (cx, cy, nx, ny) ->
                    data[rx][ry] = '.'
                    data[cx][cy] = '@'
                    rx = cx
                    ry = cy
                    if (cx != nx || cy != ny) {
                        data[nx][ny] = 'O'
                    }
                }
                //println("Move $command:")
                //printData(data)
                //println()
            }
        }

        printData(data)

        var result = 0L
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c == 'O') {
                    result += 100L * x + y
                }
            }
        }
        println(result)
    }

    private fun getNextFree(data: Array<CharArray>, dx: Int, dy: Int, x: Int, y: Int): Next? {
        var cx = x + dx
        var cy = y + dy
        while (data[cx][cy] == 'O') {
            cx += dx
            cy += dy
        }
        return when (data[cx][cy]) {
            '.' -> Next(x + dx, y + dy, cx, cy)
            '#' -> null
            else -> throw IllegalArgumentException("Bad char ${data[cx][cy]}")
        }
    }

    private fun printData(data: Array<CharArray>): Pair<Int, Int> {
        lateinit var result: Pair<Int, Int>
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c == '@') {
                    result = x to y
                }
                print(c)
            }
            println()
        }
        return result
    }

    private fun Any.loadGrid2(name: String): Pair<Array<CharArray>, MutableList<CharArray>> {
        val linesList = mutableListOf<CharArray>()
        var seenEmpty = false
        val commands = mutableListOf<CharArray>()
        consumeInput(name) { _, line ->
            if (line.isEmpty()) {
                seenEmpty = true
            }
            if (seenEmpty) {
                if (line.isNotEmpty()) {
                    commands.add(line.toCharArray())
                }
            } else {
                linesList.add(line.toCharArray())
            }
        }
        return linesList.toTypedArray() to commands
    }

    data class Next(val cx: Int, val cy: Int, val freeX: Int, val freeY: Int)
}
