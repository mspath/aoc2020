package day10

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

fun breakfast() {
    val input = File("data/day10/input.txt").readLines().map { it.toInt() }.toMutableList()
    val start = 0
    val end = input.maxOf { it } + 3
    input.add(start)
    input.add(end)
    val adapters = input.sorted()
    val diff = adapters.windowed(2).map { it.last() - it.first() }.groupingBy { it }.eachCount()
    val ones = diff.getOrDefault(1, 0)
    val threes = diff.getOrDefault(3, 0)
    val solution = ones * threes
    println(solution)
}

fun longestSequenceOfOnes(list: List<Int>): Int {
    var max = 0
    var streak = 0
    for (i in list) {
        when(i) {
            1 -> {
                streak++
                if (streak > max) max = streak
            }
            else -> {
                streak = 0
            }
        }
    }
    return max
}

fun lunch() {
    val input = File("data/day10/sample.txt").readLines().map { it.toInt() }.toMutableList()
    val start = 0
    val end = input.maxOf { it } + 3
    input.add(start)
    input.add(end)
    val adapters = input.sorted()
    println(adapters)
    val diff = adapters.windowed(2).map { it.last() - it.first() }.reversed()
    println(diff)
    println(longestSequenceOfOnes(diff))
}