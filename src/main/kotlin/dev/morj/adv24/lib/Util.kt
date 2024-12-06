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
