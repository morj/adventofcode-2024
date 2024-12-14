package dev.morj.adv24.day14

import dev.morj.adv24.lib.consumeInput

object Main2 {
    private const val w = 101
    private const val h = 103

    @JvmStatic
    fun main(args: Array<String>) {
        val robots = mutableListOf<Robot>()
        consumeInput("day-14") { _, line ->
            val (p, v) = line.split(" ")
            val (px, py) = p.removePrefix("p=").split(",").map { it.toInt() }
            val (vx, vy) = v.removePrefix("v=").split(",").map { it.toInt() }
            robots.add(Robot(px, py, vx, vy))
        }
        println("total robots count: ${robots.size}")
        for (step in 0..10000) {
            val points = getPoints(step, robots)
            for (x in 0..<w) {
                var metric = 0
                points.forEach { (t, _) ->
                    if (t.first == x) {
                        metric++
                    }
                }
                if (metric > 32) {
                    println("step = $step")
                    printPoints(points)
                }
            }
        }
    }

    private fun printPoints(points: MutableMap<Pair<Int, Int>, Int>) {
        for (y in 0..<h) {
            for (x in 0..<w) {
                val count = points[x to y]
                if (count == null) {
                    print(".")
                } else {
                    print(count)
                }
            }
            println()
        }
    }

    private fun getPoints(steps: Int, robots: MutableList<Robot>): MutableMap<Pair<Int, Int>, Int> {
        val result = mutableMapOf<Pair<Int, Int>, Int>()
        robots.forEach {
            val x = (it.px + steps * it.vx).mod(w)
            val y = (it.py + steps * it.vy).mod(h)
            val count = result[x to y] ?: 0
            result[x to y] = count + 1
        }
        return result
    }

    data class Robot(val px: Int, val py: Int, val vx: Int, val vy: Int)
}
