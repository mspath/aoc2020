package day11

import java.io.File
import kotlin.system.exitProcess

data class Position(val x: Int, val y: Int)

enum class State {
    FLOOR,
    EMPTY,
    OCCUPIED
}

data class Cell(val position: Position, var state: State) {

    var nextState = state

    val willChange
        get() = nextState != state

    // this time I try to process the steps 'in place' in 2 phases which need to be called sequentially
    fun prepareNextStep() {
        val neighbors = Ferry.getNeighbors(position).filter { it.state == State.OCCUPIED }.size
        if (state == State.EMPTY && neighbors == 0) nextState = State.OCCUPIED
        else if (state == State.OCCUPIED && neighbors > 3) nextState = State.EMPTY
    }

    fun prepareNextStepTwo() {
        val visibleNeighbors = Ferry.countNeighborsInSight(this.position)
        if (state == State.EMPTY && visibleNeighbors == 0) nextState = State.OCCUPIED
        else if (state == State.OCCUPIED && visibleNeighbors > 4) nextState = State.EMPTY
    }

    fun applyNextStep() {
        state = nextState
    }
}

object Ferry {

    val seats by lazy {
        File("data/day11/input.txt").readLines().mapIndexed() { y, s ->
            s.mapIndexedNotNull() { x, c ->
                when(c) {
                    '.' -> Cell(Position(x, y), State.FLOOR)
                    'L' -> Cell(Position(x, y), State.EMPTY)
                    '#' -> Cell(Position(x, y), State.OCCUPIED)
                    else -> null
                }
            }
        }.flatten().associateBy { it.position }
    }

    fun nextStep() {
        seats.values.forEach {
            it.prepareNextStep()
        }
        val stable = seats.values.all { it.willChange == false }
        if (stable) {
            printFloor()
            val occupied = seats.values.count { it.state == State.OCCUPIED }
            println(occupied)
            exitProcess(0)
        }
        seats.values.forEach {
            it.applyNextStep()
        }
    }

    fun getNeighbors(position: Position): List<Cell> {
        val x = position.x
        val y = position.y
        val list = listOf(
            Position(x - 1, y - 1),
            Position(x, y - 1),
            Position(x + 1, y - 1),
            Position(x - 1, y),
            Position(x + 1, y),
            Position(x - 1, y + 1),
            Position(x, y + 1),
            Position(x + 1, y + 1),
        )
        val neighbors = list.mapNotNull {
            seats.get(it)
        }.filterNot { it.state == State.FLOOR }
        return neighbors
    }

    fun nextStepTwo() {
        seats.values.forEach {
            it.prepareNextStepTwo()
        }
        val stable = seats.values.all { it.willChange == false }
        if (stable) {
            println("----")
            printFloor()
            val occupied = seats.values.count { it.state == State.OCCUPIED }
            println(occupied)
            exitProcess(0)
        }
        seats.values.forEach {
            it.applyNextStep()
        }
    }

    fun countNeighborsInDirection(position: Position, direction: (Position) -> Position): Int {
        val next = direction(position)
        // at a border
        if (!seats.containsKey(next)) {
            return 0
        }
        val nextCell = seats.get(next)
        // return 0 for floors
        nextCell?.let {
            return if (it.state == State.OCCUPIED) 1
            else if (it.state == State.EMPTY) 0
            else countNeighborsInDirection(next, direction)
        }
        // should not be possible
        return countNeighborsInDirection(next, direction)
    }

    // take a look in each of the 8 directions and return how many of them contain visible occupieds
    fun countNeighborsInSight(position: Position): Int {
        val directions = listOf(
            { p: Position -> Position(p.x - 1, p.y - 1) },
            { p: Position -> Position(p.x, p.y - 1) },
            { p: Position -> Position(p.x + 1, p.y - 1) },
            { p: Position -> Position(p.x - 1, p.y) },
            { p: Position -> Position(p.x + 1, p.y) },
            { p: Position -> Position(p.x - 1, p.y + 1) },
            { p: Position -> Position(p.x, p.y + 1) },
            { p: Position -> Position(p.x + 1, p.y + 1) }
        )
        return directions.sumOf {
            countNeighborsInDirection(position, it)
        }
    }

    fun printFloor() {
        val width = seats.keys.maxOf { it.x }
        val height = seats.keys.maxOf { it.y }
        for (y in 0..height) {
            for (x in 0..width) {
                val state = seats.get(Position(x, y))?.state
                state?.let {
                    val c = when(it) {
                        State.FLOOR -> '.'
                        State.OCCUPIED -> '#'
                        State.EMPTY -> 'L'
                    }
                    print(c)
                }
            }
            println()
        }
    }
}

fun main() {
    //breakfast()
    lunch()
}

fun breakfast() {
    var counter = 0
    while (true) {
        println(counter++)
        Ferry.nextStep()
    }
}

fun lunch() {
    var counter = 0
    while (true) {
        println(counter++)
        Ferry.nextStepTwo()
    }
}