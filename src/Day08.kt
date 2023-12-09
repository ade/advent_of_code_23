
import io.kotest.matchers.shouldBe

private const val day = "Day08"

private enum class LeftRight {
    L, R
}
private data class MapNode(val id: String, val left: String, val right: String) {
    fun go(dir: LeftRight, map: Map<String, MapNode>): MapNode {
        return if(dir == LeftRight.L) {
            map[left]!!
        } else {
            map[right]!!
        }
    }
}

private data class DesertIslandMap(val instructions: List<LeftRight>, val map: Map<String, MapNode>)
fun main() {
    fun parse(input: List<String>): DesertIslandMap {
        val instructions = input.first().map { if(it == 'L') LeftRight.L else LeftRight.R }
        val map = input.drop(2).map {
            val (id, l, r) = it.destruct("""(...) = \((...), (...)\)""".toRegex())
            MapNode(id, l, r)
        }.associateBy { it.id }
        return DesertIslandMap(instructions, map)
    }

    fun findLength(startNode: MapNode, data: DesertIslandMap): Long {
        var steps = 0L
        var index = 0
        var node = startNode

        while (true) {
            steps++

            val dir = data.instructions[index]
            node = node.go(dir, data.map)
            if(node.id.endsWith("Z")) {
                return steps
            }

            index = if(index == data.instructions.lastIndex) 0 else index + 1
        }
    }

    /** Greatest common divisor */
    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    /** Least common multiple */
    fun lcm(a: Long, b: Long): Long {
        return if (a == 0L || b == 0L) 0L else a * b / gcd(a, b)
    }

    fun part1(input: List<String>): Long {
        val data = parse(input)
        return findLength(data.map.entries.first().value, data)
    }

    fun part2(input: List<String>): Long {
        val data = parse(input)
        return data.map.filter { it.key.endsWith("A") }.values
            .map { findLength(it, data) }
            .reduce { acc, length -> lcm(acc, length) }
    }

    val input = readInput(day)
    val test = readInput("$day-test")
    val test2 = readInput("$day-test2")

    part1(test) shouldBe 6
    part2(test2) shouldBe 6

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}