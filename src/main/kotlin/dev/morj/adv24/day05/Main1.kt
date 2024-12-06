package dev.morj.adv24.day05

import dev.morj.adv24.lib.consumeInput

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val rules = mutableSetOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()
        consumeInput("day-05") { _, line ->
            if (line.contains("|")) {
                val (x, y) = line.split("|").map { it.toInt() }
                rules.add(x to y)
            } else {
                if (line.isNotBlank()) {
                    updates.add(line.split(",").map { it.toInt() })
                }
            }
        }
        // rules.forEach { println(it) }
        var sum = 0L
        updates.forEach {
            // println(it.joinToString(",", postfix = " ${validate(it, rules)}"))
            if (validate(it, rules)) {
                sum += it[it.size / 2]
            }
        }
        println(sum)
    }

    private fun validate(update: List<Int>, rules: MutableSet<Pair<Int, Int>>): Boolean {
        for (i in (0..<update.size)) {
            for (j in (i + 1..<update.size)) {
                // println(it[i] to it[j])
                if (rules.contains(update[j] to update[i])) {
                    return false
                }
            }
        }
        return true
    }
}
