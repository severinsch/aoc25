
fun main() {
    fun part1(input: List<String>): Long {
        val lines = input.map { line ->
            line.split(" ").filter { it.isNotBlank() }
        }
        val transposed = lines.first().indices.map { i ->
            lines.indices.map { j -> lines[j][i] }
        }
        return transposed.sumOf { problem ->
            return@sumOf when(problem.last()) {
                "*" -> {
                    problem.dropLast(1).fold(
                        1L)
                        { acc, c -> acc * c.toLong() }
                }
                "+" -> {
                    problem.dropLast(1).sumOf { it.toLong() }
                }

                else -> throw IllegalStateException()
            }
        }
    }

    fun <T>List<List<T>>.transpose(): List<List<T>> {
        return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
    }
    fun List<String>.transpose(): List<List<Char>> {
        return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
    }

    fun part2(input: List<String>): Long {
        val maxLen = input.maxOf { it.length }
        val padded = input.map { it.padEnd(maxLen, ' ') }
        var transposed = padded.transpose()
        var total = 0L
        while (transposed.isNotEmpty()) {
            val problemLists = transposed.takeWhile { it.any { c -> !c.isWhitespace()} }
            transposed = transposed.drop(problemLists.size + 1)
            val operation = problemLists.first().last()
            val problem = problemLists.map { it.joinToString("").removeSuffix(operation.toString()).trim() }
            println(problem)
            total += when(operation) {
                '*' -> {
                    problem.fold(
                        1L)
                    { acc, c -> acc * c.toLong() }
                }
                '+' -> {
                    problem.sumOf { it.toLong() }
                }
                else -> throw IllegalStateException()
            }
        }
        return total
    }

    val input = readInput("day06")
    part1(input).println()
    part2(input).println()
}
