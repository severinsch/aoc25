import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private fun <T> allPairs(arr: List<T>): Sequence<Pair<T, T>> = sequence {
    for(i in 0 until arr.size-1)
        for(j in i+1 until arr.size)
            yield(arr[i] to arr[j])
}

private fun area(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>): Long {
    val width = abs(pair1.first - pair2.first) + 1
    val height = abs(pair1.second - pair2.second) + 1
    return width.toLong() * height
}

fun main() {
    fun part1(input: List<String>): Long {
        val parsed = input.map {
            val (x,y) = it.split(",")
            return@map Pair(x.toInt(),y.toInt())
        }
        return allPairs(parsed).maxOf { area(it.first, it.second) }
    }

    fun part2(input: List<String>): Long {
        val parsed = input.map {
            val (x,y) = it.split(",")
            return@map Pair(x.toInt(),y.toInt())
        }

        data class Edge(
            val minX: Int,
            val minY: Int,
            val maxX: Int,
            val maxY: Int
        )
        val first = parsed.first()
        val last = parsed.last()
        val edges = parsed.zipWithNext().map { (a, b) ->
            Edge(min(a.first, b.first), min(a.second, b.second), max(a.first, b.first), max(a.second, b.second))
        } + Edge(min(first.first, last.first), min(first.second, last.second), max(first.first, last.first), max(first.second, last.second))

        var maxArea = 0L

        allPairs(parsed).forEach { (a,b) ->
            val minX = min(a.first, b.first)
            val minY = min(a.second, b.second)
            val maxX = max(a.first, b.first)
            val maxY = max(a.second, b.second)

            val area = area(a, b)
            if (area <= maxArea) return@forEach

            val isContained = edges.none { e ->
                minX < e.maxX && maxX > e.minX && minY < e.maxY && maxY > e.minY
            }

            if (isContained) {
                maxArea = area
            }
        }
        return maxArea
    }

    val input = readInput("day09")
    part1(input).println()
    part2(input).println()
}
