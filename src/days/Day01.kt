fun main() {
    fun part1(input: List<String>): Int {
        val elfCalorieMap = mutableMapOf<Int, Int>()
        var currentElf = 0
        for (line in input) {
            if (line.isBlank()) {
                currentElf++
            } else {
                val calorie = line.toInt()
                val currentVal = elfCalorieMap.getOrDefault(currentElf, 0)
                elfCalorieMap[currentElf] = currentVal + calorie
            }
        }
        return elfCalorieMap.values.maxOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        var elfCalorieMap = mutableMapOf<Int, Int>()
        var currentElf = 0
        for (line in input) {
            if (line.isBlank()) {
                currentElf++
                if (elfCalorieMap.size > 3) {
                    elfCalorieMap = elfCalorieMap.toList().sortedBy { (_, value) -> value }.takeLast(3).toMap().toMutableMap()
                }
            } else {
                val calorie = line.toInt()
                val currentVal = elfCalorieMap.getOrDefault(currentElf, 0)
                elfCalorieMap[currentElf] = currentVal + calorie
            }
        }
        if (elfCalorieMap.size > 3) {
            elfCalorieMap = elfCalorieMap.toList().sortedBy { (_, value) -> value }.takeLast(3).toMap().toMutableMap()
        }
        return elfCalorieMap.values.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("tests/Day01")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("input/Day01")
//    println(part1(input))
    println(part2(input))
}
