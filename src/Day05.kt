import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val ranges = input.takeWhile { it.isNotBlank() }.map {
            it.split("-").let { (a, b) -> a.toLong()..b.toLong() }
        }
        val ingredients = input.drop(ranges.size + 1).map { it.toLong() }
        return ingredients.count { ing ->
            ranges.any { it.contains(ing)}
        }
    }

    fun part2(input: List<String>): Long {
        val ranges = input.takeWhile { it.isNotBlank() }.map {
            it.split("-").let { (a, b) -> a.toLong()..b.toLong() }
        }.sortedBy {it.first()}

        val nonOverlapping = mutableListOf(ranges.first())
        for (range in ranges.drop(1)) {
            val lastMerged = nonOverlapping.removeLast()
            if (range.first <= lastMerged.last) { // merge
                nonOverlapping.addLast(lastMerged.first..max(lastMerged.last, range.last))
            } else {
                nonOverlapping.addLast(lastMerged)
                nonOverlapping.addLast(range)
            }
        }
        return nonOverlapping.sumOf { it.last - it.first + 1 }
    }

    val input = readInput("day05")
    part1(input).println()
    part2(input).println()
}
