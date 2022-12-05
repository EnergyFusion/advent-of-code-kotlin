fun main() {

    val playMap = mapOf(
        "A" to RPS.ROCK,
        "B" to RPS.PAPER,
        "C" to RPS.SCISSOR,
        "X" to RPS.ROCK,
        "Y" to RPS.PAPER,
        "Z" to RPS.SCISSOR,
    )

    val myPlayMap = mapOf<String, (RPS) -> RPS>(
        "X" to { it.beats },
        "Y" to { it },
        "Z" to  { it.loses },
    )

    fun calculateScore1(line: String): Int {
        val (theirChar, myChar) = line.split(" ")
        val theirPlay = playMap[theirChar]
        val myPlay = playMap[myChar]

//        println("$myPlay vs $theirPlay = ${myPlay!!.compare(theirPlay!!)}")
        return myPlay!!.compare(theirPlay!!) + myPlay.score
    }

    fun calculateScore2(line: String): Int {
        val (theirChar, myChar) = line.split(" ")
        val theirPlay = playMap[theirChar]
        val myPlay = myPlayMap[myChar]!!(theirPlay!!)

        //        println("$myPlay vs $theirPlay = ${myPlay!!.compare(theirPlay!!)}")
        return myPlay.compare(theirPlay) + myPlay.score
    }

    fun part1(input: List<String>): Int {
        var score = 0
        for (line in input) {
            score += calculateScore1(line)
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        for (line in input) {
            score += calculateScore2(line)
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("tests/Day02")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)
    println("checks passed")

    val input = readInput("input/Day02")
    println(part1(input))
    println(part2(input))
}

enum class RPS {
    ROCK {
        override val beats by lazy { SCISSOR }
        override val loses by lazy { PAPER }
        override val score = 1
    },
    PAPER {
        override val beats = ROCK
        override val loses by lazy { SCISSOR }
        override val score = 2
    },
    SCISSOR {
        override val beats = PAPER
        override val loses = ROCK
        override val score = 3
    };

    abstract val beats : RPS
    abstract val loses: RPS
    abstract val score: Int

    fun compare(other: RPS) = when (other) {
        beats -> 6
        loses -> 0
        else -> 3
    }
}