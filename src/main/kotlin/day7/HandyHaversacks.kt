package day7

import java.io.File

fun main() {
    breakfast()
}

// part 1
// this for me was tougher than expected
// need to think about the data structures used.

fun breakfast() {
    val rules = File("data/day7/input.txt").readLines()
    val map = rules.map {
        val (outer, inner) = it.split(" bags contain ")
        // fixme find a better regex
        val innerFixed = inner.replace("\\d ".toRegex(), "")
            .replace(" bags.", "")
            .replace(" bag.", "")
            .replace(" bags, ", ",")
            .replace(" bag, ", ",").split(",")
        Pair(outer, innerFixed)
    }.toMap()
    val lookup: MutableMap<String, MutableSet<String>> = mutableMapOf()
    map.forEach { parent ->
        parent.value.forEach {
            if (lookup.containsKey(it)) {
                val next = lookup.get(it)
                next?.add(parent.key)
            }
            else {
                lookup.put(it, mutableSetOf(parent.key))
            }
        }
    }
    println(findBags("shiny gold", lookup, mutableSetOf()).size)
}

// recusively adding bags.
// fixme: I pass back and forth this set of found strings, rethink
fun findBags(bag: String, lookup: MutableMap<String, MutableSet<String>>, found: MutableSet<String>): MutableSet<String> {
    val parents = lookup.getOrDefault(bag, mutableSetOf())
    if (parents.size == 0) return found
    else if (parents.all { it in found }) return found
    else  {
        parents.forEach {
            found.add(it) }
        parents.forEach {
            findBags(it, lookup, found)
        }
        return found
    }
}