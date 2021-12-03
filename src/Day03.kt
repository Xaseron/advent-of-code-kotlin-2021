fun main() {
    fun part1(input: List<String>): Int {
        return solveA(input)
    }

    fun part2(input: List<String>): Int {
        return solveB(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun convertToInts(input: List<String>) =
    input.map { line -> line.map { it.lowercase().toInt() } }


private fun solveA(input: List<String>): Int {
    val foo = convertToInts(input)
    val size = input.size
    val gamma = List(foo.first().size) { index ->
        val ones = foo.count { it[index] == 1 }
        if (ones > size / 2)
            1
        else
            0
    }
    val gammaStr = gamma.joinToString(separator = "")
    val epsilonStr = gammaStr.binInvert()
    return Integer.parseInt(gammaStr, 2) * Integer.parseInt(epsilonStr, 2)
}

private fun solveB(input: List<String>): Int {
    val foo = convertToInts(input)
    val oxygen = filterFun(foo, true)
    val co2 = filterFun(foo, false)
    return oxygen.toBinInt() * co2.toBinInt()
}

private fun filterFun(input: List<List<Int>>, takeMostCommon: Boolean): List<Int> {
    return when {
        input.isEmpty() -> emptyList()
        input.size == 1 -> input.first()
        else -> {
            val ones = input.count { it.first() == 1 }
            val size = input.size
            val filterForNum = when {
                (size % 2 == 0) && (ones == size / 2) -> if (takeMostCommon) 1 else 0
                ones > size / 2 -> if (takeMostCommon) 1 else 0
                else -> if (takeMostCommon) 0 else 1
            }
            val mapNotNull = input.mapNotNull {
                if (it.first() == filterForNum) {
                    it.drop(1)
                } else {
                    null
                }
            }
            listOf(filterForNum) + filterFun(mapNotNull, takeMostCommon)
        }
    }
}

private fun String.binInvert() =
    this.map {
        when (it) {
            '0' -> '1'
            '1' -> '0'
            else -> throw Exception("lol")
        }
    }.joinToString(separator = "")

private fun List<Int>.toBinInt() =
    Integer.parseInt(this.joinToString(separator = ""), 2)
