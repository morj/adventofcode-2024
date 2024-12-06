package dev.morj.adv24.day03

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

// 69968157 low
object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val doR = Regex("do\\(\\)")
        val dontR = Regex("don't\\(\\)")
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        var sum = 0L
        val doIndexes = mutableListOf<Int>()
        doIndexes.add(-1)
        val builder = StringBuilder()
        consumeInput("day-03") { _, line ->
            builder.append(line)
        }
        val line = builder.toString()
        doR.findAll(line).forEach { match ->
            doIndexes.add(match.range.first)
        }
        val dontIndexes = mutableListOf<Int>()
        dontR.findAll(line).forEach { match ->
            dontIndexes.add(match.range.first)
        }
        println(doIndexes)
        println(dontIndexes)
        regex.findAll(line).forEach { match ->
            val lastDo = doIndexes.filter { it <= match.range.first }.maxOrNull()!!
            val lastDont = dontIndexes.filter { it <= match.range.first }.maxOrNull()
            if (lastDont == null || lastDont < lastDo) {
                sum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        }
        println(sum)
    }
}
