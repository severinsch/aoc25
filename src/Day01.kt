import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        return input.runningFold(50) {
            acc, s ->
                when(s.first()) {
                    'R' -> (acc + s.drop(1).toInt()).mod(100)
                    'L' -> (acc - s.drop(1).toInt()).mod(100)
                    else -> throw UnsupportedOperationException()
                }
        }.count {it == 0}
    }

    fun part2(input: List<String>): Int {
        return input.fold(Pair(50,0)) {
                acc, s ->
            when(s.first()) {
                'R' -> {
                    val res = (acc.first + s.drop(1).toInt())
                    Pair(res.mod(100),acc.second + res.div(100))
                }
                'L' -> {
                    val res = (acc.first - s.drop(1).toInt())
                    // extra if we land on 0 or move left past zero. no extra if we start at zero and move left
                    val extra = if (res == 0 || (res < 0 && (acc.first != 0))) 1 else 0
                    Pair(res.mod(100),acc.second + res.div(100).absoluteValue + extra)
                }
                else -> throw UnsupportedOperationException()
            }
        }.second
    }

    val input = readInput("day01")
    part1(input).println()
    part2(input).println()
}
