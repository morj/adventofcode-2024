package dev.morj.adv24.day09

import dev.morj.adv24.lib.consumeInput
import java.util.*

object Main1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val blocks = TreeMap<Int, Int>() // block position to id
        consumeInput("day-09") { _, line ->
            var position = 0
            line.mapIndexed { idx, char ->
                val blockSize = char.digitToInt()
                if (idx % 2 == 0) {
                    val blockId = idx / 2
                    for (i in position..<position + blockSize) {
                        blocks[i] = blockId
                    }
                }
                position += blockSize
            }
        }
        val freeSpace = TreeSet<Int>()
        for (i in 0..blocks.lastKey()) {
            if (blocks[i] == null) {
                freeSpace.add(i)
            }
        }
        printAll(blocks)
        val move = blocks.entries.reversed()
        move.forEach { (k, v) ->
            freeSpace.pollFirst()?.let { free ->
                if (free < k) {
                    freeSpace.add(k)
                    blocks.remove(k)
                    blocks[free] = v
                }
            }
        }
        printAll(blocks)
        var result = 0L
        blocks.forEach { (k, v) ->
            result += k * v
        }
        println(result)
    }

    private fun printAll(blocks: TreeMap<Int, Int>) {
        for (i in 0..blocks.lastKey()) {
            blocks[i]?.let {
                print(it)
            } ?: run {
                print(".")
            }
        }
        println()
    }
}
