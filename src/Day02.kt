fun main() {
    fun part1(input: List<String>): Int {
        val inst = convertToInstruction(input)
        return calcPositionA(inst)
    }

    fun part2(input: List<String>): Int {
        val inst = convertToInstruction(input)
        return calcPositionB(inst)    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun convertToInstruction(input: List<String>): List<Pair<String, Int>> {
    return input.map { line ->
       val (a, b) = line.split(' ', limit =  2)
        a to b.toInt()
    }
}

private fun calcPositionA(input: List<Pair<String, Int>>): Int{
    var horizontal = 0
    var vertical = 0

    input.forEach { (a, b) ->
        when(a) {
            "up" -> vertical -= b
            "down" -> vertical += b
            "forward" -> horizontal +=b
        }
    }

    return horizontal * vertical
}

private fun calcPositionB(input: List<Pair<String, Int>>): Int{
    var horizontal = 0
    var vertical = 0
    var aim = 0

    input.forEach { (a, b) ->
        when(a) {
            "up" -> aim -= b
            "down" -> aim += b
            "forward" -> { horizontal +=b; vertical += aim * b }
        }
    }

    return horizontal * vertical
}
