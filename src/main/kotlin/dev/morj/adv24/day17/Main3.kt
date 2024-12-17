package dev.morj.adv24.day17

object Main3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = javaClass.classLoader.getResource("day-17.txt")!!.readText().lines()
        val expected = lines[4].removePrefix("Program: ").split(",").map { it.toInt() }.toTypedArray()

        // val step = 0b10111101
        val step = 1
        val max = expected.size
        check(0L, max, expected, step)
    }

    private fun check(start: Long, max: Int, expected: Array<Int>, step: Int) {
        var a = start
        while (a < Long.MAX_VALUE) {
            val offset = run(a, max, expected)
            if (max == offset) {
                println(a)
                break
            }
            a += step
        }
    }

    private fun run(startA: Long, max: Int, expected: Array<Int>): Int {
        var a = startA
        var b: Long
        var c: Long
        var i = 0
        do {
            // (BST, 4)
            b = a.mod(8L)
            // (BXL, 1)
            b = b.xor(1L)
            // (CDV, 5)
            c = a.shr(b.toInt())
            // (BXL, 5)
            b = b.xor(5L)
            // (BXC, 0)
            b = b.xor(c)
            // (ADV, 3)
            a = a.shr(3)
            // (OUT, 5)
            if (i > 9) { println(startA.toString(2)) }
            if (i >= max || expected[i] != b.mod(8)) {
                break
            }
            i++
        } while (a > 0)

        return i
    }
}
