
import io.kotest.matchers.shouldBe

private const val day = "Day06"
fun main() {
    fun parse(input: List<String>): List<Pair<Int, Int>> {
        val times = input[0].substringAfter("Time:").trim().split("""\s+""".toRegex())
        val dists = input[1].substringAfter("Distance:").trim().split("""\s+""".toRegex())
        return times.map { it.toInt() }.zip(dists.map { it.toInt() })
    }

    fun parsePart2(input: List<String>): Pair<Long, Long> {
        val time = input[0].substringAfter("Time:").trim().replace(" ", "").toLong()
        val dist = input[1].substringAfter("Distance:").trim().replace(" ", "").toLong()
        return time to dist
    }

    fun part1(input: List<String>): Int {
        return parse(input).map {
            val time = it.first
            val record = it.second
            var wins = 0
            for(i in 1..<time) {
                val speed = i
                val distance = (time - i) * speed
                if(distance > record)
                    wins++
            }
            wins
        }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val data = parsePart2(input)
        val time = data.first
        val record = data.second
        var wins = 0L
        for(i in 1..<time) {
            val speed = i
            val distance = (time - i) * speed
            if(distance > record)
                wins++
        }
        return wins
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 288
    part2(test) shouldBe 71503

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}