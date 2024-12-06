package dev.morj.adv24.day01

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val l0 = arrayListOf<Int>()
        val l1 = arrayListOf<Int>()
        consumeInput("day-01") { index, line ->
            val (x, y) = line.split(" ").filter { it.isNotEmpty() }.map { s -> s.toInt() }
            l0.add(x)
            l1.add(y)
        }
        l0.sort()
        l1.sort()
        val reps = mutableMapOf<Int, Int>()
        l1.forEach {
            reps[it] = 1
        }
        l1.zipWithNext().forEach { (l0, l1) ->
            if (l0 == l1) {
                reps[l0] = reps[l0]!! + 1
            }
        }
        reps.forEach { println(it) }
        println(l0.joinToString())
        println(l1.joinToString())
        println(l0.sumOf { it * (reps[it] ?: 0) })
    }
}
