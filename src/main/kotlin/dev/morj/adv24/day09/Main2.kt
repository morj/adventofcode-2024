package dev.morj.adv24.day09

import dev.morj.adv24.lib.consumeInput
import java.util.*

object Main2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val freeSpace = TreeMap<Int, Int>()
        val blocks = TreeMap<Int, Block>() // block position to id
        consumeInput("day-09") { _, line ->
            var position = 0
            line.mapIndexed { idx, char ->
                val blockSize = char.digitToInt()
                if (idx % 2 == 0) {
                    blocks[position] = Block(idx / 2, blockSize)
                } else {
                    if (blockSize > 0) {
                        freeSpace[position] = blockSize
                    }
                }
                position += blockSize
            }
        }
        val move = blocks.entries.reversed()
        move.forEach { (position, block) ->
            var e: Map.Entry<Int, Int>? = null
            for (dest in freeSpace) {
                if (dest.value >= block.size) {
                    e = dest
                    break
                }
            }
            if (e != null && e.key < position) {
                val newPosition = e.key
                val freeSpaceSize = e.value
                freeSpace.remove(newPosition)
                blocks.remove(position)
                if (freeSpaceSize > block.size) {
                    freeSpace[newPosition + block.size] = freeSpaceSize - block.size
                }
                blocks[newPosition] = block
            }
        }
        // printAll(blocks)
        var result = 0L
        blocks.forEach { (k, v) ->
            for (i in 0..<v.size) {
                result += (k + i) * v.id
            }
        }
        println()
        println(result)
    }

    private fun printAll(blocks: TreeMap<Int, Block>) {
        for (i in 0..blocks.lastKey()) {
            blocks[i]?.let { b ->
                repeat(b.size) {
                    print(b.id)
                }
            } ?: run {
                print(".")
            }
        }
        println()
    }

    data class Block(val id: Int, val size: Int)
}
