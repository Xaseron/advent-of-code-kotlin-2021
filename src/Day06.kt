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
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
//    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    check(part1(input) == 379114)
    println(part1(input))
    println(part2(input))
}

private fun solveA(input: List<String>): Int {
    var foo = input.first().split(",").map { it.toInt() }.toMutableList()

    repeat(256) {
        val zeros = foo.count { it == 0 }
        foo.replaceAll { if (it == 0)  7 else it }
        foo.addAll(List(zeros) { _ -> 9 })
        foo = foo.map { it - 1 }.toMutableList()
    }


    return foo.size
}

private fun solveB(input: List<String>): Int {
return 0
}

