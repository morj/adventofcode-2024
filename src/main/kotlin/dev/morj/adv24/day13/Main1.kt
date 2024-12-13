package dev.morj.adv24.day13

import dev.morj.adv24.lib.consumeInput

object Main1 {
    private const val pa = "Button A: X+"
    private const val pb = "Button B: X+"
    private const val pr = "Prize: X="

    @JvmStatic
    fun main(args: Array<String>) {
        val a = mutableListOf<Pair<Int, Int>>()
        val b = mutableListOf<Pair<Int, Int>>()
        val r = mutableListOf<Pair<Int, Int>>()
        consumeInput("day-13") { _, line ->
            if (line.startsWith(pa)) {
                val (x, y) = line.removePrefix(pa).split(", Y+").map { it.toInt() }
                a.add(x to y)
            } else if (line.startsWith(pb)) {
                val (x, y) = line.removePrefix(pb).split(", Y+").map { it.toInt() }
                b.add(x to y)
            } else if (line.startsWith(pr)) {
                val (x, y) = line.removePrefix(pr).split(", Y=").map { it.toInt() }
                r.add(x to y)
            }
        }
        val data = mutableListOf<Input>()
        a.forEachIndexed { i, a1 ->
            data.add(Input(a1, b[i], r[i]))
        }
        var result = 0L
        data.forEach { input ->
            println(input)
            val (ax, ay) = input.a
            val (bx, by) = input.b
            val (rx, ry) = input.r
            val matches = mutableListOf<Pair<Int, Int>>()
            for (i in 0..100) {
                val tx = rx - ax * i
                val ty = ry - ay * i
                if (tx > 0 && tx.mod(bx) == 0) {
                    val j = tx / bx
                    if (ty == j * by) {
                        matches.add(i to j)
                    }
                }
            }
            if (matches.isNotEmpty()) {
                val tokens = matches.minOf { it.first * 3 + it.second }
                println(matches.joinToString(prefix = "$$tokens ", separator = ", "))
                result += tokens
            }
        }
        println(result)
    }

    data class Input(val a: Pair<Int, Int>, val b: Pair<Int, Int>, val r: Pair<Int, Int>)
}
