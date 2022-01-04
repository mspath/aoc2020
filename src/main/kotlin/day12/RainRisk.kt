package day12

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    //breakfast()
    lunch()
}

data class Command(val command: Char, val value: Int)

data class Ship(var direction: Char, var x: Int, var y: Int)

fun Ship.turnRight(times: Int) {
    (0 until times).forEach {
        when (this.direction) {
            'E' -> this.direction = 'S'
            'S' -> this.direction = 'W'
            'W' -> this.direction = 'N'
            'N' -> this.direction = 'E'
        }
    }
}

fun Ship.turnLeft(times: Int) {
    (0 until times).forEach {
        when (this.direction) {
            'E' -> this.direction = 'N'
            'S' -> this.direction = 'E'
            'W' -> this.direction = 'S'
            'N' -> this.direction = 'W'
        }
    }
}

fun Ship.processCommand(command: Command) {
    when(command.command) {
        'N' -> this.y += command.value
        'S' -> this.y -= command.value
        'E' -> this.x += command.value
        'W' -> this.x -= command.value
        'F' -> this.processCommand(Command(this.direction, command.value))
        'R' -> this.turnRight(command.value / 90)
        'L' -> this.turnLeft(command.value / 90)
    }
}

fun breakfast() {
    val input = File("data/day12/input.txt").readLines().map {
        Command(it[0], it.drop(1).toInt())
    }
    val ship = Ship('E', 0, 0)
    input.forEach {
        ship.processCommand(it)
    }
    println(ship)
    println(ship.x.absoluteValue + ship.y.absoluteValue)
}

data class Waypoint(var x: Int, var y: Int)

fun ShipWithWaypoint.turnRight(times: Int) {
    (0 until times).forEach {
        this.waypoint.x = this.waypoint.y.also { this.waypoint.y = -this.waypoint.x }
    }
}

fun ShipWithWaypoint.turnLeft(times: Int) {
    (0 until times).forEach {
        this.waypoint.x = -this.waypoint.y.also { this.waypoint.y = this.waypoint.x }
    }
}

fun ShipWithWaypoint.processCommand(command: Command) {
    when(command.command) {
        'N' -> this.waypoint.y += command.value
        'S' -> this.waypoint.y -= command.value
        'E' -> this.waypoint.x += command.value
        'W' -> this.waypoint.x -= command.value
        'F' -> {
            this.x += waypoint.x * command.value
            this.y += waypoint.y * command.value
        }
        'R' -> this.turnRight(command.value / 90)
        'L' -> this.turnLeft(command.value / 90)
    }
}

data class ShipWithWaypoint(val waypoint: Waypoint, var x: Int, var y: Int)

fun lunch() {
    val input = File("data/day12/input.txt").readLines().map {
        Command(it[0], it.drop(1).toInt())
    }
    val ship = ShipWithWaypoint(Waypoint(10, 1), 0, 0)
    input.forEach {
        ship.processCommand(it)
    }
    println(ship)
    println(ship.x.absoluteValue + ship.y.absoluteValue)
}