package dev.morj.adv24.day05

import dev.morj.adv24.lib.consumeInput

object Main2 {
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
        val deps = rules.groupBy { it.first }.map { it.key to it.value.map { it.second } }.toMap()
        // deps.forEach { (key, value) -> println("$key -> " + value.joinToString()) }
        updates.forEach {
            // println(it.joinToString(",", postfix = " ${validate(it, rules)}"))
            if (!validate(it, rules)) {
                val relevant = it.toSet()
                val fixed = topologicalSort(it) { x -> deps[x]?.filter { relevant.contains(it) } ?: listOf() }
                println(fixed.joinToString())
                if (!validate(fixed, rules)) {
                    throw RuntimeException()
                } else {
                    sum += fixed[fixed.size / 2]
                }
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

    private fun <T> topologicalSort(items: Iterable<T>, dependencies: (T) -> Iterable<T>): List<T> {
        val wip = HashSet<T>()
        val done = HashSet<T>()
        val result = ArrayList<T>()

        fun visit(item: T) {
            if (done.contains(item)) {
                return
            }

            if (wip.contains(item)) {
                throw IllegalStateException()
            }

            wip.add(item)

            for (dependency in dependencies(item)) {
                visit(dependency)
            }

            wip.remove(item)
            done.add(item)
            result.add(item)
        }

        for (item in items) {
            visit(item)
        }

        return result.reversed()
    }
}
