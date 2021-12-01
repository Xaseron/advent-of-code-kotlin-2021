fun main() {
    fun part1(input: List<String>): Int {
        val intList = input.map { it.toInt() }
        val depths = convertToDepthA(intList)
        return depths.count { it == Depth.INCREASED }
    }

    fun part2(input: List<String>): Int {
        val intList = input.map { it.toInt() }
        val depths = convertToDepthB(intList)
        return depths.count { it == Depth.INCREASED }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun convertToDepthA(input: List<Int>): List<Depth> =
    input.zipWithNext { a, b ->
        compareDepths(a, b)
    }

private fun compareDepths(a: Int, b: Int): Depth {
    return when {
        a < b -> Depth.INCREASED
        a > b -> Depth.DECREASED
        else -> Depth.SAME
    }
}

private fun convertToDepthB(input: List<Int>): List<Depth> {
    val foo = input.zipWithNext()
    val bar = input.drop(2).zipWithNext()

    return foo.zip(bar) { (a, b), (c, d) ->
        val sum1 = a + b + c
        val sum2 = b + c + d
        compareDepths(sum1, sum2)
    }
}

private enum class Depth {
    INCREASED,
    SAME,
    DECREASED,
}
