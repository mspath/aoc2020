package day4

import java.io.File

fun main() {
    morning()
    lunch()
}

// provides methods to check the validity
// I wanted to create passport instances, but we only check for validity...
object PassportValidator {

    private fun hasValidValues(map: Map<String, String>): Boolean =
        map.all { (k, v) ->
            // don't bother about number format exceptions, array indices etc.
            try {
                when (k) {
                    "byr" -> v.toInt() in 1920..2002
                    "iyr" -> v.toInt() in 2010..2020
                    "eyr" -> v.toInt() in 2020..2030
                    "hgt" -> when (v.takeLast(2)) {
                        "cm" -> v.dropLast(2).toInt() in 150..193
                        "in" -> v.dropLast(2).toInt() in 59..76
                        else -> false
                    }
                    "hcl" -> v matches """#[0-9a-f]{6}""".toRegex()
                    "ecl" -> v in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    "pid" -> v.length == 9 && v.toInt() > 0
                    // any value which slipped in can be ignored
                    else -> true
                }
            } catch (e: Exception) {
                false
            }
        }

    fun validateString(s: String): Boolean {
        val map = mutableMapOf<String, String>()
        val items = s.split(" ")
            .map { it.split(":") }
            .map {
                map.put(it.first(), it.last())
            }
        return hasValidValues(map)
    }

    // helper method to check for the keys
    fun containsRequiredFields(s: String): Boolean {
        return s.contains("byr:") && s.contains("iyr:") &&
                s.contains("eyr") && s.contains("hgt:") &&
                s.contains("hcl:") && s.contains("ecl:") &&
                s.contains("pid:")
    }
}

fun lunch() {
    val inputFile = File("data/day4/input.txt")
    val valid = inputFile.readText().trim().split("\n\n").map {
        it.replace("""\s""".toRegex(), " ")
    }.count {
        PassportValidator.containsRequiredFields(it) && PassportValidator.validateString(it)
    }
    println(valid)
}

fun morning() {
    val inputFile = File("data/day4/input.txt")
    val valid = inputFile.readText().trim().split("\n\n").map {
        it.replace("""\s""".toRegex(), " ")
    }.count {
        PassportValidator.containsRequiredFields(it)
    }
    println(valid)
}