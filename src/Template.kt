
import io.kotest.matchers.shouldBe

private const val day = "Template"
fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 100
    //part2(test) shouldBe 200

    println("Part 1: ${part1(input)}")
    //println("Part 2: ${part2(input)}")
}