
import io.kotest.matchers.shouldBe

private const val day = "Day03"

private data class Coord(val x: Int, val y: Int)
private enum class Direction(val offset: Coord) {
    NW(Coord(-1,-1)),
    N(Coord(0, -1)),
    NE(Coord(1,-1)),
    W(Coord(-1,0)),
    E(Coord(1,0)),
    SW(Coord(-1,1)),
    S(Coord(0,1)),
    SE(Coord(1,1))
}
private data class Position(val data: List<String>, val coord: Coord) {
    fun char() = data[coord.y][coord.x]
    fun isDigit() = char().isDigit()
    fun isSymbol() = !char().isDigit() && char() != '.'
    fun isGear() = char() == '*'
}

fun main() {
    fun Position.relative(direction: Direction): Position? {
        val newCoord = coord.copy(x = coord.x + direction.offset.x, y = coord.y + direction.offset.y)
        return if(newCoord.x < 0 || newCoord.y < 0 || newCoord.x >= data[0].length || newCoord.y >= data.size)
            null
        else
            this.copy(coord = newCoord)
    }


    fun part1(input: List<String>): Int {
        return buildList {
            for (y in input.indices) {
                val item = input[y]
                val sb = StringBuilder()
                var adjacent = false
                for(x in item.indices) {
                    val p = Position(input, Coord(x,y))

                    if(p.isDigit()) {
                        sb.append(p.char())
                        adjacent = adjacent || Direction.entries.any { p.relative(it)?.isSymbol() == true }
                    }

                    if(!p.isDigit() || x == item.length-1) {
                        val number = if(!adjacent || sb.isEmpty()) null else sb.toString().toInt()

                        sb.clear()
                        adjacent = false

                        if(number != null)
                            add(number)
                    }
                }
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val gearsTotal = mutableMapOf<Coord, List<Int>>()
        for (y in input.indices) {
            val item = input[y]
            val sb = StringBuilder()
            val adjacentGears = mutableSetOf<Coord>()

            for(x in item.indices) {
                val p = Position(input, Coord(x,y))

                if(p.isDigit()) {
                    sb.append(p.char())
                    adjacentGears.addAll(Direction.entries.mapNotNull {
                        p.relative(it)?.takeIf { it.isGear() }?.coord
                    })
                }

                if(!p.isDigit() || x == item.length-1) {
                    val number = if(sb.isEmpty()) null else sb.toString().toInt()

                    if(number != null) {
                        adjacentGears.forEach {
                            val list = gearsTotal.getOrPut(it) { listOf() }
                            gearsTotal[it] = list + number
                        }
                    }

                    adjacentGears.clear()
                    sb.clear()
                }
            }
        }

        return gearsTotal.map {
            if(it.value.size == 2) {
                it.value[0] * it.value[1]
            } else 0
        }.sum()
    }

    val input = readInput(day)
    val test = readInput("$day-test")

    part1(test) shouldBe 4361
    part2(test) shouldBe 467835

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}