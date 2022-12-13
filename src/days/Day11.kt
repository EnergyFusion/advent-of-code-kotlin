import java.io.BufferedReader

fun main() {
    val day = 11

    fun createMonkeys(inputReader: BufferedReader): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        var currentListOfItems = mutableListOf<Long>()
        var testStrings = mutableListOf<String>()
        var testDiv = 0L
        lateinit var operation: (Long) -> Long
        lateinit var test: (Long) -> Int
        inputReader.forEachLine { line ->
            when {
                line.trim().isEmpty() -> {
                    monkeys.add(Monkey(0L, testDiv, currentListOfItems, operation, test))
                    currentListOfItems = mutableListOf()
                    testStrings = mutableListOf()
                }
                line.trim().startsWith("Starting items:") -> {
                    val numsString = line.substringAfter(":").trim()
                    val nums = numsString.split(",")
                    for (num in nums) {
                        currentListOfItems.add(num.trim().toLong())
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
                                {old -> old + v.toLong()}
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
                    val div = testStrings[0].split(" ").last().trim().toLong()
                    testDiv = div
                    val tMonkeyIndex = testStrings[1].split(" ").last().trim().toInt()
                    val fMonkeyIndex = testStrings[2].split(" ").last().trim().toInt()
                    test = {worry ->
                        if (worry % div == 0L) {
                            tMonkeyIndex
                        } else {
                            fMonkeyIndex
                        }
                    }
                }
                else -> {}
            }
        }
        monkeys.add(Monkey(0, testDiv, currentListOfItems, operation, test))
        return monkeys.toList()
    }

    fun playRounds(monkeys: List<Monkey>, rounds: Int, worryLevelFun: (Long) -> Long) {
        for (i in 0..rounds) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.inspections++
                    var worryLevel = monkey.operation(item)
                    worryLevel = worryLevelFun(worryLevel)
                    val nextMonkey = monkey.test(worryLevel)
                    monkeys[nextMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
        }
    }

    fun part1(inputReader: BufferedReader): Long {

        val monkeys = createMonkeys(inputReader)
//        println(monkeys)

        playRounds(monkeys, 19) {worryLevel -> worryLevel/3}

//        println()
//        println(monkeys)
//        println()
        val result = monkeys.map { it.inspections }.sorted().takeLast(2).reduce{ left, right -> left * right }
        println(result)
        println()
        return result
    }

    fun part2(inputReader: BufferedReader): Long {

        val monkeys = createMonkeys(inputReader)
//        println(monkeys)
        val modProduct = monkeys.map { it.testDiv }.reduce {left, right -> left*right}
        playRounds(monkeys, 9999) {worryLevel -> worryLevel%modProduct}

//        println(monkeys)
        val result = monkeys.map { it.inspections }.sorted().takeLast(2).reduce{ left, right -> left * right }
        println(result)
        return result
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 10605L)
        check(part2(testInputReader) == 2713310158L)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}

data class Monkey(var inspections: Long,
                  val testDiv: Long,
                  val items: MutableList<Long>,
                  val operation: (Long) -> Long,
                  val test: (Long) -> Int)