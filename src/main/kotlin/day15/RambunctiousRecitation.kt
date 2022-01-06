package day15

fun main() {
    breakfast()
}

object Memory {
    val map: MutableMap<Int, Int> = mutableMapOf()
    var index = 0

    fun sayAndGetNext(number: Int): Int {
        // number is new
        if (!map.containsKey(number)) {
            map[number] = ++index
            return 0
        }
        else {
            val previousIndex = map[number]!!
            val diff = ++index - previousIndex
            map[number] = index
            return diff
        }
    }

    // initialises the 'memory' by provided list.
    // will not add but return the last element to kick of the game without conditionals
    fun initMap(list: List<Int>): Int {
        val starters = list.dropLast(1)
        starters.forEach { number ->
            map[number] = ++index
        }
        return list.last()
    }
}

fun breakfast() {
    val sample1 = listOf(0, 3, 6)
    val sample2 = listOf(1, 3, 2)
    val input = listOf(9, 19, 1, 6, 0, 5, 4)
    var next = Memory.initMap(input)
    println(Memory.map)
    println(next)
    while (Memory.index < 30000000 - 1) {
        next = Memory.sayAndGetNext(next)
    }
    println(next)
}