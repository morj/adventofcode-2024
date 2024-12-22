package dev.morj.adv24.day22

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val numbers = javaClass.classLoader.getResource("day-22.txt")!!.readText().lines().map { it.toLong() }
        val keySet = mutableSetOf<String>()
        val allOutcomes = mutableSetOf<MutableMap<String, Int>>()
        numbers.forEach { initial ->
            val outcomes = mutableMapOf<String, Int>()
            var n = initial
            val data = mutableListOf<Long>()
            data.add(n)
            for (i in 0..<2000) {
                n = next(n)
                data.add(n)
            }
            data.windowed(5, 1).forEachIndexed { i, w ->
                val p = w.map { it.mod(10) }
                val d = p.zipWithNext { a, b -> b - a }
                val key = d.joinToString(",")
                // println(key)
                if (!outcomes.containsKey(key)) {
                    keySet.add(key)
                    outcomes[key] = p.last()
                }
            }
            allOutcomes.add(outcomes)
            // println(outcomes)
        }
        val resultKey = keySet.maxBy { key ->
            allOutcomes.sumOf { outcome -> outcome[key] ?: 0 }
        }
        println(resultKey)
        println(allOutcomes.sumOf { outcome -> outcome[resultKey] ?: 0 })
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
