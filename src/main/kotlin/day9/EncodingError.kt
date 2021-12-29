package day9

import java.io.File

fun main() {
    val windowSample = 5
    val inputSample = File("data/day9/sample.txt").readLines().map { it.toLong() }
    val window = 25
    val input = File("data/day9/input.txt").readLines().map { it.toLong() }
    val xmas = breakfast(input, window)
    println(xmas)
    val result = lunch(input, xmas)
    println(result)
}

/**
 * checks whether a pair of previous numbers adds up to the last number of the list
 */
fun addsUp(list: List<Long>): Boolean {
    val window = list.dropLast(1)
    val value = list.last()
    val complements = window.associateBy { value - it }.filter { it.key > 0 }.filter { it.key != it.value }
    return window.any { complements.containsKey(it) }
}

/**
 * find the first number in a list which does not add up from previous numbers in a given window
 */
fun breakfast(input: List<Long>, window: Int): Long {
    input.windowed(window + 1).forEach {
        val addsUp = addsUp(it)
        if (!addsUp) {
            println("not addsUp: ${it.last()}")
            return it.last()
        }
    }
    return Long.MIN_VALUE
}

fun lunch(input: List<Long>, xmas: Long): Long {
    val queue: MutableList<Long> = mutableListOf()
    for (i in input) {
        queue.add(i)
        if (queue.sum() == xmas) {
            return queue.sorted().first() + queue.sorted().last()
        } else {
            while (queue.sum() > xmas) {
                queue.removeAt(0)
                if (queue.sum() == xmas) {
                    return queue.sorted().first() + queue.sorted().last()
                }
            }
        }
    }
    return Long.MIN_VALUE
}