package dev.morj.adv24.day17

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = javaClass.classLoader.getResource("day-17.txt")!!.readText().lines()
        val a = lines[0].removePrefix("Register A: ").toInt()
        val b = lines[1].removePrefix("Register B: ").toInt()
        val c = lines[2].removePrefix("Register C: ").toInt()
        val program = Program(a, b, c, readProgram(lines))

        println(program.data)

        val out = mutableListOf<Int>()

        program.run { _, i -> out.add(i) }

        println(out.joinToString(","))
    }

    class Program(var a: Int, var b: Int, var c: Int, val data: List<Pair<Instruction, Int>>) {
        var offset = 0
        var p = 0

        fun run(out: (Int, Int) -> Unit) {
            while (p < data.size) {
                val (instr, op) = data[p]
                when (instr) {
                    Instruction.ADV -> {
                        a = a.shr(op.combo)
                        p++
                    }

                    Instruction.BXL -> {
                        b = b.xor(op.literal)
                        p++
                    }

                    Instruction.BST -> {
                        b = op.combo.mod(8)
                        p++
                    }

                    Instruction.JNZ -> {
                        if (a == 0) {
                            p++
                        } else {
                            p = op.literal
                        }
                    }

                    Instruction.BXC -> {
                        b = b.xor(c)
                        p++
                    }

                    Instruction.OUT -> {
                        out(offset++, op.combo.mod(8))
                        p++
                    }

                    Instruction.BDV -> {
                        b = a.shr(op.combo)
                        p++
                    }

                    Instruction.CDV -> {
                        c = a.shr(op.combo)
                        p++
                    }
                }
            }
        }

        private val Int.literal: Int get() = this

        private val Int.combo: Int
            get() = when (this) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
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
