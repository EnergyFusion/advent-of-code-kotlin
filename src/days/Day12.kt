import java.io.BufferedReader

fun main() {
    val day = 12

    fun getHeight(char: Char):Int {
        return if (char.isUpperCase()) {
            if (char == 'S') {
                0
            } else {
                27
            }
        } else {
            char.code - 96
        }
    }

    fun setupMapAndStepNodes(stepNodes: MutableList<StepNode>, inputReader: BufferedReader): Array<Array<StepNode>> {
        lateinit var map: Array<Array<StepNode>>
        var i = 0
        var width: Int
        inputReader.forEachLine { line ->
            val row = line.toCharArray()
            if (i == 0) {
                width = row.size
                map = Array(width) { r -> Array(width, init = { c -> StepNode(coords = Pair(r,c)) }) }
            }
            row.forEachIndexed{ j, char ->
//                print("%02d ".format(getHeight(char)))
                val node = StepNode(height = getHeight(char), coords = Pair(i,j))
                stepNodes.add(node)
                map[i][j] = node
            }
//            println()
            i++
        }
        return map
    }

    fun checkNeighbors(map: Array<Array<StepNode>>, i: Int, j: Int): List<StepNode> {
        val neighbours = mutableListOf<StepNode>()
        val currH = map[i][j].height
        //Check left
        if (j > 0 && map[i][j-1].height <= currH+1) {
            neighbours.add(map[i][j-1])
        }
        //Check up
        if (i > 0 && map[i-1][j].height <= currH+1) {
            neighbours.add(map[i-1][j])
        }
        //Check right
        if (j < map[i].size-1 && map[i][j+1].height <= currH+1) {
            neighbours.add(map[i][j+1])
        }
        //Check down
        if (i < map.size-1 && map[i+1][j].height <= currH+1) {
            neighbours.add(map[i+1][j])
        }
        return neighbours
    }

    fun updateNeighbors(map: Array<Array<StepNode>>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var heighestPoint = Pair(0, 0)
        var startPoint = Pair(0, 0)
        var i = 0
        while (i < map.size) {
            var j = 0
            while (j < map[i].size) {
                map[i][j].neighbours.addAll(checkNeighbors(map, i, j))
                if (map[i][j].height == 27) {
                    heighestPoint = Pair(i, j)
                }
                if (map[i][j].height == 0) {
                    startPoint = Pair(i, j)
                }
                j++
            }
            i++
        }
        return Pair(startPoint, heighestPoint)
    }

    fun bfs(map: Array<Array<StepNode>>, queue: MutableList<Pair<Int, Int>>, target: Pair<Int, Int>): Int {
        val dist = queue.associateWith { 0 }.toMutableMap()
        val dirs = listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1)
        while (queue.isNotEmpty()) {
            val (y, x) = queue.removeFirst()
            dirs
                .map { (ya, xa) -> Pair(y+ya, x+xa) }
                .filter { (ya, xa) -> ya in map.indices && xa in map[0].indices }
                .filter { !dist.contains(it) }
                .filter { (ya, xa) -> map[ya][xa].height <= map[y][x].height + 1 }
                .forEach {
                    dist[it] = dist[Pair(y, x)]!! + 1
                    queue.add(it)
                }
        }
        return dist[target]!!
    }

    fun part1(inputReader: BufferedReader): Int {
        val stepNodes = mutableListOf<StepNode>()

        val map = setupMapAndStepNodes(stepNodes, inputReader)

        val (startPoint, heighestPoint) = updateNeighbors(map)

        val steps = bfs(map, mutableListOf(startPoint), heighestPoint)

        val result = steps //path.size-1
        println(result)
        println()
        return result
    }

    fun updateNeighbors2(map: Array<Array<StepNode>>): Pair<MutableList<Pair<Int, Int>>, Pair<Int, Int>> {
        var heighestPoint = Pair(0, 0)
        var startPoints = mutableListOf<Pair<Int, Int>>()
        var i = 0
        while (i < map.size) {
            var j = 0
            while (j < map[i].size) {
                map[i][j].neighbours.addAll(checkNeighbors(map, i, j))
                if (map[i][j].height == 27) {
                    heighestPoint = Pair(i, j)
                }
                if (map[i][j].height == 0 || map[i][j].height == 1) {
                    startPoints.add(Pair(i, j))
                }
                j++
            }
            i++
        }
        return Pair(startPoints, heighestPoint)
    }

    fun part2(inputReader: BufferedReader): Int {
        val stepNodes = mutableListOf<StepNode>()
        val map = setupMapAndStepNodes(stepNodes, inputReader)

        val (startPoints, heighestPoint) = updateNeighbors2(map)

        val steps = bfs(map, startPoints, heighestPoint)

        val result = steps //path.size-1
        println(result)
        println()
        return result
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 31)
    check(part2(testInputReader) == 29)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}

data class StepNode(val neighbours:MutableList<StepNode> = mutableListOf(),
                    var height: Int = -99,
                    var coords: Pair<Int, Int>,
                    var visited: Boolean = false) {
    override fun toString(): String {
        return "%02d - (%d,%d)".format(height, coords.first, coords.second)
    }

    override fun hashCode(): Int {
        return coords.first*999 + coords.second
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StepNode

//        if (neighbours != other.neighbours) return false
        if (height != other.height) return false
        if (coords != other.coords) return false
        if (visited != other.visited) return false

        return true
    }
}
