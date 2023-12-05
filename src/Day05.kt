private const val START = "seed"

fun mapByRanges(ranges: List<Triple<Long, Long, Long>>, number: Long): Long {
    for (i in ranges.indices) {
        val range = ranges[i]
        if (number >= range.first && number <= range.second) {
            return number + range.third
        }
    }
    return number
}

fun main() {

    fun part1(input: List<String>): Long {
        var seeds: Set<Long> = HashSet()
        val xToYMap: MutableMap<String, String> = HashMap()
        val functions: MutableMap<String, (Long) -> Long> = HashMap()

        var currentGoal = ""
        var ranges: MutableList<Triple<Long, Long, Long>> = ArrayList()
        input.forEach { line ->
            if (line.isBlank()) {
                return@forEach
            }
            if (line.startsWith("seeds:")) {
                seeds = line.removePrefix("seeds:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }.toSet()
            }
            if (line.endsWith("map:")) {
                if (ranges.isNotEmpty()) {
                    val test = ArrayList(ranges)
                    functions[currentGoal] = fun(number: Long): Long {
                        return mapByRanges(test, number)
                    }
                    ranges = ArrayList()
                }
                val parts = line.split(" ")[0].split("-")
                xToYMap[parts[0]] = parts[2]
                currentGoal = parts[2]
            }
            if (line[0].isDigit()) {
                val numbers = line.split(" ").map { it.toLong() }
                ranges.add(Triple(numbers[1], numbers[1] + numbers[2], numbers[0] - numbers[1]))
            }
        }
        functions[currentGoal] = fun(number: Long): Long {
            return mapByRanges(ranges, number)
        }

        var i = 0
        val result = seeds.minOf { seed ->
            i++
            if (i % 1000000 == 0) {
                println(i)
            }
            var currentName = START
            var currentValue = seed
            while (currentName in xToYMap) {
                currentName = xToYMap[currentName]!!
                currentValue = functions[currentName]!!(currentValue)
            }
            currentValue
        }

        return result
    }



    fun part2(input: List<String>): Long {
        var seeds: MutableSet<Pair<Long, Long>> = HashSet()
        val xToYMap: MutableMap<String, String> = HashMap()
        val functions: MutableMap<String, (Long) -> Long> = HashMap()

        var currentGoal = ""
        var ranges: MutableList<Triple<Long, Long, Long>> = ArrayList()
        input.forEach { line ->
            if (line.isBlank()) {
                return@forEach
            }
            if (line.startsWith("seeds:")) {
                val seedPairs = line.removePrefix("seeds:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
                for (index in seedPairs.indices) {
                    if ((index + 1) % 2 == 0) {
                        seeds.add(seedPairs[index - 1] to seedPairs[index])
                    }
                }
            }
            if (line.endsWith("map:")) {
                if (ranges.isNotEmpty()) {
                    val test = ArrayList(ranges)
                    functions[currentGoal] = fun(number: Long): Long {
                        return mapByRanges(test, number)
                    }
                    ranges = ArrayList()
                }
                val parts = line.split(" ")[0].split("-")
                xToYMap[parts[0]] = parts[2]
                currentGoal = parts[2]
            }
            if (line[0].isDigit()) {
                val numbers = line.split(" ").map { it.toLong() }
                ranges.add(Triple(numbers[1], numbers[1] + numbers[2], numbers[0] - numbers[1]))
            }
        }
        functions[currentGoal] = fun(number: Long): Long {
            return mapByRanges(ranges, number)
        }

        val totalTotal = seeds.sumOf { it.second }

        var i = 0L
        var total = 0L
        return seeds.minOf { seed ->
            (seed.first..<(seed.first + seed.second)).minOf {
                i++
                if (i > 1000000) {
                    total += i
                    println(total.toDouble() / totalTotal)
                    i = 0
                }
                var currentName = START
                var currentValue = it
                while (currentName in xToYMap) {
                    currentName = xToYMap[currentName]!!
                    currentValue = functions[currentName]!!(currentValue)
                }
                currentValue
            }
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    part1(testInput).println()
    check(part1(testInput) == 35L)
    val testInput2 = readInput("Day05_test")
    part2(testInput2).println()
    check(part2(testInput2) == 46L)

    println()

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}