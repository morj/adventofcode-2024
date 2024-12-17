package dev.morj.adv24.day17

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = javaClass.classLoader.getResource("day-17.txt")!!.readText().lines()
        val expected = lines[4].removePrefix("Program: ").split(",").map { it.toInt() }

        val data = readProgram(lines)
        println(data)
        val start = 0L
        var a = start
        val step = 1L
        while (a < Long.MAX_VALUE) {
            val program = Program(a, 0, 0, data)
            val max = expected.size
            val offset = program.run { i, x ->
                if (i == max - 1) {
                    println(a.toString(2))
                }
                i < max && expected[i] == x
            }
            if (max == offset) {
                println(a)
                break
            }
            a += step
        }
    }

    class Program(var a: Long, var b: Long, var c: Long, val data: List<Pair<Instruction, Int>>) {
        private var offset = 0
        private var p = 0

        fun run(out: (Int, Int) -> Boolean): Int {
            while (p < data.size) {
                val (instr, op) = data[p]
                when (instr) {
                    Instruction.ADV -> {
                        a = a.shr(op.combo.toInt())
                        p++
                    }

                    Instruction.BXL -> {
                        b = b.xor(op.literal)
                        p++
                    }

                    Instruction.BST -> {
                        b = op.combo.mod(8L)
                        p++
                    }

                    Instruction.JNZ -> {
                        if (a == 0L) {
                            p++
                        } else {
                            p = op.literal.toInt()
                        }
                    }

                    Instruction.BXC -> {
                        b = b.xor(c)
                        p++
                    }

                    Instruction.OUT -> {
                        if (!out(offset++, op.combo.mod(8))) {
                            return -1
                        }
                        p++
                    }

                    Instruction.BDV -> {
                        b = a.shr(op.combo.toInt())
                        p++
                    }

                    Instruction.CDV -> {
                        c = a.shr(op.combo.toInt())
                        p++
                    }
                }
            }
            return offset
        }

        private val Int.literal: Long get() = this.toLong()

        private val Int.combo: Long
            get() = when (this) {
                0 -> 0L
                1 -> 1L
                2 -> 2L
                3 -> 3L
                4 -> a
                5 -> b
                6 -> c
                else -> throw UnsupportedOperationException()
            }
    }

    enum class Instruction(val opcode: Int) {
        ADV(0),
        BXL(1),
        BST(2),
        JNZ(3),
        BXC(4),
        OUT(5),
        BDV(6),
        CDV(7);
    }

    private fun readProgram(lines: List<String>): List<Pair<Instruction, Int>> {
        val instr = Instruction.entries.associateBy { it.opcode }
        return lines[4].removePrefix("Program: ").split(",").chunked(2) {
            instr[it.first().toInt()]!! to it.last().toInt()
        }
    }
}
