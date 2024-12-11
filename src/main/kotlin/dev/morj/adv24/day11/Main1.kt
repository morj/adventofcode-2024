package dev.morj.adv24.day11

import dev.morj.adv24.lib.consumeInput
import kotlin.math.log10
import kotlin.math.pow

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = mutableListOf<Long>()
        consumeInput("day-11") { _, line ->
            data.addAll(line.split(" ").filter { it.isNotBlank() }.map { it.toLong() })
        }
        println(data.joinToString())
        var result = 0L
        data.forEach {
            result += count(it, 25)
        }
        println(result)
    }

    private fun count(x: Long, depth: Int): Long {
        if (depth == 0) {
            // print("$x ")
            return 1
        }
        if (x == 0L) {
            return count(1, depth - 1)
        }
        val length = x.length()
        if (length.mod(2) == 0) {
            val shift = 10.toDouble().pow((length / 2).toDouble()).toLong()
            val upper = x / shift
            val lower = x.mod(shift)
            val l = count(upper, depth - 1)
            return l + count(lower, depth - 1)
        }
        return count(x * 2024, depth - 1)
    }

    private fun Long.length() = when(this) {
        0L -> 1
        else -> log10(toDouble()).toLong() + 1
    }
}
