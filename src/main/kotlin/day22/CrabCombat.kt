package day22

import java.io.File
import java.util.*

fun List<Int>.toQueue(): Queue<Int> {
    val queue: Queue<Int> = LinkedList()
    queue.addAll(this)
    return queue
}

fun main() {
    breakfast()
}

fun breakfast() {
    val (input1, input2) = File("data/day22/input.txt").readText()
        .split("\n\n")
        .map {
            it.split("\n")
        }
        .map { it.drop(1) }
    val deck1 = input1.map {
        it.toInt()
    }.toQueue()
    val deck2 = input2.map {
        it.toInt()
    }.toQueue()

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val card1 = deck1.remove()
        val card2 = deck2.remove()
        if (card1 > card2) {
            deck1.add(card1)
            deck1.add(card2)
        } else if (card2 > card1) {
            deck2.add(card2)
            deck2.add(card1)
        }
    }

    val winner = if (deck1.isEmpty()) deck2.reversed() else deck1.reversed()
    var result = 0
    winner.forEachIndexed { index, i ->
        result += i * (index + 1)
    }
    println(result)
}