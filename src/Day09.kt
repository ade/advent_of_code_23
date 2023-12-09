
import io.kotest.matchers.shouldBe

private const val day = "Day09"
fun main() {
    fun parse(input: List<String>): List<List<Long>> {
        return input.map {
            it.split("\\s+".toRegex()).map { it.toLong() }
        }
    }
    fun getLayers(data: List<Long>): List<List<Long>> {
        val result = mutableListOf(data)
        var prev = data
        while(result.last().any { it != 0L }) {
            prev = prev.windowed(2).map { (a, b) -> b - a }
            result.add(prev)
        }
        return result
    }

    fun extrapolated(data: List<List<Long>>): List<List<Long>> {
        val result = mutableListOf(data.last() + 0L)
        for((ri, li) in (0..<data.lastIndex).reversed().withIndex()) {
            val row = data[li]
            val below = result[ri].last()
            result.add(row + (row.last() + below))
        }
        return result.reversed()
    }

    fun extrapolatedLeft(data: List<List<Long>>): List<List<Long>> {
        val result = mutableListOf(data.last() + 0L)
        for((ri, li) in (0..<data.lastIndex).reversed().withIndex()) {
            val row = data[li]
            val below = result[ri].first()
            result.add(listOf(row.first() - below) + row)
        }
        return result.reversed()
    }

    fun part1(input: List<String>): Long {
        return parse(input).sumOf {
            extrapolated(getLayers(it)).first().last()
        }
    }

    fun part2(input: List<String>): Long {
        return parse(input).sumOf {
            extrapolatedLeft(getLayers(it)).first().first()
        }
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 114
    part2(test) shouldBe 2

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}