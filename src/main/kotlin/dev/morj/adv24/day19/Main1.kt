package dev.morj.adv24.day19

import dev.morj.adv24.lib.consumeInput

object Main1 {
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
        val result = targets.count {
            match(it, 0, maxChunk, towels, mutableSetOf())/*.also { println(it) }*/
        }
        println(result)
    }

    private fun match(
        target: String, offset: Int, maxChunk: Int, towels: List<String>, used: MutableSet<String>
    ): Boolean {
        val chunk = target.substring(offset, (offset + maxChunk).coerceAtMost(target.length))
        towels.forEach { towel ->
            if (chunk.startsWith(towel)) {
                val newOffset = offset + towel.length
                if (newOffset == target.length) {
                    return true
                }
                if (match(target, newOffset, maxChunk, towels, used)) {
                    return true
                }
            }
        }
        return false
    }
}
