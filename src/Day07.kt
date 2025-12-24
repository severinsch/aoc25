
fun main() {
    fun part1(input: List<String>): Long {
        val initialIndex = input.first().indexOf('S')
        return input.drop(1).fold(Pair(0L, mutableSetOf(initialIndex))) {
            (splits, beamIndices), line ->
            val splitIndices = line.withIndex().filter {
                it.value == '^' && it.index in beamIndices
            }.map { it.index }
            beamIndices.removeIf { it in splitIndices }
            beamIndices.addAll(splitIndices.flatMap { listOf(it-1,it+1) }.filter { it in line.indices})
            return@fold Pair(splits + splitIndices.count(), beamIndices)
        }.first
    }

    fun part2(input: List<String>): Long {
        val initialIndex = input.first().indexOf('S')
        return input.drop(1).fold(mutableMapOf(initialIndex to 1L)) {
                beamIndices, line ->
            line.withIndex().filter {
                it.value == '^' && it.index in beamIndices.keys
            }.forEach {
                val particlesAtIdx = beamIndices.remove(it.index) ?: 0
                beamIndices.compute(it.index+1) {
                    _, v -> (v ?: 0) + particlesAtIdx
                }
                beamIndices.compute(it.index-1) {
                        _, v -> (v ?: 0) + particlesAtIdx
                }
            }
            return@fold beamIndices
        }.filter { it.key in input.first().indices }.values.sum()
    }

    val input = readInput("day07")
    part1(input).println()
    part2(input).println()
}
