package day3

import java.io.File

fun main() {
    morning()
    lunch()
}

// count the number of trees on tne slope through the wood
fun morning() {
    val inputFile = File("data/day3/input.txt")
    val woods = inputFile.readLines()
    val length = woods.first().length
    val slope = Pair(1, 3)
    val trees = woods.mapIndexed { index, s ->
        s[index * slope.second % length].equals('#')
    }.count { it }
    println(trees)
}

// helper method to count the trees for any given slope.
// don't like the skipping of lines via modulo but alas
fun countTreesForSlope(woods: List<String>, slope: Pair<Int, Int>) = woods.mapIndexed { index, s ->
    if (slope.first > 1) {
        if (index % slope.first != 0) {
            false
        } else {
            // here i'm calculating the position based on the index
            // this will be a puzzler by tomorrow
            s[index * slope.second / slope.first % woods.first().length].equals('#')
        }
    } else {
        s[index * slope.second % woods.first().length].equals('#')
    }
}.count { it }

// count the number of trees for various slopes and calculate the product
fun lunch() {
    val inputFile = File("data/day3/input.txt")
    val woods = inputFile.readLines()
    val slopes = listOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1))
    val trees = slopes.map {
        countTreesForSlope(woods, it)
    }
    val result = trees.fold(1.toBigInteger()) { product, item -> product * item.toBigInteger() }
    println(result)
}