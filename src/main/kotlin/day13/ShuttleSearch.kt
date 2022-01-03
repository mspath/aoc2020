package day13

import java.io.File

fun main() {
    // breakfast()
    lunch()
}

fun breakfast() {
    val input = File("data/day13/input.txt").readLines()
    val timestamp = input.first().toInt()
    val buses = input.last().split(',').filterNot { it == "x" }.map { it.toInt() }
    val schedule = buses.map {
        Pair(it, firstStopAfter(it, timestamp))
    }
    val firstBus = schedule.sortedBy { it.second }.first()
    val solution = firstBus.first * (firstBus.second - timestamp)
    println(solution)
}

fun firstStopAfter(bus: Int, timestamp: Int): Int {
    var time = 0
    while (true) {
        time += bus
        if (time >= timestamp) return time
    }
}

fun lunch() {
    val input = File("data/day13/input.txt").readLines()
    val buses = input.last().split(',').mapIndexedNotNull { index, s ->
        if (s != "x") {
            Pair(s.toInt(), index)
        }
        else null
    }
    println(buses)
    val departures = buses.map { bus ->
        val schedules = (0..1000).filter { it % bus.first == 0 }.map { it + bus.second }
        Pair(bus.first, schedules)
    }
    println(departures)
}