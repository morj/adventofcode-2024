package dev.morj.adv24.day19

import dev.morj.adv24.lib.consumeInput

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val towels = mutableListOf<String>()
        val targets = mutableListOf<String>()
        consumeInput("day-19") { index, line ->
            if (index == 0) {
                towels.addAll(line.split(", ").filter { it.isNotEmpty() })
            } else if (index > 1) {
                targets.add(line)
            }
        }
        println(towels)
        println(targets.joinToString("\n"))
        if (towels.distinct().size != towels.size) throw IllegalArgumentException("Towels must be distinct")
        val maxChunk = towels.maxOf { it.length }
        println("max chunk: $maxChunk")
        val result = targets.sumOf { target ->
            match(target, 0, maxChunk, towels, hashMapOf()).also { println(it) }
        }
        println(result)
    }

    private fun match(
        target: String, offset: Int, maxChunk: Int, towels: List<String>, cache: MutableMap<Pair<String, Int>, Long>
    ): Long {
        cache[target to offset]?.let {
            return it
        }
        val chunk = target.substring(offset, (offset + maxChunk).coerceAtMost(target.length))
        var result = 0L
        towels.forEach { towel ->
            if (chunk.startsWith(towel)) {
                val newOffset = offset + towel.length
                if (newOffset == target.length) {
                    result++
                } else {
                    result += match(target, newOffset, maxChunk, towels, cache)
                }
            }
        }
        cache[target to offset] = result
        return result
    }
}
