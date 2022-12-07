import java.io.BufferedReader

fun main() {
    val day = 7

    fun getDirMap(inputReader: BufferedReader): Map<String, Int> {
        val dirMap = mutableMapOf("/" to 0)
        val directoryStack = ArrayDeque<String>()
        var currentDir = "/"
        var lineNum = 0
        inputReader.forEachLine { line ->
            if (line.startsWith("$ cd")) {
                val split = line.split(" ")
                var newDir = split.last()
                if (lineNum != 0 && !newDir.equals("..")) {
                    directoryStack.addLast(currentDir)
                }
                if (newDir.equals("..")) {
                    newDir = directoryStack.removeLast()
                } else {
                    newDir = dirMap.keys.findLast { name -> name.startsWith(newDir) }!!
                }
                currentDir = newDir
            }  else if (line.equals("$ ls")) {
            //Do nothing
            } else if (line.startsWith("dir")) {
                val split = line.split(" ")
                var newDir = split.last()
                while (dirMap.containsKey(newDir)) {
                    newDir += "_"
                }
                dirMap[newDir] = 0
            } else {
                // This should be a file now
                val split = line.split(" ")
                val size = split[0].toInt()

                var curSize = dirMap[currentDir] ?: 0
                dirMap[currentDir] = curSize + size

                directoryStack.forEach { dir ->
                    curSize = dirMap[dir] ?: 0
                    dirMap[dir] = curSize + size
                }
            }
            //            println(dirMap)
            lineNum++
        }
        return dirMap
    }

    fun part1(inputReader: BufferedReader): Int {
        val dirMap = getDirMap(inputReader)
        val sizes = dirMap.values.filter { value -> value <= 100000 }
        return sizes.sum()
    }

    fun part2(inputReader: BufferedReader): Int {
        val dirMap = getDirMap(inputReader)
        val totalSpace = 70000000
        val homeFilled = dirMap["/"]!!
        val freeSpace = totalSpace - homeFilled
        val spaceNeeded = 30000000 - freeSpace
        val directorySizesBigEnough = dirMap.values.filter { value -> value >= spaceNeeded }

        return directorySizesBigEnough.sorted().first()
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 95437)
    check(part2(testInputReader) == 24933642)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}