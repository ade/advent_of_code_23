
import io.kotest.matchers.shouldBe
import kotlin.math.pow

private const val day = "Day04"

private data class Card(
    val id: Int,
    val winners: Set<Int>,
    val have: Set<Int>
) {
    val winningNumbers = have.intersect(winners).size
}
fun main() {
    fun parse(input: List<String>): List<Card> {
        return input.map {
            val (id, firstNumbers, secondNumbers) = it.destruct("""Card\s+(\d+):(.*)\|(.*)""".toRegex())
            Card(
                id = id.toInt(),
                winners = firstNumbers.trim().replace("  ", " ").split(" ").map { it.toInt() }.toSet(),
                have = secondNumbers.trim().replace("  ", " ").split(" ").map { it.toInt() }.toSet(),
            )
        }
    }

    fun part2resolve(allCards: List<Card>, card: Card): List<Card> {
        val newCards = allCards.filter {
            it.id > card.id && it.id <= card.id + card.winningNumbers
        }

        return listOf(card) + newCards.flatMap {
            part2resolve(allCards, it)
        }
    }

    fun part1(input: List<String>): Int {
        return parse(input).sumOf {
            if(it.winningNumbers >= 1)
                2.toDouble().pow(it.winningNumbers-1).toInt()
            else 0
        }
    }

    fun part2(input: List<String>): Int {
        return parse(input).flatMap { part2resolve(parse(input), it) }.size
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 13
    part2(test) shouldBe 30

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}