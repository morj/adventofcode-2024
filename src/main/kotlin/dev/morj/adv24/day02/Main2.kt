package dev.morj.adv24.day02

import dev.morj.adv24.lib.consumeInput
import kotlin.math.abs

object Main2 {
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
            if (Main1.isOK(it)) {
                true
            } else {
                var result = false
                for (i in 0..it.size-1) {
                    if (Main1.isOK(it.filterIndexed { index, _ -> index != i })) {
                        result = true
                        break
                    }
                }
                result
            }
        }
        println(result)
    }
}
