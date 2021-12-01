package day1

import java.io.File

fun main() {
    afternoon()
}

// find the three numbers which add up to 2020
// solution is their product
fun afternoon() {
    val total = 2020
    val inputFile = File("data/day1/input.txt")
    val numbers = inputFile.readLines().map { it.toInt() }
    numbers.forEach { n ->
        val rest = total - n
        // we might pass the current index and start from there
        val complementaryPair = getComplements(numbers, rest)
        // if a pair is found we have the solution
        complementaryPair?.let { pair ->
            println("the numbers are: ${n}, ${pair.first} and ${pair.second}")
            println("the result is: ${n * pair.first * pair.second}")
            return
        }
    }
}

// calculate a Pair? of Ints within the list which add up to the total
fun getComplements(list: List<Int>, total: Int): Pair<Int, Int>? {
    val complements = mutableSetOf<Int>()
    list.forEach {
        val complement = total - it
        if (complements.contains(it)) {
            return Pair(it, complement)
        } else {
            complements.add(complement)
        }
    }
    return null
}

// find the two numbers which add up to 2020
// solution is their product
fun morning() {
    val total = 2020
    val complements = mutableSetOf<Int>()
    val inputFile = File("data/day1/input.txt")
    inputFile.readLines().map { it.toInt() }
        .forEach {
            val complement = total - it
            if (complements.contains(it)) {
                println("the numbers are: ${it} and ${complement}")
                println("the result is: ${it * complement}")
                return
            } else {
                complements.add(complement)
            }
        }
}






