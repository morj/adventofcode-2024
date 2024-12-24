package dev.morj.adv24.day24

import dev.morj.adv24.lib.consumeInput

object Main2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val nodes = mutableSetOf<String>()
        val gates = mutableListOf<Gate>()
        consumeInput("day-24-f") { _, line ->
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
                nodes.add(sx)
                nodes.add(sy)
                nodes.add(t)
                gates.add(Gate(x = sx, y = sy, op = op, t = t))
            }
        }
        gates.sortBy { it.op.toString() }
        // val v = nodes.filter { it.startsWith("x") || it.startsWith("y") }.associateWith { false }.toMutableMap()
        checkPlus1ViaY(gates)
        //checkPlus1ViaX(gates)
    }

    private fun checkPlus1ViaY(gates: MutableList<Gate>) {
        val used = mutableSetOf<String>()
        val v = mutableMapOf<String, Boolean>()
        v["y00"] = true
        // for (i in 0..44) { v["x${num(i)}"] = true }
        val tiers = mutableMapOf<Gate, Int>()
        for (i in 0..44) {
            println("processing bit $i")
            val n = num(i)
            v["x$n"] = true
            if (i > 0) {
                v["y$n"] = false
            }
            run(3, i, gates, v, mapOf("z$n" to false), used, tiers)
        }
        println()
        // tiers.entries.sortedBy { it.value }.forEach { println("${it.key.x} ${it.key.op} ${it.key.y} -> ${it.key.t}") }
    }

    private fun checkPlus1ViaX(gates: MutableList<Gate>) {
        val used = mutableSetOf<String>()
        val v = mutableMapOf<String, Boolean>()
        // v["x00"] = true
        for (i in 0..44) { v["y${num(i)}"] = false }
        val tiers = mutableMapOf<Gate, Int>()
        for (i in 0..44) {
            println("processing bit $i")
            val n = num(i)
            v["x$n"] = false
            // if (i > 0) { v["x$n"] = false }
            run(3, i, gates, v, mapOf("z$n" to false), used, tiers)
        }
        println()
        // tiers.entries.sortedBy { it.value }.forEach { println("${it.key.x} ${it.key.op} ${it.key.y} -> ${it.key.t}") }
    }

    /*private fun checkPlus1ViaX(gates: MutableList<Gate>) {
        val used = mutableSetOf<String>()
        val v = mutableMapOf<String, Boolean>()
        v["x00"] = true
        // for (i in 0..44) { v["x${num(i)}"] = true }
        for (i in 0..44) {
            println("processing bit $i")
            val n = num(i)
            v["y$n"] = true
            if (i > 0) {
                v["x$n"] = false
            }
            run(3, gates, v, mapOf("z$n" to false), used)
        }
    }*/

    private fun run(
        max: Int, step: Int, gates: MutableList<Gate>, values: MutableMap<String, Boolean>,
        desired: Map<String, Boolean>, out: MutableSet<String>, tier: MutableMap<Gate, Int>
    ) {
        val prevOut = out.toMutableSet()
        out.clear()
        // println("run for $gates")
        var i = 0
        var j = 0
        val ops = mutableMapOf<String, Int>()
        while (i++ < max) {
            val updates = mutableMapOf<String, Boolean>()
            gates.forEach { g ->
                val (x, y, op, t) = g
                val vx = values[x]
                val vy = values[y]
                if (vx != null && vy != null) {
                    val upd = op.apply(vx, vy)
                    if (!values.containsKey(t)) {
                        if(tier.containsKey(g)) { throw IllegalStateException("gate already used") }
                        ops[x] = j++
                        ops[y] = j++
                        tier[g] = step
                        if (!x.startsWith("x") && !x.startsWith("y") && !prevOut.contains(x) && !out.contains(x)) { println("must not use $x") }
                        if (!y.startsWith("x") && !y.startsWith("y") && !prevOut.contains(y) && !out.contains(y)) { println("must not use $y") }
                        println("gate $g updates to $upd")
                        updates[t] = upd
                        out.add(t)
                    }
                }
            }
            var hasUpdates = false
            updates.entries.forEach { (k, v) ->
                if (!values.containsKey(k)) {
                    // throw IllegalStateException("Override $k")
                    hasUpdates = true
                    values[k] = v
                }
            }
            if (!hasUpdates) {
                break
            }
        }
        println(gates.filter { tier[it] == step }.sortedBy { it.op.toString() }.joinToString { debugOut(ops, it) })
        desired.forEach { (x, v) ->
            if (values[x] != v) {
                println("bad value $x")
            }
        }
        println("vwr,z06,z11,tqm,z16,kfs,gfv,hcm".split(",").sorted().joinToString(","))
    }

    private fun debugOut(
        ops: MutableMap<String, Int>,
        it: Gate
    ): String {
        var x = ops[it.x]!!
        var y = ops[it.y]!!
        if (x > y) {
            val tmp = x
            x = y
            y = tmp
        }
        return "$x ${it.op} $y -> ${ops[it.t]}"
    }

    private fun num(i: Int): String {
        val n = if (i < 10) {
            "0$i"
        } else {
            i.toString()
        }
        return n
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
