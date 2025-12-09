
fun main() {
    fun part1(input: List<String>): Int {
        return input.iterateWithCoords().count { (c, coords) ->
            c == '@' &&
                (input.getAllSurrounding(coords).count { it == '@' } < 4)
        }
    }

    fun part2(input: List<String>): Int {
        val input = input.toMutableList()
        var removed: Int
        var total = 0
        do {
            val removable = input.iterateWithCoords().toList().filter { (c, coords) ->
                c == '@' &&
                        (input.getAllSurrounding(coords).count { it == '@' } < 4)
            }
            removed = removable.count()
            total += removed
            removable.forEach { (_, coords) ->
                input[coords] = '.'
            }
        } while(removed > 0)
        return total
    }

    val input = readInput("day04")
    part1(input).println()
    part2(input).println()
}
