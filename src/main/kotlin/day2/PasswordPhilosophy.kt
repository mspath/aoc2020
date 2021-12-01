package day2

import java.io.File

fun main() {
    morning()
    lunch()
}

// check passwords for correct position
fun lunch() {
    val passwordRegex = """(\d+)-(\d+) ([a-z]):\s([a-z]+)""".toRegex()
    val inputFile = File("data/day2/input.txt")
    val passwords = inputFile.readLines()
    val valid = passwords.count {
        try {
            val (a, b, c, d) = passwordRegex.find(it)!!.destructured
            val cInA = c[0] == d[a.toInt() - 1]
            val cInB = c[0] == d[b.toInt() - 1]
            cInA xor cInB
        } catch (e: Exception) {
            false
        }
    }
    println(valid)
}

// check passwords for correct number of doccurrences
fun morning() {
    val passwordRegex = """(\d+)-(\d+) ([a-z]):\s([a-z]+)""".toRegex()
    val inputFile = File("data/day2/input.txt")
    val passwords = inputFile.readLines()
    val valid = passwords.count {
        try {
            val (a, b, c, d) = passwordRegex.find(it)!!.destructured
            val occurences = d.filter { it == c[0] }.length
            occurences in a.toInt()..b.toInt()
        } catch (e: Exception) {
            false
        }
    }
    println(valid)
}

