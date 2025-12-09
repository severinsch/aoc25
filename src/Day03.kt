
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { bank ->
            var firstDigit = Char.MIN_VALUE
            var fistDigitIdx: Int = -1
            for (index in 0..<bank.length-1) {
                if(bank[index] > firstDigit) {
                    firstDigit = bank[index]
                    fistDigitIdx = index
                }
            }
            val secondDigit = bank.drop(fistDigitIdx+1).max()
            "$firstDigit$secondDigit".toInt()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { bank ->
            var startIndex = 0
            val digits = mutableListOf<Char>()
            for(i in 11 downTo 0) {
                var currentMax = Char.MIN_VALUE
                var currentMaxIdx = -1
                for (index in startIndex..<bank.length-i) {
                    if(bank[index] > (currentMax)) {
                        currentMax = bank[index]
                        currentMaxIdx = index
                    }
                }
                digits.add(currentMax)
                startIndex = currentMaxIdx + 1
            }
            return@sumOf digits.joinToString("").toLong()
        }
    }

    val input = readInput("day03")
    part1(input).println()
    part2(input).println()
}
