package day5

import java.io.File

fun main() {
    breakfast()
    lunch()
}

// the task is to find the one missing id
fun lunch() {
    val inputFile = File("data/day5/input.txt")
    val passes = inputFile.readLines().map {
        val row = it.substring(0, 7).replace("B", "1").replace("F", "0")
        val col = it.substring(7, 10).replace("R", "1").replace("L", "0")
        val id = Integer.parseInt(row, 2) * 8 + Integer.parseInt(col, 2)
        id
    }.sorted()
    val minId = passes.first()
    val maxId = passes.last()
    // this already worked for me without further checks.
    for (i in minId until maxId) {
        if (i !in passes) {
            println(i)
        }
    }
}

// find the highest id for a boarding pass
// b/f and r/l can be mapped to 1 resp 0 binary
fun breakfast() {
    val inputFile = File("data/day5/input.txt")
    val passes = inputFile.readLines()
    val max = passes.maxOf {
        val row = it.substring(0, 7).replace("B", "1").replace("F", "0")
        val col = it.substring(7, 10).replace("R", "1").replace("L", "0")
        val id = Integer.parseInt(row, 2) * 8 + Integer.parseInt(col, 2)
        id
    }
    println(max)
}

