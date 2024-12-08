package dev.morj.adv24.lib

fun Any.consumeInput(name: String, action: (Int, String) -> Unit) {
    javaClass.classLoader.getResource("$name.txt")!!.openStream().use { stream ->
        var index = 0
        stream.bufferedReader().forEachLine {
            action(index, it)
            index++
        }
    }
}

tailrec fun gcd(x: Long, y: Long): Long {
    return if (y == 0L) x else gcd(y, x % y)
}

fun lcm(numbers: LongArray): Long {
    return numbers.asSequence().fold(1) { x, y -> x * (y / gcd(x, y)) }
}

tailrec fun gcd(x: Int, y: Int): Int {
    return if (y == 0) x else gcd(y, x % y)
}

fun lcm(numbers: IntArray): Int {
    return numbers.asSequence().fold(1) { x, y -> x * (y / gcd(x, y)) }
}
