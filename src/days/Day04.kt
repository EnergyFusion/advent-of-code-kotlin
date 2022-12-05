import java.io.BufferedReader

fun main() {

    val day = 4

    fun generateList(start: Int, end: Int) = List(end + 1 - start) { it + start }

    fun part1(inputReader: BufferedReader): Int {
        var count = 0
        inputReader.forEachLine { line ->
            val (s1, s2) = line.split(",")

            //Set 1
            val (s1s, s1e) = s1.split("-")
            val s1List = generateList(s1s.toInt(), s1e.toInt())

            //Set 2
            val (s2s, s2e) = s2.split("-")
            val s2List = generateList(s2s.toInt(), s2e.toInt())

            if (s1List.containsAll(s2List) || s2List.containsAll(s1List)) {
                count++
            }
        }
        return count
    }

    fun part2(inputReader: BufferedReader): Int {
        var count = 0
        inputReader.forEachLine { line ->
            val (s1, s2) = line.split(",")

            //Set 1
            val (s1s, s1e) = s1.split("-")
            val s1List = generateList(s1s.toInt(), s1e.toInt())

            //Set 2
            val (s2s, s2e) = s2.split("-")
            val s2List = generateList(s2s.toInt(), s2e.toInt())

            if (s1List.any { it in s2List.toSet()}) {
                count++
            }
        }
        return count
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 2)
    check(part2(testInputReader) == 4)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}