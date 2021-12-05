import org.jetbrains.kotlinx.multik.api.empty
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.count
import org.jetbrains.kotlinx.multik.ndarray.operations.plus

fun main() {
    fun part1(input: List<String>): Int {
        return solveA(input)
    }

    fun part2(input: List<String>): Int {
        return solveB(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    check(part1(input) == 6397)
    println(part1(input))
    println(part2(input))
}

private fun solveA(input: List<String>): Int {
    val foo = parse(input)
    val ndArray = mk.zeros<Int>(1000, 1000)

    foo.forEach { (xList, yList) ->
        val xRange = xList.sorted().let { it.first()..it.last() }
        val yRange = yList.sorted().let { it.first()..it.last() }
        when {
            xRange.first == xRange.last -> yRange.forEach { ndArray[it, xRange.first] += 1 }
            yRange.first == yRange.last -> xRange.forEach { ndArray[yRange.first, it] += 1 }
        }

    }

    return ndArray.count { it > 1 }
}

private fun solveB(input: List<String>): Int {
    val foo = parse(input)
    val ndArray = mk.zeros<Int>(1000, 1000)

    foo.forEach { (xList, yList) ->
        val xRange = xList.sorted().let { it.first()..it.last() }
        val yRange = yList.sorted().let { it.first()..it.last() }

        if (xRange.toList().size == yRange.toList().size) {
            val (x1, x2) = xList
            val (y1, y2) = yList
            val r1 = if (x1 < x2) x1..x2 else x1 downTo x2
            val r2 = if (y1 < y2) y1..y2 else y1 downTo y2
            r1.toList().zip(r2.toList()).forEach { (x, y) ->
                ndArray[y, x] += 1
            }
        }

        when {
            xRange.first == xRange.last -> yRange.forEach { ndArray[it, xRange.first] += 1 }
            yRange.first == yRange.last -> xRange.forEach { ndArray[yRange.first, it] += 1 }
        }
    }

    return ndArray.count { it > 1 }
}

private fun parse(input: List<String>): List<Pair<List<Int>, List<Int>>> {
    return input.map { line ->
        val (x1y1, x2y2) = line.split("->")
        val (x1, y1) = x1y1.split(",").map { it.trim().toInt() }
        val (x2, y2) = x2y2.split(",").map { it.trim().toInt() }
        listOf(x1, x2) to listOf(y1, y2)
    }
}
