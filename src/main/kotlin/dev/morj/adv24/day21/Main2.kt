package dev.morj.adv24.day21

import kotlin.math.abs
import kotlin.math.sign

object Main2 {
    private val numeric = Pad(
        mapOf(
            '7' to 0, '8' to 0, '9' to 0,
            '4' to 1, '5' to 1, '6' to 1,
            '1' to 2, '2' to 2, '3' to 2,
            '0' to 3, 'A' to 3
        ),
        mapOf(
            '7' to 0, '4' to 0, '1' to 0,
            '8' to 1, '5' to 1, '2' to 1, '0' to 1,
            '9' to 2, '6' to 2, '3' to 2, 'A' to 2,
        )
    )

    private val directional = Pad(
        mapOf('^' to 0, 'A' to 0, '<' to 1, 'v' to 1, '>' to 1),
        mapOf('<' to 0, '^' to 1, 'v' to 1, 'A' to 2, '>' to 2),
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = javaClass.classLoader.getResource("day-21.txt")!!.readText().lines()
        var result = 0L
        lines.forEach { code ->
            var cost = Long.MAX_VALUE
            val cache = mutableMapOf<Pair<String, Int>, Long>()
            allPresses(numeric, code, 'A', "") {
                val r = allPressesQ(directional, it, 'A', cache, 0, "")
                if (r < cost) { cost = r }
            }
            val n = code.replace("A", "").toInt()
            println("cost for $code: $cost * $n")
            result += cost * n
        }
        println("total cost: $result")
    }

    private fun allPressesQ(
        pad: Pad, code: String, start: Char, cache: MutableMap<Pair<String, Int>, Long>,
        depth: Int, acc: String
    ) : Long {
        cache[code to depth]?.let { return it }
        if (code.length == 1 && code != "A") { throw IllegalArgumentException("Bad code $code") }
        if (depth >= 25) { return code.length.toLong() }
        if (!code.contains("A")) { throw IllegalArgumentException() }
        var result = 0L
        val split = code.split("A")
        split.forEachIndexed { index, s ->
            if (index != split.size - 1) {
                var r = Long.MAX_VALUE
                val command = s + "A"
                val presses = mutableListOf<String>()
                allPresses(pad, command, start, "") { presses.add(it) }
                presses.forEach {
                    val q = allPressesQ(pad, it, start, cache, depth + 1, acc)
                    if (q < r) r = q
                    if (r == Long.MAX_VALUE) { throw IllegalStateException() }
                }
                result += r
            }
        }
        if (result == 0L) { throw IllegalStateException() }
        cache[code to depth] = result
        return result
    }

    private fun allPresses(pad: Pad, line: String, start: Char, acc: String, out: (String) -> Unit) {
        if (line.isNotEmpty()) {
            val next = line.first()
            pad.presses(start, next).forEach { press ->
                allPresses(pad, line.drop(1), next, acc + press + 'A', out)
            }
        } else {
            out(acc)
        }
    }

    class Pad(private val rows: Map<Char, Int>, private val cols: Map<Char, Int>) {
        private val indices = rows.keys.associateBy { rows[it]!! to cols[it]!! }

        fun presses(from: Char, to: Char): List<String> {
            val tx = rows[to]!!
            val ty = cols[to]!!
            val fx = rows[from]!!
            val fy = cols[from]!!
            val dxa = tx - fx
            val dya = ty - fy

            val c1 = if (dxa < 0) "^" else "v"
            val c2 = if (dya < 0) "<" else ">"

            return if (dxa != 0 && dya != 0) {
                val result = mutableListOf<String>()
                val n1 = (fx + dxa.sign) to fy
                indices[n1]?.let { next ->
                    result.addAll(presses(next, to).map { c1 + it })
                }
                val n2 = fx to (fy + dya.sign)
                indices[n2]?.let { next ->
                    result.addAll(presses(next, to).map { c2 + it })
                }
                result
            } else if (dya == 0) {
                listOf(c1.repeat(abs(dxa)))
            } else {
                listOf(c2.repeat(abs(dya)))
            }
        }
    }
}
