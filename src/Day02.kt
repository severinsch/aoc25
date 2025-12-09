
fun main() {
    fun part1(input: List<String>): Long {
        // this is inefficient, there has to be some smart way
        return input.first().split(",").flatMap { range ->
            val (start, end) = range.split("-").map { it.toLong() }
            return@flatMap (start..end).filter {
                val s = it.toString()
                s.drop(s.length/2) == s.take(s.length/2)
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return input.first().split(",").flatMap { range ->
            val (start, end) = range.split("-").map { it.toLong() }
            return@flatMap (start..end).filter {
                val s = it.toString()
                (s.length/2 downTo 1).any { len ->
                    s.chunkedSequence(len).distinct().count() == 1
                }
            }
        }.sum()
    }

    val input = readInput("day02")
    part1(input).println()
    part2(input).println()
}
