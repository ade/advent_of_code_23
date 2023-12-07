
import io.kotest.matchers.shouldBe

private const val day = "Day05"

private data class Info(
    val starters: List<Long>,
    val maps: List<ExchangeMap>
)
private data class ExchangeMap(
    val name: String,
    val entries: List<GroupRange>
) {
    fun exchange(from: Long): Long {
        return entries.map { it.exchange(from) }.firstOrNull { it != from } ?: from
    }
    fun reverse(to: Long): Long {
        return entries.map { it.reverse(to) }.firstOrNull { it != to } ?: to
    }
}

private data class GroupRange(
    val srcRangeStart: Long,
    val destRangeStart: Long,
    val length: Long,
) {
    fun exchange(from: Long): Long {
        return if(from >= srcRangeStart && from <= srcRangeStart + length) {
            destRangeStart + (from - srcRangeStart)
        } else from
    }

    fun reverse(to: Long): Long {
        return if(to >= destRangeStart && to <= destRangeStart + length) {
            srcRangeStart + (to - destRangeStart)
        } else to
    }
}

fun main() {
    fun parseCategoryMap(data: List<String>): ExchangeMap {
        val name = data[0].substringBefore(" map:")

        val groups = data.drop(1).map {
            //the destination range start, the source range start, and the range length.
            val (destStart, srcStart, length) = it.destructLongs("""(\d+) (\d+) (\d+)""".toRegex())
            GroupRange(
                srcRangeStart = srcStart,
                destRangeStart = destStart,
                length = length
            )
        }

        return ExchangeMap(name, groups)
    }

    fun parse(input: List<String>): Info {
        val starters = input[0]
            .substringAfter("seeds: ")
            .trim()
            .split(" ")
            .map { it.toLong() }

        val sections = input.drop(2)
            .joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }

        val maps = sections.map { parseCategoryMap(it).also { println(it) } }

        return Info(
            starters = starters,
            maps = maps
        )
    }

    fun part1(input: List<String>): Long {
        val data = parse(input)
        val locations = data.starters.map {
            var current = it
            data.maps.forEach {
                current = it.exchange(current)
            }
            current
        }
        return locations.min()
    }

    fun part2(input: List<String>): Long {
        val data = parse(input)
        val seedRanges = data.starters.chunked(2).map { it[0]..<(it[0]+it[1]) }
        var i = 0L
        var found = -1L
        val reverseMaps = data.maps.reversed()
        var lastPrint = System.currentTimeMillis()
        while(found == -1L) {
            val startAt = i
            var current = startAt
            reverseMaps.forEach {
                current = it.reverse(current)
            }

            if(seedRanges.any { it.contains(current) }) found = startAt
            i++

            val t = System.currentTimeMillis()
            if(lastPrint + 1000L < t) {
                println("Iteration $i")
                lastPrint = t
            }
        }
        return found
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 35
    part2(test) shouldBe 46

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}