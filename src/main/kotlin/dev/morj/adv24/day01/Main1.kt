package dev.morj.adv24.day01

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

object Main1 {
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
        println(l0.joinToString())
        println(l1.joinToString())
        println(l0.zip(l1) { x, y -> abs(x - y) }.sum())
    }
}
