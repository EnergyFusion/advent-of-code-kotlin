import java.io.BufferedReader

fun main() {
    val day = 11

    fun createMonkeys(inputReader: BufferedReader): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        var currentListOfItems = mutableListOf<Int>()
        var testStrings = mutableListOf<String>()
        lateinit var operation: (Int) -> Int
        lateinit var test: (Int) -> Int
        inputReader.forEachLine { line ->
            when {
                line.trim().isEmpty() -> {
                    monkeys.add(Monkey(0, currentListOfItems, operation, test))
                    currentListOfItems = mutableListOf()
                    testStrings = mutableListOf()
                }
                line.trim().startsWith("Starting items:") -> {
                    val numsString = line.substringAfter(":").trim()
                    val nums = numsString.split(",")
                    for (num in nums) {
                        currentListOfItems.add(num.trim().toInt())
                    }
                }
                line.trim().startsWith("Operation:") -> {
                    val opString = line.substringAfter(":").substringAfter("=").substringAfter("d").trim()
                    val ops = opString.split(" ")
                    val op = ops[0].trim()
                    val v = ops[1].trim()
                    when (op) {
                        "*" -> {
                            operation = if (v == "old") {
                                {old -> old * old}
                            } else {
                                {old -> old * v.toInt()}
                            }
                        }
                        "+" -> {
                            operation = if (v == "old") {
                                {old -> old + old}
                            } else {
                                {old -> old + v.toInt()}
                            }
                        }
                    }
                }
                line.trim().startsWith("Test:") -> {
                    testStrings.add(line.substringAfter(":"))
                }
                line.trim().startsWith("If true:") -> {
                    testStrings.add(line.substringAfter(":"))
                }
                line.trim().startsWith("If false:") -> {
                    testStrings.add(line.substringAfter(":").trim())
                    val div = testStrings[0].split(" ").last().trim().toInt()
                    val tMonkeyIndex = testStrings[1].split(" ").last().trim().toInt()
                    val fMonkeyIndex = testStrings[2].split(" ").last().trim().toInt()
                    test = {worry ->
                        if (worry % div == 0) {
                            tMonkeyIndex
                        } else {
                            fMonkeyIndex
                        }
                    }
                }
                else -> {}
            }
        }
        monkeys.add(Monkey(0, currentListOfItems, operation, test))
        return monkeys.toList()
    }

    fun part1(inputReader: BufferedReader): Int {

        val monkeys = createMonkeys(inputReader)
//        println(monkeys)

        for (i in 0..19) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.inspections++
                    var worryLevel = monkey.operation(item)
                    worryLevel /= 3
                    val nextMonkey = monkey.test(worryLevel)
                    monkeys[nextMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
        }

//        println(monkeys)
        val result = monkeys.map { it.inspections }.sorted().takeLast(2).reduce{ left, right -> left * right }
        println(result)
        return result
    }

    fun part2(inputReader: BufferedReader): Int {

        val result = 0
        println(result)
        return result
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
    check(part1(testInputReader) == 10605)
//    check(part2(testInputReader) == 0)

    val input = readBufferedInput("input/Day%02d".format(day))
    println(part1(input))
//    println(part2(input))
}

data class Monkey(var inspections: Int, val items: MutableList<Int>, val operation: (Int) -> Int, val test: (Int) -> Int)