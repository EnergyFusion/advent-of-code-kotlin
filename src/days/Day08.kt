import java.io.BufferedReader

fun main() {
    val day = 8

    fun setupMap(map: Array<Array<Pair<Int, Boolean>>>, row: List<Int>, rowI: Int, width: Int) {
        val lastI = width - 1
        if (rowI == 0 || rowI == lastI) {
            //Top or bottom edge always visible, no need to check vertically
            row.forEachIndexed { col, height -> map[rowI][col] = Pair(height, true) }
        } else {
            map[rowI][0] = Pair(row[0], true)
            map[rowI][lastI] = Pair(row[lastI], true)

            row.forEachIndexed { col, height ->
                if (col > 0 && col < width-1) {
                    map[rowI][col] = Pair(height, false)
                }
            }
        }
    }

    fun checkRightS(map: Array<Array<Pair<Int, Boolean>>>, row: Int, width: Int) {
        var col = width - 2

        while (col > 0) {
            if (map[row][col+1].first < map[row][col].first) {
                map[row][col] = Pair(map[row][col].first, true)
            } else if (map[row][col+1].first == map[row][col].first) {
                // Do Nothing
            } else {
                break
            }
            col--
        }
    }

    fun checkLeftS(map: Array<Array<Pair<Int, Boolean>>>, row: Int, width: Int) {
        var col = 1

        while (col < width - 1) {
            if (map[row][col-1].first < map[row][col].first) {
                map[row][col] = Pair(map[row][col].first, true)
            } else if (map[row][col-1].first == map[row][col].first) {
                // Do Nothing
            } else {
                break
            }
            col++
        }
    }

    fun checkUpS(map: Array<Array<Pair<Int, Boolean>>>, col: Int, width: Int) {
        var row = 1

        while (row < width - 1) {
            if (map[row-1][col].first < map[row][col].first) {
                map[row][col] = Pair(map[row][col].first, true)
            } else if (map[row-1][col].first == map[row][col].first) {
                // Do Nothing
            } else {
                break
            }
            row++
        }
    }

    fun checkDown(map: Array<Array<Pair<Int, Boolean>>>, col: Int, width: Int) {
        var row = width - 2

        while (row > 0) {
            if (map[row+1][col].first < map[row][col].first) {
                map[row][col] = Pair(map[row][col].first, true)
            } else if (map[row+1][col].first == map[row][col].first) {
                // Do Nothing
            } else {
                break
            }
            row--
        }
    }

    fun checkVerticalAll(map: Array<Array<Pair<Int, Boolean>>>, width: Int) {
        var col = 1
        while (col < width - 1) {
            checkDown(map, col, width)
            checkUpS(map, col, width)
            col++
        }
    }

    fun checkHorizontalAll(map: Array<Array<Pair<Int, Boolean>>>, width: Int) {
        var row = 1
        while (row < width - 1) {
            checkLeftS(map, row, width)
            checkRightS(map, row, width)
            row++
        }
    }

    fun checkDirs(map: Array<Array<Pair<Int, Boolean>>>, row: Int, col: Int, width: Int) {
        val currentHeight = map[row][col].first
        val lastI = width - 1
        // check up
        var i = row-1
        while (i >= 0) {
            if (map[i][col].first >= currentHeight) {
                break
            }
            i--
        }
        if (i < 0) {
            map[row][col] = Pair(currentHeight, true)
            return
        }
        // check down
        i = row + 1
        while (i <= lastI) {
            if (map[i][col].first >= currentHeight) {
                break
            }
            i++
        }
        if (i > lastI) {
            map[row][col] = Pair(currentHeight, true)
            return
        }
        // check left
        i = col - 1
        while (i >= 0) {
            if (map[row][i].first >= currentHeight) {
                break
            }
            i--
        }
        if (i < 0) {
            map[row][col] = Pair(currentHeight, true)
            return
        }
        // check right
        i = col + 1
        while (i <= lastI) {
            if (map[row][i].first >= currentHeight) {
                break
            }
            i++
        }
        if (i > lastI) {
            map[row][col] = Pair(currentHeight, true)
            return
        }
    }

    fun checkAll(map: Array<Array<Pair<Int, Boolean>>>, width: Int) {
        var row = 1
        while (row < width-1) {
            var col = 1
            while (col < width-1) {
                checkDirs(map, row, col, width)
                col++
            }
            row++
        }
    }

    fun <T>debugPrint(map: Array<Array<Pair<Int, T>>>) {
        for (bRow in map!!) {
            print("|")
            for (col in bRow) {
                print(" ${col.first} - ${col.second}\t|")
            }
            println()
        }
    }

    fun part1(inputReader: BufferedReader): Int {
        var map: Array<Array<Pair<Int, Boolean>>>? = null
        var row = 0
        var width = 0
        inputReader.forEachLine { line ->
            if (row == 0) {
                width = line.length
                map = Array(width) { Array(width, init = { _ -> Pair(0, false) }) }
            }

            setupMap(map!!, line.toCharArray().map { it.digitToInt() }, row, width)

            row++
        }

//        debugPrint(map!!)
//        println()

        checkAll(map!!, width)
//        checkVerticalAll(map!!, width)
//        checkHorizontalAll(map!!, width)

                return map!!.map { columns: Array<Pair<Int, Boolean>> ->
                    columns.map { pair: Pair<Int, Boolean> -> pair.second }
                }.flatten().count { it }

        // Debug
//        debugPrint(map!!)
//
//        val booleanMap = map!!.map { columns: Array<Pair<Int, Boolean>> ->
//            columns.map { pair: Pair<Int, Boolean> -> pair.second }
//        }
//
//        val count = booleanMap.flatten().count { it }
//        println(count)
//
//        return count
        // End Debug
    }

     fun setupMap2(map: Array<Array<Pair<Int, Int>>>, row: List<Int>, rowI: Int, width: Int) {
        val lastI = width - 1
        if (rowI == 0 || rowI == lastI) {
            //Top or bottom edge always visible, no need to check vertically
            row.forEachIndexed { col, height -> map[rowI][col] = Pair(height, 0) }
        } else {
            map[rowI][0] = Pair(row[0], 0)
            map[rowI][lastI] = Pair(row[lastI], 0)

            row.forEachIndexed { col, height ->
                if (col > 0 && col < width-1) {
                    map[rowI][col] = Pair(height, 1)
                }
            }
        }
     }

    fun checkDirections(map: Array<Array<Pair<Int, Int>>>, row: Int, col: Int, width: Int, highest: Int): Int {
        val currentHeight = map[row][col].first
        val lastI = width - 1
        var score = 1
        // check up
        var count = 1
        var i = row-1
        while (i > 0) {
            if (map[i][col].first >= currentHeight) {
                break
            }
            i--
            count++
        }
        score *= count
        // check down
        count = 1
        i = row + 1
        while (i < lastI) {
            if (map[i][col].first >= currentHeight) {
                break
            }
            i++
            count++
        }
        score *= count

        // check left
        count = 1
        i = col - 1
        while (i > 0) {
            if (map[row][i].first >= currentHeight) {
                break
            }
            i--
            count++
        }
        score *= count

        // check right
        count = 1
        i = col + 1
        while (i < lastI) {
            if (map[row][i].first >= currentHeight) {
                break
            }
            i++
            count++
        }
        score *= count

        map[row][col] = Pair(currentHeight, score)

        if (score > highest) {
            return score
        }
        return highest
    }

    fun updateScenicScores(map: Array<Array<Pair<Int, Int>>>, width: Int): Int {
        var highest = 0
        var row = 1
        while (row < width-1) {
            var col = 1
            while (col < width-1) {
                highest = checkDirections(map, row, col, width, highest)
                col++
            }
            row++
        }
        return highest
    }

    fun part2(inputReader: BufferedReader): Int {
        var map: Array<Array<Pair<Int, Int>>>? = null
        var row = 0
        var width = 0
        inputReader.forEachLine { line ->
            if (row == 0) {
                width = line.length
                map = Array(width) { Array(width, init = { _ -> Pair(0, 1) }) }
            }

            setupMap2(map!!, line.toCharArray().map { it.digitToInt() }, row, width)

            row++
        }

//        debugPrint(map!!)
        val highest = updateScenicScores(map!!, width)

//        println()
//        debugPrint(map!!)
//        println(highest)

        return highest
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 21)
        check(part2(testInputReader) == 8)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}