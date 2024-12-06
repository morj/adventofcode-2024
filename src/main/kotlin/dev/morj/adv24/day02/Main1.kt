package dev.morj.adv24.day02

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val levels = mutableListOf<MutableList<Int>>()
        consumeInput("day-02") { index, line ->
            levels.add(line.split(" ").map { it.toInt() }.toMutableList())
        }
        levels.forEach {
            println(it.toString())
        }
        val result = levels.count {
            isOK(it)
        }
        println(result)
    }

    fun isOK(it: List<Int>): Boolean {
        var ups = 0
        var downs = 0
        it.zipWithNext { a, b ->
            val distance = abs(a - b)
            if (distance in 1..3) {
                if (a > b) {
                    downs++
                }
                if (a < b) {
                    ups++
                }
            }
        }
        return (ups == 0 && downs == it.size - 1) || (downs == 0 && ups == it.size - 1)
    }
}
