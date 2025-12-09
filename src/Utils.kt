import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.text.replaceRange

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("inputs/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

typealias Coords = Pair<Int, Int>

operator fun List<String>.get(point: Coords): Char {
    return this[point.second][point.first]
}

operator fun MutableList<String>.set(point: Coords, value: Char) {
    this[point.second] = this[point.second].replaceRange(point.first, point.first + 1, value.toString())
}

fun List<String>.getInt(point: Coords): Int {
    return this[point.second][point.first].digitToInt()
}

fun List<String>.isValidPoint(point: Coords): Boolean {
    return point.second in indices && point.first in this[0].indices
}

fun List<String>.allCoords(): List<Coords> {
    val res = mutableListOf<Coords>()
    for (y in this.indices) {
        for (x in this[0].indices) {
            res.add(Pair(x, y))
        }
    }
    return res
}

fun List<String>.getAllSurrounding(point: Coords): List<Char> {
    val (x, y) = point
    return listOf(
        Pair(x - 1, y),
        Pair(x + 1, y),
        Pair(x, y - 1),
        Pair(x, y + 1),
        Pair(x - 1, y - 1),
        Pair(x - 1, y + 1),
        Pair(x + 1, y - 1),
        Pair(x + 1, y + 1),
    ).filter { this.isValidPoint(it) }.map { get(it) }
}

fun List<String>.iterateWithCoords(): Sequence<Pair<Char, Coords>> {
    return sequence {
        for (y in this@iterateWithCoords.indices) {
            for (x in this@iterateWithCoords[0].indices) {
                val coords = Pair(x, y)
                yield(Pair(this@iterateWithCoords[coords], coords))
            }
        }
    }
}