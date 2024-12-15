package dev.morj.adv24.day15

import dev.morj.adv24.lib.consumeInput
import java.lang.IllegalStateException

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (data, commands) = loadGrid2("day15")
        var (rx, ry) = printData(data)
        println()
        commands.forEach { commandline ->
            commandline.forEach { command ->
                // println("Move $command:")
                if (command == '<' || command == '>') {
                    ry = moveHorizontal(command, data, rx, ry)
                }
                if (command == '^' || command == 'v') {
                    rx = moveVertical(command, data, rx, ry)
                }
                // printData(data)
                // println()
            }
        }

        printData(data)

        var result = 0L
        data.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c == '[') {
                    result += 100L * x + y
                }
            }
        }
        println(result)
    }

    private fun moveVertical(command: Char, data: Array<CharArray>, rx: Int, ry: Int): Int {
        val dx = if (command == '^') -1 else 1
        val affected = mutableSetOf<Pair<Int, Int>>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        getAffected(data, visited, affected, dx, rx, ry)
        // result.forEach { (x, y) -> data[x][y] = '%' }
        // printData(data)
        if (affected.any { (x, y) -> data[x][y] == '#' }) {
            return rx
        } else {
            if (!affected.all { (x, y) -> data[x][y] == '.' }) throw IllegalStateException("?")
            val toMove = if (dx > 0) {
                visited.sortedBy { it.first }.reversed()
            } else {
                visited.sortedBy { it.first }
            }
            toMove.forEach { (x, y) ->
                data[x + dx][y] = data[x][y]
                data[x][y] = '.'
            }
            // println(toMove.joinToString())
            return rx + dx
        }
    }

    private fun getAffected(
        data: Array<CharArray>, visited: MutableSet<Pair<Int, Int>>, result: MutableSet<Pair<Int, Int>>,
        dx: Int, x: Int, y: Int
    ) {
        if (!visited.contains(x to y)) {
            when (data[x][y]) {
                '.' -> result.add(x to y)
                '#' -> result.add(x to y)
                '@' -> {
                    visited.add(x to y)
                    getAffected(data, visited, result, dx, x + dx, y)
                }

                '[' -> {
                    visited.add(x to y)
                    getAffected(data, visited, result, dx, x + dx, y)
                    getAffected(data, visited, result, dx, x, y + 1)
                }

                ']' -> {
                    visited.add(x to y)
                    getAffected(data, visited, result, dx, x + dx, y)
                    getAffected(data, visited, result, dx, x, y - 1)
                }
            }
        }
    }

    private fun moveHorizontal(command: Char, data: Array<CharArray>, rx: Int, ry: Int): Int {
        var result = ry
        if (command == '<') {
            getNextFreeHorizontal(data, -1, rx, ry)
        } else {
            getNextFreeHorizontal(data, 1, rx, ry)
        }?.let { (cy, ny) ->
            data[rx][ry] = '.'
            data[rx][cy] = '@'
            result = cy

            if (ny < result) {
                for (i in ny + 1..<result) {
                    flip(data, rx, i)
                }
                data[rx][ny] = '['
            } else if (ny > result) {
                for (i in result + 1..<ny) {
                    flip(data, rx, i)
                }
                data[rx][ny] = ']'
            }
        }
        return result
    }

    private fun flip(data: Array<CharArray>, rx: Int, i: Int) {
        when (data[rx][i]) {
            '[' -> data[rx][i] = ']'
            ']' -> data[rx][i] = '['
            else -> throw IllegalArgumentException("Must be part of the box")
        }
    }

    private fun getNextFreeHorizontal(data: Array<CharArray>, dy: Int, x: Int, y: Int): Pair<Int, Int>? {
        var cy = y + dy
        while (data[x][cy] == '[' || data[x][cy] == ']') {
            cy += dy
        }
        return when (data[x][cy]) {
            '.' -> Pair(y + dy, cy)
            '#' -> null
            else -> throw IllegalArgumentException("Bad char ${data[x][cy]}")
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
                linesList.add(line.map { c ->
                    when (c) {
                        '#' -> "##"
                        'O' -> "[]"
                        '.' -> ".."
                        '@' -> "@."
                        else -> throw IllegalArgumentException("Unknown data")
                    }
                }.joinToString("").toCharArray())
            }
        }
        return linesList.toTypedArray() to commands
    }
}
