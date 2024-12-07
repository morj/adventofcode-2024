package dev.morj.adv24.day07

import dev.morj.adv24.lib.consumeInput
import kotlin.math.log10
import kotlin.math.pow

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val eqs = mutableListOf<Eq>()
        consumeInput("day-07") { _, line ->
            val t = line.substringBefore(":").toLong()
            val d = line.substringAfter(":").split(" ").filter {
                it.isNotBlank()
            }.map {
                it.toLong()
            }
            eqs.add(Eq(t, d))
        }
        var result = 0L
        eqs.forEach {
            println(it)
            val valid = it.valid()
            println(valid)
            if (valid) {
                result += it.t
            }
        }
        println(result)
    }

    data class Eq(val t: Long, val d: List<Long>) {
        fun valid(): Boolean {
            val q = d[d.size - 1]
            if (d.size == 1) {
                return t == q
            }

            val n = d.dropLast(1)
            val v = Eq(t - q, n).valid()
            val shift = 10.toDouble().pow(q.length().toDouble()).toLong()
            return v || (t.mod(q) == 0L && Eq(t / q, n).valid()) || (t.mod(shift) == q && Eq(t / shift, n).valid())
        }
    }

    fun Long.length() = when(this) {
        0L -> 1
        else -> log10(toDouble()).toLong() + 1
    }
}
