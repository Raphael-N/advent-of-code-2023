fun main() {
    fun getWinningIntersection(numbers: List<String>) = numbers.map {
        it.split(" ").filter { it.isNotBlank() }.map { num -> num.toInt() }.toSet()
    }.reduce { acc, ints ->
        acc.intersect(ints)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val numbers = line.split(": ")[1].split(" | ")
            val result: Int = getWinningIntersection(numbers).fold(0) { sum, _ ->
                if (sum == 0) 1 else sum * 2
            }
            result
        }
    }


    fun part2(input: List<String>): Int {
        val integers: Array<Int> = Array(size = input.size, init = { 1 })

        input.forEachIndexed { lineNum, line ->
            val numbers = line.split(": ")[1].split(" | ")
            getWinningIntersection(numbers).forEachIndexed { index, winningNumber ->
                integers[lineNum + index + 1] += integers[lineNum]
            }
        }
        return integers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    val testInput2 = readInput("Day04_test")
    check(part2(testInput2) == 30)

    println()

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}