package dev.morj.adv24.day24

import dev.morj.adv24.lib.consumeInput

object Main1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val values = mutableMapOf<String, Boolean>()
        val interesting = mutableSetOf<String>()
        val gates = mutableListOf<Gate>()
        consumeInput("day-24") { _, line ->
            if (line.contains("->")) {
                val (s0, t) = line.split(" -> ")
                val op: OP
                val (sx, sy) = if (s0.contains(" AND ")) {
                    op = OP.AND
                    s0.split(" AND ")
                } else if (s0.contains(" XOR ")) {
                    op = OP.XOR
                    s0.split(" XOR ")
                } else {
                    op = OP.OR
                    s0.split(" OR ")
                }
                if (sx.startsWith("z")) {
                    interesting.add(sx)
                }
                if (sy.startsWith("z")) {
                    interesting.add(sy)
                }
                if (t.startsWith("z")) {
                    interesting.add(t)
                }
                gates.add(Gate(x = sx, y = sy, op = op, t = t))
            } else if (line.isNotEmpty()) {
                val (x, v) = line.split(": ")
                values[x] = v == "1"
                if (x.startsWith("z")) {
                    interesting.add(x)
                }
            }
        }
        println(values)
        println(gates)
        println(interesting.sorted())
        run(gates, values, interesting)
    }

    private fun run(
        gates: MutableList<Gate>,
        values: MutableMap<String, Boolean>,
        interesting: MutableSet<String>
    ): Long {
        while (true) {
            val updates = mutableMapOf<String, Boolean>()
            gates.forEach { g ->
                val (x, y, op, t) = g
                val vx = values[x]
                val vy = values[y]
                if (vx != null && vy != null) {
                    updates[t] = op.apply(vx, vy)
                }
            }
            updates.entries.forEach { (k, v) ->
                if (!values.containsKey(k)) {
                    // throw IllegalStateException("Override $k")
                    values[k] = v
                }
            }
            if (interesting.all { values.containsKey(it) }) {
                var result = 0L
                var bit = 1L
                // val bits = interesting.sorted().map { if (values[it]!!) 1 else 0 }
                // println(bits.joinToString(""))
                interesting.sorted().forEach {
                    if (values[it]!!) {
                        result += bit
                    }
                    bit *= 2
                }
                // values.keys.sorted().forEach { println("$it: ${values[it]}") }
                println("gotcha: $result")
                return result
            }
        }
    }

    data class Gate(val x: String, val y: String, val op: OP, val t: String)

    sealed class OP {
        data object AND : OP() {
            override fun apply(x: Boolean, y: Boolean): Boolean = x and y
        }

        data object OR : OP() {
            override fun apply(x: Boolean, y: Boolean): Boolean = x or y
        }

        data object XOR : OP() {
            override fun apply(x: Boolean, y: Boolean): Boolean = x xor y
        }

        abstract fun apply(x: Boolean, y: Boolean): Boolean
    }
}
