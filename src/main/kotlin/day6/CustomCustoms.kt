package day6

import java.io.File
import kotlin.system.exitProcess

fun main() {
    breakfast()
    lunch()
}

// task is to count and add the variety of answers by group
fun breakfast() {
    val inputFile = File("data/day6/input.txt")
    val answers = inputFile.readText().trim().split("\n\n").map {
        it.replace("""\s+""".toRegex(), "")
    }
    val groupsTotal = answers.map {
        it.toSet().size
    }.sum()
    println(groupsTotal)
}

// task is to count and add the similarity of answers by group
fun lunch() {
    val inputFile = File("data/day6/input.txt")
    val answers = inputFile.readText().trim().split("\n\n").map {
        it.replace("""\s+""".toRegex(), " ")
    }

    // answers for each group at this point are 'words' separated by spaces
    val union = answers.map {
        // split the individual responses
        val responses = it.split(" ")
        // get a set of all letters used in this group
        val letters = it.replace("""\s+""".toRegex(), "").toSet()
        // filter out all letters not used in all responses
        letters.toList().filter { c ->
            responses.count { it.contains(c) } == responses.size
        }.size
    }

    println(union.sum())
}