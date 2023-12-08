
import io.kotest.matchers.shouldBe

private const val day = "Day07"
private data class InputHand(
    val hand: Hand,
    val bet: Long
): Comparable<InputHand> {
    override fun compareTo(other: InputHand): Int = hand.compareTo(other.hand)
}
private enum class CamelCard(val char: Char) {
    N2('2'),
    N3('3'),
    N4('4'),
    N5('5'),
    N6('6'),
    N7('7'),
    N8('8'),
    N9('9'),
    T('T'),
    J('J'),
    Q('Q'),
    K('K'),
    A('A');

    override fun toString() = char.toString()
}
private enum class HandStrength {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OAK, FULL_HOUSE, FOUR_OAK, FIVE_OAK
}

private data class Hand(
    val cards: List<CamelCard>,
    val jokers: Boolean = false
): Comparable<Hand> {
    fun strength(): HandStrength {
        val group = cards.groupBy { it }
        return when {
            group.any { it.value.size == 5 } -> HandStrength.FIVE_OAK
            group.any { it.value.size == 4 } -> HandStrength.FOUR_OAK
            group.size == 2 && group.entries.first().value.size in listOf(2,3) -> HandStrength.FULL_HOUSE
            group.any { it.value.size == 3 } -> HandStrength.THREE_OAK
            group.filter { it.value.size == 2 }.size == 2 -> HandStrength.TWO_PAIR
            group.any { it.value.size == 2 } -> HandStrength.ONE_PAIR
            else -> HandStrength.HIGH_CARD
        }
    }
    fun optimized(): Hand {
        if(!this.cards.contains(CamelCard.J) || strength() == HandStrength.FIVE_OAK)
            return this

        val most = cards.filter { it != CamelCard.J }
            .groupBy { it }
            .maxBy { it.value.size }.key

        return replace(most)
    }

    private fun replace(to: CamelCard, from: CamelCard = CamelCard.J): Hand {
        return this.copy(cards = cards.map { if(it == from) to else it })
    }

    override operator fun compareTo(other: Hand): Int {
        val comp = if(jokers)
            this.optimized().strength().compareTo(other.optimized().strength())
        else
            this.strength().compareTo(other.strength())

        if(comp == 0) {
            for(i in 0..4) {
                val mine = cards[i]
                val theirs = other.cards[i]
                val diff = if(jokers) {
                    if(mine == CamelCard.J && theirs != CamelCard.J) {
                        -1
                    } else if(mine == CamelCard.J) {
                        0
                    } else if(theirs == CamelCard.J) {
                        1
                    } else {
                        mine.compareTo(theirs)
                    }
                } else {
                    mine.compareTo(theirs)
                }

                if(diff != 0) return diff
            }
            return 0
        } else {
            return comp
        }
    }
}

fun main() {
    fun parse(input: List<String>, jokers: Boolean = false): List<InputHand> {
        return input.map {
            val (cards, bet) = it.destruct("""(.*) (\d+)""".toRegex())

            val hand = Hand(cards.map {
                CamelCard.entries.first { e -> e.char == it }
            }, jokers = jokers)

            InputHand(hand, bet.toLong())
        }
    }

    fun sortAndCount(entries: List<InputHand>): Long {
        val sorted = entries.sorted()
        return sorted.mapIndexed { index, inputHand ->
            val rank = index+1
            rank * inputHand.bet
        }.sum()
    }

    fun part1(input: List<String>) = sortAndCount(parse(input))
    fun part2(input: List<String>) = sortAndCount(parse(input, true))

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 6440
    part2(test) shouldBe 5905

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}