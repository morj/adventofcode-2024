package dev.morj.adv24.day11

import dev.morj.adv24.lib.consumeInput
import kotlin.math.log10
import kotlin.math.pow

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val data = mutableListOf<Long>()
        consumeInput("day-11") { _, line ->
            data.addAll(line.split(" ").filter { it.isNotBlank() }.map { it.toLong() })
        }
        println(data.joinToString())
        var result = 0L
        val cache = hashMapOf<Key, Long>()
        data.forEach {
            result += count(it, cache, 75)
        }
        println(result)
    }

    private fun count(x: Long, cache: MutableMap<Key, Long>, depth: Int): Long {
        if (depth == 0) {
            // print("$x ")
            return 1
        }
        val key = Key(x, depth)
        cache[key]?.let {
            return it
        }
        val result = if (x == 0L) {
            count(1, cache, depth - 1)
        } else {
            val length = x.length()
            if (length.mod(2) == 0) {
                val shift = 10.toDouble().pow((length / 2).toDouble()).toLong()
                val upper = x / shift
                val lower = x.mod(shift)
                val l = count(upper, cache, depth - 1)
                l + count(lower, cache, depth - 1)
            } else {
                count(x * 2024, cache, depth - 1)
            }
        }
        cache[key] = result
        return result
    }

    private fun Long.length() = when (this) {
        0L -> 1
        else -> log10(toDouble()).toLong() + 1
    }

    private data class Key(val x: Long, val depth: Int)
}
