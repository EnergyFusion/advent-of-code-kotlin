import java.io.BufferedReader

fun main() {

    val day = 5

    fun buildStacks(map: List<String>): List<ArrayDeque<Char>> {
        val numberOfColumns = map.last().trim().last().toString().toInt()
        val stacks = List(numberOfColumns) { ArrayDeque<Char>() }
        map.dropLast(1).reversed().forEach { line ->
            line.chunked(4).forEachIndexed { i, item ->
                if (item.isNotBlank()) {
                    stacks[i].addLast(item[1])
                }
            }
        }
        return stacks
    }

    fun performMove1(stacks: List<ArrayDeque<Char>>, numOfElements: Int, fromIndex: Int, toIndex: Int) {
        var numOfE = numOfElements
        while (numOfE > 0) {
            val element = stacks[fromIndex].removeLast()
            stacks[toIndex].addLast(element)
            numOfE--
        }
    }

    fun part1(inputReader: BufferedReader): String {
        var message = ""
        inputReader.useLines {
            val (map, moves) = it.partition { line ->
                line.matches("^[0-9 ]+$".toRegex()) || line.matches("^[A-Z \\[\\]]+$".toRegex())
            }
            val stacks = buildStacks(map)
//            println(columns)
            moves.drop(1).forEach { line ->
                val elements = line.split(" ")
                var numOfElements = elements[1].toInt()
                val fromIndex = elements[3].toInt() - 1
                val toIndex = elements[5].toInt() - 1
                performMove1(stacks, numOfElements, fromIndex, toIndex)
            }
//            println(columns)
            message = String(stacks.map { it.removeLast() }.toCharArray())
//            println(message)
        }
        return message
    }

    fun performMove2(stacks: MutableList<ArrayDeque<Char>>, numOfElements: Int, fromIndex: Int, toIndex: Int) {
        val stack = stacks[fromIndex]
        stacks[fromIndex] = ArrayDeque(stack.subList(0, stack.size - numOfElements))
        stacks[toIndex].addAll(stack.subList(stack.size - numOfElements, stack.size))
    }

    fun part2(inputReader: BufferedReader): String {
        var message = ""
        inputReader.useLines {
            val (map, moves) = it.partition { line ->
                line.matches("^[0-9 ]+$".toRegex()) || line.matches("^[A-Z \\[\\]]+$".toRegex())
            }
            val stacks = buildStacks(map).toMutableList()
            //            println(columns)
            moves.drop(1).forEach { line ->
                val elements = line.split(" ")
                var numOfElements = elements[1].toInt()
                val fromIndex = elements[3].toInt() - 1
                val toIndex = elements[5].toInt() - 1
                performMove2(stacks, numOfElements, fromIndex, toIndex)
            }
            println(stacks)
            message = String(stacks.map { it.removeLast() }.toCharArray())
        //            println(message)
        }
        return message
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == "CMZ")
    check(part2(testInputReader) == "MCD")

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}