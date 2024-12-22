package dev.morj.adv24.day22

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val numbers = javaClass.classLoader.getResource("day-22.txt")!!.readText().lines().map { it.toLong() }
        var result = 0L
        numbers.forEach {
            var n = it
            for (i in 0..<2000) { n = next(n) }
            result += n
        }
        println(result)
    }

    fun next(number: Long): Long {
        var n = number
        n = mix(n, n * 64)
        n = prune(n)
        n = mix(n, n / 32)
        n = prune(n)
        n = mix(n, n * 2048)
        n = prune(n)
        return n
    }

    private fun mix(n: Long, m: Long): Long {
        return n.xor(m)
    }

    private fun prune(n: Long): Long {
        return n.mod(16777216L)
    }
}
