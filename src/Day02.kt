import io.kotest.matchers.shouldBe

private const val day = "Day02"
fun main() {
    data class BallSet(val r: Int, val g: Int, val b: Int)

    fun parse(input: List<String>): Map<Int, List<BallSet>> {
        return input.map {
            val (id, data) = it.destruct("""Game (\d+): (.*)""".toRegex())
            val rounds = data.split(";")

            id.toInt() to rounds.map { round ->
                var (r,g,b) = BallSet(0,0,0)
                round.split(",").map { draw ->
                    val (count, type) = draw.destruct("""\s*(\d+) (.+)\s*""".toRegex())
                    when(type) {
                        "red" -> r += count.toInt()
                        "green" -> g += count.toInt()
                        "blue" -> b += count.toInt()
                    }
                }
                BallSet(r,g,b)
            }
        }.toMap()
    }

    fun part1(input: List<String>): Int {
        return parse(input).map {
            if(it.value.any { it.r > 12 || it.g > 13 || it.b > 14 }) 0 else it.key
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return parse(input).map { game ->
            game.value.maxOf { it.r } * game.value.maxOf { it.g } * game.value.maxOf { it.b }
        }.sum()
    }

    part1(readInput("$day-test")) shouldBe 8
    part2(readInput("$day-test")) shouldBe 2286

    val input = readInput(day)

    println("Part 1: ${part1(input)}")
    part1(input) shouldBe 2727

    println("Part 2: ${part2(input)}")
    part2(input) shouldBe 56580
}