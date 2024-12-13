package dev.morj.adv24.day13

import dev.morj.adv24.lib.consumeInput

object Main2 {
    private const val pa = "Button A: X+"
    private const val pb = "Button B: X+"
    private const val pr = "Prize: X="
    private const val extra = 10000000000000L
    // private const val extra = 0L

    @JvmStatic
    fun main(args: Array<String>) {
        val a = mutableListOf<Pair<Long, Long>>()
        val b = mutableListOf<Pair<Long, Long>>()
        val r = mutableListOf<Pair<Long, Long>>()
        consumeInput("day-13") { _, line ->
            if (line.startsWith(pa)) {
                val (x, y) = line.removePrefix(pa).split(", Y+").map { it.toLong() }
                a.add(x to y)
            } else if (line.startsWith(pb)) {
                val (x, y) = line.removePrefix(pb).split(", Y+").map { it.toLong() }
                b.add(x to y)
            } else if (line.startsWith(pr)) {
                val (x, y) = line.removePrefix(pr).split(", Y=").map { it.toLong() }
                r.add(x + extra to y + extra)
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

            // ax * i + bx * j = rx
            // ay * i + by * j = ry

            val p = rx * by - ry * bx
            val q = ax * by - ay * bx
            if (p.mod(q) == 0L) {
                val i = p / q
                val j = (rx - ax * i) / bx
                if (ax * i + bx * j == rx && ay * i + by * j == ry) {
                    println(i to j)
                    result += i * 3 + j
                }
            }
        }
        println(result)
    }

    data class Input(val a: Pair<Long, Long>, val b: Pair<Long, Long>, val r: Pair<Long, Long>)
}
