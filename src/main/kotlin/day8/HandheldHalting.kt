package day8

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

sealed class Instruction(val value: Int)
class Nop(value: Int) : Instruction(value)
class Acc(value: Int) : Instruction(value)
class Jmp(value: Int) : Instruction(value)

fun performInstruction(index: Int) {
    if (Instructions.wasVisited(index)) {
        return
    }
    Instructions.visited.add(index)
    val instruction = Instructions.list[index]
    when(instruction) {
        is Nop -> performInstruction(index + 1)
        is Acc -> {
            Instructions.accumulator += instruction.value
            performInstruction(index + 1)
        }
        is Jmp -> performInstruction(index + instruction.value)
    }
}

fun checkVariation(index: Int, variation: Int) {
    assert(index < Instructions.list.size)
    // nothing to do when acc
    if (Instructions.list[variation] is Acc) {
        return
    }
    // we found a terminating version
    if (index == Instructions.list.lastIndex) {
        println("### terminating program found, variation index: $variation")
        println(Instructions.accumulator)
        return
    }
    // reset on 0 (assumes no jump goes there..)
    if (index == 0) {
        Instructions.visited.clear()
        Instructions.accumulator = 0
    }
    // loop found, try next variation
    if (Instructions.wasVisited(index)) {
        return
    }
    Instructions.visited.add(index)
    val instruction = Instructions.list[index]
    when(instruction) {
        is Nop -> {
            // switch nop with jmp
            if (index == variation) checkVariation(index + instruction.value, variation)
            else checkVariation(index + 1, variation)
        }
        is Acc -> {
            Instructions.accumulator += instruction.value
            checkVariation(index + 1, variation)
        }
        is Jmp -> {
            // switch jmp with nop
            if (index == variation) checkVariation(index + 1, variation)
            else checkVariation(index + instruction.value, variation)
        }
    }
}

object Instructions {
    val list by lazy {
        File("data/day8/input.txt").readLines().mapNotNull {
            val (type, value) = it.split(" ")
            when (type) {
                "nop" -> Nop(value.toInt())
                "acc" -> Acc(value.toInt())
                "jmp" -> Jmp(value.toInt())
                else -> null
            }
        }
    }
    val visited: MutableSet<Int> = mutableSetOf()
    var accumulator = 0
    fun wasVisited(index: Int) = visited.contains(index)
}

fun breakfast() {
    performInstruction(0)
}

fun lunch() {
    for (i in 0..277) {
        checkVariation(0, i)
    }
}