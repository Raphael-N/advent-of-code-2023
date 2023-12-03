fun main() {
    val digits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val irrelevantNeighbours = digits + charArrayOf('.')

    fun findNextNumber(pos: Int, input: String): Pair<Int, Int>? {
        val nextNumberStart = input.indexOfAny(digits, startIndex = pos)
        if (nextNumberStart == -1) {
            return null
        }
        var nextNumberEnd = nextNumberStart
        while (nextNumberEnd < input.length && input[nextNumberEnd] in digits) {
            nextNumberEnd++
        }
        return nextNumberStart to nextNumberEnd
    }

    fun hasSpecialCharNeighbour(input: List<String>, line: Int, start: Int, end: Int): Boolean {
        for (y in line - 1..line + 1) {
            for (x in start - 1..end) {
                if (y < 0 || y >= input.size || x < 0 || x >= input[y].length || (y == line && x >= start && x < end)) {
                    continue
                }
                if (!irrelevantNeighbours.contains(input[y][x])) {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { lineNum, line ->
            var position = 0
            result += generateSequence {
                val nextNumberPos = findNextNumber(position, line)
                position = (nextNumberPos?.second ?: 0) + 1
                nextNumberPos
            }.sumOf {
                if (hasSpecialCharNeighbour(input, lineNum, it.first, it.second)) {
                    line.substring(
                        it.first,
                        it.second
                    ).toInt()
                } else 0
            }
        }
        return result
    }

    fun completeNumber(
        x: Int,
        input: List<String>,
        y: Int,
        handled: MutableSet<Pair<Int, Int>>
    ): String {
        var number = ""
        var currentX = x
        while (input[y][currentX].isDigit()) {
            handled.add(y to currentX)
            number = "$number${input[y][currentX]}"
            currentX++
            if (currentX >= input[y].length) {
                break
            }
        }
        if (x == 0) {
            return number
        }
        currentX = x - 1
        while (input[y][currentX].isDigit()) {
            handled.add(y to currentX)
            number = "${input[y][currentX]}$number"
            currentX--
            if (currentX < 0) {
                return number
            }
        }
        return number
    }

    fun getNeighborNumbers(input: List<String>, line: Int, col: Int): List<Int> {
        val handled: MutableSet<Pair<Int, Int>> = HashSet()
        val numbers: MutableList<Int> = ArrayList()
        for (y in line - 1..line + 1) {
            for (x in col - 1..col + 1) {
                if (y < 0 || y >= input.size || x < 0 || x >= input[y].length || (line == y && col == x) || (y to x in handled)) {
                    continue
                }
                if (input[y][x].isDigit()) {
                    numbers.add(completeNumber(x, input, y, handled).toInt())
                }
            }
        }
        return numbers
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { lineNum, line ->
            line.forEachIndexed { colNum, col ->
                if (col != '*') {
                    return@forEachIndexed
                }
                val neighbours = getNeighborNumbers(input, lineNum, colNum)
                result += if (neighbours.size == 2) neighbours[0] * neighbours[1] else 0
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    val testInput2 = readInput("Day03_test")
    check(part2(testInput2) == 467835)

    println()

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}