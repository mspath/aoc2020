package day1

import java.io.File

// for scale the more elegant solution from jetbrain's svtk
// see https://www.youtube.com/watch?v=o4emra1xm88

fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    val complements = associateBy { sum - it }
    return firstNotNullOfOrNull { number ->
        val complement = complements[number]
        if (complement != null) Pair(number, complement) else null
    }
}

fun List<Int>.findTripleOfSum(): Triple<Int, Int, Int>? =
    firstNotNullOfOrNull { x ->
        findPairOfSum(2020 - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }

fun main1() {
    val numbers = File("data/day1/input.txt")
        .readLines()
        .map { it.toInt() }
    val pair = numbers.findPairOfSum(2020)

    println(pair)
    println(pair?.let { (a, b) -> a * b })

    val triple = numbers.findTripleOfSum()
    println(triple)
    println(triple?.let { (x, y, z) -> x * y * z })
}