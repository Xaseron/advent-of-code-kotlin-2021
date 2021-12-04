fun main() {
    fun part1(input: List<String>): Int {
        return solveA(input)
    }

    fun part2(input: List<String>): Int {
        return solveB(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    check(part1(input) == 29440)
    println(part1(input))
    println(part2(input))
}

private fun parseBingos(input: List<String>): List<Bingo> {
    val list = mutableListOf<List<Int>>()
    val bingos = mutableListOf<Bingo>()
    input.forEach { line ->
        if (line.isNotBlank()) {
            list.add(line.trim().split("\\s+".toRegex()).map { it.toInt() })
        } else if (list.isNotEmpty()) {
            bingos.add(Bingo(list))
            list.clear()
        }
    }
    if (list.isNotEmpty()) {
        bingos.add(Bingo(list))
    }
    return bingos
}

private fun solveA(input: List<String>): Int {
    val numbers  = input.first().split(",").map { it.toInt() }
    val bingos = parseBingos(input.drop(1))
    numbers.forEach { num ->
        val bingo = findBingo(num, bingos)
        if (bingo.isNotEmpty()) {
            return bingo.first().calcResult(num)
        }
    }

    return -1
}

private fun solveB(input: List<String>): Int {
    val numbers  = input.first().split(",").map { it.toInt() }
    val bingos = parseBingos(input.drop(1)).toMutableList()
    numbers.forEach { num ->
        val bingo = findBingo(num, bingos)
        if (bingo.isNotEmpty()) {
            if (bingos.size == 1) {
                return bingo.first().calcResult(num)
            }
            bingos.removeAll(bingo)
        }
    }

    return -1
}

private fun findBingo(num: Int, bingos: List<Bingo>): List<Bingo> {
    return bingos.mapNotNull { bingo ->
        bingo.markNumber(num)
        if (bingo.hasBingo()) {
             bingo
        }
        else {
            null
        }
    }
}

private class Bingo(input: List<List<Int>>) {
    private val rows = input.map { it.toMutableList() }.toMutableList()
    private val columns = input.indices.map { i ->
        input.map { it[i] }.toMutableList()
    }.toMutableList()

    fun markNumber(num: Int) {
        rows.forEach { list-> list.remove(num)  }
        columns.forEach { list-> list.remove(num)  }
    }

    fun hasBingo(): Boolean =
        rows.any { it.isEmpty() } || columns.any { it.isEmpty() }

    fun calcResult(num: Int) =
        num * rows.sumOf { list -> list.sum() }
}
