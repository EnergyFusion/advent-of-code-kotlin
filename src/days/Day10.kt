import java.io.BufferedReader

fun main() {
    val day = 10

    fun part1(inputReader: BufferedReader): Int {
        val signalStrengthMap = mutableMapOf(20 to 1, 60 to 1, 100 to 1, 140 to 1, 180 to 1, 220 to 1)
        var cycle = 1
        var regX = 1

        inputReader.forEachLine { line ->
            if (line.trim() == "noop") {
                for (i in cycle .. (cycle + 1)) {
                    if (signalStrengthMap.containsKey(i)) {
                        signalStrengthMap[i] = regX*i
                    }
                }
                cycle += 1
            } else {
                val split = line.split(" ")
                val value = split[1].toInt()
                for (i in cycle .. (cycle + 2)) {
                    if (signalStrengthMap.containsKey(i)) {
                        signalStrengthMap[i] = regX*i
                    }
                }
                cycle += 2
                regX += value
            }
        }

        val result = signalStrengthMap.values.sum()
        println(result)
        return result
    }

    fun printCycle(cycle: Int, regX: Int) {
        if ((cycle%40) in (regX-1)..(regX+1)) {
            print("#")
        } else {
            print('.')
        }
    }

    fun printNewLine(cycle: Int) {
        if (cycle%40 == 0) {
            println()
        }
    }

    fun part2(inputReader: BufferedReader): Int {
        var cycle = 0
        var regX = 1

        inputReader.forEachLine { line ->
            if (line.trim() == "noop") {
                printCycle(cycle, regX)
                cycle++
                printNewLine(cycle)
            } else {
                printCycle(cycle, regX)
                cycle++
                printNewLine(cycle)

                printCycle(cycle, regX)
                cycle++
                printNewLine(cycle)

                val split = line.split(" ")
                val value = split[1].toInt()
                regX += value
            }
        }

        val result = 0
        println()
        println(result)
        return result
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 13140)
    check(part2(testInputReader) == 0)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}