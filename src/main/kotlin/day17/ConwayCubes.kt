package day17

import java.io.File

fun main() {
    //breakfast()
    lunch()
}

data class Point(val x: Int, val y: Int, val z: Int)

data class Point4(val x: Int, val y: Int, val z: Int, val w: Int)

object ConwayCube {

    var cube: Set<Point> = initCube()

    val pointMin: Point
        get() {
            val minX = cube.minOf { it.x } - 1
            val minY = cube.minOf { it.y } - 1
            val minZ = cube.minOf { it.z } - 1
            return Point(minX, minY, minZ)
        }

    val pointMax: Point
        get() {
            val maxX = cube.maxOf { it.x } + 1
            val maxY = cube.maxOf { it.y } + 1
            val maxZ = cube.maxOf { it.z } + 1
            return Point(maxX, maxY, maxZ)
        }

    val size: Int
        get() = cube.size

    fun getNeighbors(point: Point): List<Point> {
        val rx = point.x - 1 .. point.x + 1
        val ry = point.y - 1 .. point.y + 1
        val rz = point.z - 1 .. point.z + 1
        return cube.filter { it.x in rx && it.y in ry && it.z in rz && it != point }.toList()
    }

    private fun initCube() = run {
        File("data/day17/input.txt").readLines().mapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Point(x, y, 0) else null
            }
        }.flatten().toSet()
    }

    fun nextGeneration() {
        val next: MutableSet<Point> = mutableSetOf()
        val min = pointMin
        val max = pointMax
        for (x in min.x..max.x) {
            for (y in min.y..max.y) {
                for (z in min.z..max.z) {
                    val p = Point(x, y, z)
                    if (cube.contains(p)) {
                        if (getNeighbors(p).size in 2..3) next.add(p)
                    } else {
                        if (getNeighbors(p).size == 3) next.add(p)
                    }
                }
            }
        }
        cube = next.toSet()
    }
}

object ConwayCube4 {

    var cube4: Set<Point4> = initCube4()

    val pointMin4: Point4
        get() {
            val minX = cube4.minOf { it.x } - 1
            val minY = cube4.minOf { it.y } - 1
            val minZ = cube4.minOf { it.z } - 1
            val minW = cube4.minOf { it.w } - 1
            return Point4(minX, minY, minZ, minW)
        }

    val pointMax4: Point4
        get() {
            val maxX = cube4.maxOf { it.x } + 1
            val maxY = cube4.maxOf { it.y } + 1
            val maxZ = cube4.maxOf { it.z } + 1
            val maxW = cube4.maxOf { it.w } + 1
            return Point4(maxX, maxY, maxZ, maxW)
        }

    val size4: Int
        get() = cube4.size

    fun getNeighbors4(point: Point4): List<Point4> {
        val rx = point.x - 1 .. point.x + 1
        val ry = point.y - 1 .. point.y + 1
        val rz = point.z - 1 .. point.z + 1
        val rw = point.w - 1 .. point.w + 1
        return cube4.filter { it.x in rx && it.y in ry
                && it.z in rz && it.w in rw && it != point }.toList()
    }

    private fun initCube4() = run {
        File("data/day17/input.txt").readLines().mapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Point4(x, y, 0, 0) else null
            }
        }.flatten().toSet()
    }

    fun nextGeneration4() {
        val next: MutableSet<Point4> = mutableSetOf()
        val min = pointMin4
        val max = pointMax4
        println(min)
        println(max)
        for (x in min.x..max.x) {
            for (y in min.y..max.y) {
                for (z in min.z..max.z) {
                    for (w in min.w..max.w) {
                        val p = Point4(x, y, z, w)
                        if (cube4.contains(p)) {
                            if (getNeighbors4(p).size in 2..3) next.add(p)
                        } else {
                            if (getNeighbors4(p).size == 3) next.add(p)
                        }
                    }
                }
            }
        }
        cube4 = next.toSet()
    }

}

fun breakfast() {
    repeat(6) {
        ConwayCube.nextGeneration()
    }
    println(ConwayCube.size)
}

fun lunch() {
    repeat(9) {
        ConwayCube4.nextGeneration4()
    }
    println(ConwayCube4.size4)
}