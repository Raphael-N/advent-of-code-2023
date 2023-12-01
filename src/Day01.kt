import java.lang.NumberFormatException

fun main() {


    fun part1(input: List<String>): Int {
        return input.sumOf { "${it.first { char -> char.isDigit() }}${it.last { char -> char.isDigit() }}".toInt() }
    }

    val possibleValues = setOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    fun part2(input: List<String>): Int {
        return input.sumOf {
            val firstDigit: String = it.findAnyOf(possibleValues)?.second!!.writtenToDigit()
            val lastDigit = it.findLastAnyOf(possibleValues)?.second!!.writtenToDigit()
            "$firstDigit$lastDigit".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_2_test")
    check(part2(testInput2) == 281)


    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun String.writtenToDigit(): String {
    return when (this) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> this
    }
}

