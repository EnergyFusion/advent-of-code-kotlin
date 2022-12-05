import java.io.BufferedReader

fun main() {

    val day = 3

    fun calculatePriority(char: Char):Int {
        return if (char.isUpperCase()) {
            char.code - 38
        } else {
            char.code - 96
        }
    }

    fun part1(inputReader: BufferedReader): Int {
        var totalPriority = 0
        inputReader.forEachLine { line ->
            val mid = line.length / 2
            val container1 = line.substring(0, mid).toSet()
            val container2 = line.substring(mid).toSet()
            val commonChars = container1.filter { char -> container2.contains(char) }
//            println(commonChars)
            if (commonChars.size == 1) {
                totalPriority += calculatePriority(commonChars[0])
            }
        }
        return totalPriority
    }

    fun part2(inputReader: BufferedReader): Int {
        var totalPriority = 0
        inputReader.useLines {
            it.chunked(3).forEach { group ->
                val groupSets = group.map { sack -> sack.toSet() }.sortedBy { sackSet -> sackSet.size }
                val commonChars = groupSets[0].filter { char -> groupSets[1].contains(char) && groupSets[2].contains(char) }
//                println(commonChars)
                if (commonChars.size == 1) {
                    totalPriority += calculatePriority(commonChars[0])
                }
            }
        }
        return totalPriority
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    println(part1(testInputReader))
//    check(part1(testInputReader) == 157)
    check(part2(testInputReader) == 70)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}