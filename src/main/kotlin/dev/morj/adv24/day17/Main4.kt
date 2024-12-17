package dev.morj.adv24.day17

object Main4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = javaClass.classLoader.getResource("day-17.txt")!!.readText().lines()

        // numbers fetched by brute-forcing first and second half of the target output (like in Main2)
        println(23948989L.toString(2))
        println(9807417L.toString(2))

        val a = (9807417L - 1).shl(3 * 8) + 23948989L

        val program = Program(a, 0, 0, readProgram(lines))

        println(program.data)

        val out = mutableListOf<Int>()

        program.run { _, i -> out.add(i) }

        println(out.joinToString(","))

        println(a)
        println(a.toString(2))
    }

    class Program(var a: Long, var b: Long, var c: Long, val data: List<Pair<Instruction, Int>>) {
        var offset = 0
        var p = 0

        fun run(out: (Int, Int) -> Unit) {
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
                        out(offset++, op.combo.mod(8))
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
        }

        private val Int.literal: Long get() = this.toLong()

        private val Int.combo: Long
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
