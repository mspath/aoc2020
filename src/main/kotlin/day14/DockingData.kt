package day14

import java.io.File
import kotlin.math.pow

fun main() {
    breakfast()
}

fun mask1(value: Long, mask: Long) = value or mask

fun mask0(value: Long, mask: Long): Long {
    // via specs
    val length = 35
    var result = value
    for (i in 0 until length) {
        if (mask and 2.0.pow(i).toLong() != 0L) {
            if (result and 2.0.pow(i).toLong() != 0L) {
                result -= 2.0.pow(i).toLong()
            }
        }
    }
    return result
}

fun mask(value: Long, mask: String): Long {
    val mask1 = mask.map { if (it == '1') '1' else '0' }.joinToString("").toLong(2)
    val mask0 = mask.map { if (it == '0') '1' else '0' }.joinToString("").toLong(2)
    return mask0(mask1(value, mask1), mask0)
}

fun breakfast() {
    var mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    val map: MutableMap<Int, Long> = mutableMapOf()
    val input = File("data/day14/input.txt").readLines()
    for (next in input) {
        println(next)
        if (next.startsWith("mask")) {
            mask = next.substringAfter("mask = ")
        }
        else if (next.startsWith("mem")) {
            val mem = next.substringAfter("mem[").substringBefore("]").toInt()
            val value = next.substringAfter(" = ").toLong()
            map.put(mem, mask(value, mask))
        }
    }
    println(map)
    println(map.values.sumOf { it })
}