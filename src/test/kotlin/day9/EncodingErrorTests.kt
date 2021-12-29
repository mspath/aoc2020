package day9

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EncodingErrorTests {

    val windowSample = 5
    val inputSample = File("data/day9/sample.txt").readLines().map { it.toLong() }
    val window = 25
    val input = File("data/day9/input.txt").readLines().map { it.toLong() }

    @Test
    fun `basic addsUp test`() {
        val listAddingUp = listOf(1L, 2, 3, 4, 5, 9)
        val listNotAddingUp = listOf(1L, 2, 3, 4, 5, 10)
        assertTrue(addsUp(listAddingUp))
        assertFalse(addsUp(listNotAddingUp))
    }

    @Test
    fun `breakfast sample test`() {
        val solution = breakfast(inputSample, windowSample)
        assertEquals(127L, solution)
    }

    @Test
    fun `breakfast test`() {
        val solution = breakfast(input, window)
        assertEquals(41682220L, solution)
    }

    @Test
    fun `lunch sample test`() {
        val xmas = 127L
        val solution = lunch(inputSample, xmas)
        assertEquals(62L, solution)
    }

    @Test
    fun `lunch test`() {
        val xmas = 41682220L
        val solution = lunch(input, xmas)
        assertEquals(5388976L, solution)
    }
}
