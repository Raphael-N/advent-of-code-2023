import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val colorToLimit = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return input.map {
            val gameAndColors = it.split(": ")
            gameAndColors[0].split(" ")[1].toInt() to gameAndColors[1]
        }.filter { gameIdAndColor ->
            gameIdAndColor.second.split("; ").all { set ->
                val pairs = set.split(", ").map { colorEntry ->
                    val valAndColor = colorEntry.split(" ")
                    valAndColor[0] to valAndColor[1]
                }
                pairs.all { it.first.toInt() <= colorToLimit[it.second]!! }
            }
        }.sumOf { it.first }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val colorsToReq = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            val colors = it.split(": ")[1]
            colors.split("; ").forEach { gameSet ->
                val pairs = gameSet.split(", ").map { colorEntry ->
                    val valAndColor = colorEntry.split(" ")
                    valAndColor[0] to valAndColor[1]
                }
                pairs.forEach { colorsToReq[it.second] = max(colorsToReq[it.second]!!, it.first.toInt()) }
            }
            colorsToReq
        }.sumOf { it["red"]!! * it["green"]!! * it["blue"]!! }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 2286)


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
