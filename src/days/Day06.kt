import java.io.BufferedReader

fun main() {
    val day = 6

    fun part1(input: List<String>): Int {
        val lineChars = input[0].toCharArray()
        var index = 0
        while (index < lineChars.size-4) {
            val setOfMarkers = lineChars.slice(IntRange(index, index + 3)).toSet()
            if (setOfMarkers.size == 4) {
                break
            }
            index++
        }
        return index + 4
    }

    fun part2(input: List<String>): Int {
        val lineChars = input[0].toCharArray()
        var index = 0
        while (index < lineChars.size-14) {
            val setOfMarkers = lineChars.slice(IntRange(index, index + 13)).toSet()
            if (setOfMarkers.size == 14) {
                break
            }
            index++
        }
        return index + 14
    }

    val testInput = readInput("tests/Day%02d".format(day))
//    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}