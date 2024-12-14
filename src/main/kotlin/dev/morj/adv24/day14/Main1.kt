package dev.morj.adv24.day14

import dev.morj.adv24.lib.consumeInput

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val robots = mutableListOf<Robot>()
        consumeInput("day-14") { _, line ->
            val (p, v) = line.split(" ")
            val (px, py) = p.removePrefix("p=").split(",").map { it.toInt() }
            val (vx, vy) = v.removePrefix("v=").split(",").map { it.toInt() }
            robots.add(Robot(px, py, vx, vy))
        }
        robots.forEach { println(it) }
        val w = 101
        val h = 103
        // val w = 11
        // val h = 7
        // debug(h, w, robots)
        val points = getPoints(100, h, w, robots)
        printPoints(h, w, points)
        var q0 = 0
        var q1 = 0
        var q2 = 0
        var q3 = 0
        points.forEach { (t, c) ->
            val (x, y) = t
            if (x < w / 2 && y < h / 2) { q0 += c }
            if (x < w / 2 && y > h / 2) { q1 += c }
            if (x > w / 2 && y < h / 2) { q2 += c }
            if (x > w / 2 && y > h / 2) { q3 += c }
        }
        println("$q0, $q1, $q2, $q3")
        println()
        println(q0 * q1 * q2 * q3)
    }

    private fun debug(h: Int, w: Int, robots: MutableList<Robot>) {
        for (step in 0..5) {
            //  val step = 2
            println("steps = $step")
            val points = getPoints(step, h, w, robots)
            printPoints(h, w, points)
            points.forEach { println(it) }
            println()
        }
    }

    private fun printPoints(h: Int, w: Int, points: MutableMap<Pair<Int, Int>, Int>) {
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

    private fun getPoints(steps: Int, h: Int, w: Int, robots: MutableList<Robot>): MutableMap<Pair<Int, Int>, Int> {
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
