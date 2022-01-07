package day16

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

data class Section(val name: String, val low: IntRange, val high: IntRange) {
    fun contains(value: Int) = value in low || value in high
}

data class Ticket(val features: List<Int>) {


}

fun String.toIntRange(): IntRange {
    val from = this.substringBefore('-').toInt()
    val to = this.substringAfter('-').toInt()
    return IntRange(from, to)
}

fun String.toTicket() = Ticket(this.split(',').map { it.toInt() })

fun List<Ticket>.getValuesAtIndex(index: Int) = this.map { ticket ->
    ticket.features[index]
}

fun breakfast() {
    val input = File("data/day16/input.txt").readText()
    val categories = input.substringBefore("your ticket:").trim().split("\n")
    val nearby = input.substringAfter("nearby tickets:").trim().split("\n")
    val sections = categories.map {
        val name = it.substringBefore(": ")
        val low = it.substringAfter(": ")
            .substringBefore(" or ").trim().toIntRange()
        val high = it.substringAfter(" or ").trim().toIntRange()
        Section(name, low, high)
    }
    var error = 0
    // add up values not in ranges of sections
    for (next in nearby) {
        val values = next.split(',').map { it.toInt() }
        values.forEach { value ->
            if (sections.none { it.contains(value) }) error += value
        }
    }
    println(error)
}

fun lunch() {
    val input = File("data/day16/input.txt").readText()
    val categories = input.substringBefore("your ticket:").trim().split("\n")
    val ticket = input.substringAfter("your ticket:")
        .substringBefore("nearby tickets:").trim().toTicket()
    val nearby = input.substringAfter("nearby tickets:").trim().split("\n")
    val sections = categories.map {
        val name = it.substringBefore(": ")
        val low = it.substringAfter(": ")
            .substringBefore(" or ").trim().toIntRange()
        val high = it.substringAfter(" or ").trim().toIntRange()
        Section(name, low, high)
    }
    println(sections)
    val tickets = nearby.filter { values ->
        values.split(',').map { it.toInt() }.all { value ->
            sections.any { it.contains(value) }
        }
    }.map { it.toTicket() }

    val sorted = (0..ticket.features.lastIndex).map { index ->
        val values = tickets.getValuesAtIndex(index)
        index to sections.filter { section ->
            values.all { section.contains(it) }
        }.map { it.name }
    }.sortedBy { it.second.size }

    // this only works since the input neatly adds up
    val picked: MutableSet<String> = mutableSetOf()
    val reduced = sorted.map {
        val pick = it.second.filterNot { name -> name in picked }
        picked.addAll(pick)
        it.first to pick
    }
    // prints out the assignments, I just manually parsed out the features
    // starting with 'departure'
    reduced.forEach {
        println(it)
    }
    println(ticket.features[11].toLong() * ticket.features[7] * ticket.features[9] *
            ticket.features[0] * ticket.features[3] * ticket.features[4])
}