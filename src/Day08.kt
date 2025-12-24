import kotlin.math.sqrt

typealias Position = Triple<Int,Int,Int>

fun <T> allPairs(arr: List<T>): Sequence<Pair<T, T>> = sequence {
    for(i in 0 until arr.size-1)
        for(j in i+1 until arr.size)
            yield(arr[i] to arr[j])
}

fun distance(pos1: Position, pos2: Position): Double {
    return sqrt(
        (pos1.first - pos2.first).toDouble() * (pos1.first - pos2.first).toDouble() +
                (pos1.second - pos2.second).toDouble() * (pos1.second - pos2.second).toDouble() +
                (pos1.third - pos2.third).toDouble() * (pos1.third - pos2.third).toDouble()
    )
}

fun main() {

    fun part1(input: List<String>): Int {
        val coords = input.map { line ->
            val (x,y,z) = line.split(",")
            Triple(x.toInt(),y.toInt(),z.toInt())
        }.withIndex().toList()

        val allPairsByDistance = allPairs(coords).sortedBy { distance(it.first.value, it.second.value) }.toMutableList()
        var circuits = listOf<Set<Int>>()

        repeat(1000) {
            val newConn = allPairsByDistance.removeFirst()
            val (usedCircuits, otherCircuits) = circuits.partition { it.contains(newConn.first.index) || it.contains(newConn.second.index) }

            val newCircuit = usedCircuits.fold(setOf(newConn.first.index, newConn.second.index)) { acc, mutableSet -> acc.union(mutableSet) }
            circuits = listOf(newCircuit) + otherCircuits
        }
        // assumes that there are at least 3 circuits of size > 1
        return circuits.map { it.size }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val inputSize = input.size

        val coords = input.map { line ->
            val (x,y,z) = line.split(",")
            Triple(x.toInt(),y.toInt(),z.toInt())
        }.withIndex().toList()

        val allPairsByDistance = allPairs(coords).sortedBy { distance(it.first.value, it.second.value) }.toMutableList()
        var circuits = listOf<Set<Int>>()

        while(true) {
            val newConn = allPairsByDistance.removeFirst()
            val (usedCircuits, otherCircuits) = circuits.partition { it.contains(newConn.first.index) || it.contains(newConn.second.index) }

            val newCircuit = usedCircuits.fold(setOf(newConn.first.index, newConn.second.index)) { acc, mutableSet -> acc.union(mutableSet) }
            if(newCircuit.size == inputSize) {
                return newConn.first.value.first.toLong() * newConn.second.value.first.toLong()
            }
            circuits = listOf(newCircuit) + otherCircuits
        }
    }

    val input = readInput("day08")
    part1(input).println()
    part2(input).println()
}
