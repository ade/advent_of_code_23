import io.kotest.matchers.shouldBe

fun main() {
    val part2table = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { item ->
            ("${item.first { it.isDigit() }}" + "${item.last { it.isDigit() }}").toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {row ->
            val first = part2table.mapNotNull { entry ->
                row.indexOf(entry.key).takeIf { it != -1 }?.let { entry to it }
            }.minBy { it.second }.first.value

            val last = part2table.mapNotNull { entry ->
                row.lastIndexOf(entry.key).takeIf { it != -1 }?.let { entry to it }
            }.maxBy { it.second }.first.value

            "$first$last".toInt()
        }
    }

    part1(readInput("Day01-test")) shouldBe 142
    part2(readInput("Day01-test2")) shouldBe 281

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}