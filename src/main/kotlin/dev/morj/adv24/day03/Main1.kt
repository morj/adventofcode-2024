package dev.morj.adv24.day03

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        var sum = 0L
        consumeInput("day-03") { index, line ->
            regex.findAll(line).forEach { match ->
                sum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        }
        println(sum)
    }
}
