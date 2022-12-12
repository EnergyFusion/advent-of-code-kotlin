import java.io.BufferedReader
import java.util.LinkedList

fun main() {
    val day = 9

    fun moveTail(tailNodes: LinkedList<Pair<Int, Int>>, newHead: Pair<Int, Int>) {
        val currentTail = tailNodes.last()
        val tailRow = currentTail.first
        val tailCol = currentTail.second

        val headRow = newHead.first
        val headCol = newHead.second

        // Same row
        if (headRow == tailRow) {
            if (tailCol < headCol-1) {
                tailNodes.addLast(Pair(tailRow, headCol-1))
                return
            }
            if (tailCol > headCol+1) {
                tailNodes.addLast(Pair(tailRow, headCol+1))
                return
            }
        }

        //Same Col
        if (headCol == tailCol) {
            if (tailRow < headRow-1) {
                tailNodes.addLast(Pair(headRow-1, tailCol))
                return
            }
            if (tailRow > headRow+1) {
                tailNodes.addLast(Pair(headRow+1, tailCol))
                return
            }
        }

        //Head is diagonal left
        if (headCol-1 == tailCol) {
            if (tailRow < headRow-1) {
                tailNodes.addLast(Pair(headRow-1, headCol))
                return
            }
            if (tailRow > headRow+1) {
                tailNodes.addLast(Pair(headRow+1, headCol))
                return
            }
        }

        //Head is diagonal right
        if (headCol+1 == tailCol) {
            if (tailRow < headRow-1) {
                tailNodes.addLast(Pair(headRow-1, headCol))
                return
            }
            if (tailRow > headRow+1) {
                tailNodes.addLast(Pair(headRow+1, headCol))
                return
            }
        }

        //Head is diagonal up
        if (headRow-1 == tailRow) {
            if (tailCol < headCol-1) {
                tailNodes.addLast(Pair(headRow, headCol-1))
                return
            }
            if (tailCol > headCol+1) {
                tailNodes.addLast(Pair(headRow, headCol+1))
                return
            }
        }

        //Head is diagonal down
        if (headRow+1 == tailRow) {
            if (tailCol < headCol-1) {
                tailNodes.addLast(Pair(headRow, headCol-1))
                return
            }
            if (tailCol > headCol+1) {
                tailNodes.addLast(Pair(headRow, headCol+1))
                return
            }
        }
    }

    fun goRight(headNodes: LinkedList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int) {
        val currentHead = headNodes.last()
        val row = currentHead.first
        var j = currentHead.second
        val endCol = j + paces

        while (j < endCol) {
            j++
            val newHead = Pair(row, j)
            headNodes.addLast(newHead)
            moveTail(tailNodes, newHead)
        }
    }

    fun goLeft(headNodes: LinkedList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int) {
        val currentHead = headNodes.last()
        val row = currentHead.first
        var j = currentHead.second
        val endCol = j - paces

        while (j > endCol) {
            j--
            val newHead = Pair(row, j)
            headNodes.addLast(newHead)
            moveTail(tailNodes, newHead)
        }
    }

    fun goUp(headNodes: LinkedList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int) {
        val currentHead = headNodes.last()
        var i = currentHead.first
        val col = currentHead.second
        val endRow = i + paces

        while (i < endRow) {
            i++
            val newHead = Pair(i, col)
            headNodes.addLast(newHead)
            moveTail(tailNodes, newHead)
        }
    }

    fun goDown(headNodes: LinkedList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int) {
        val currentHead = headNodes.last()
        var i = currentHead.first
        val col = currentHead.second
        val endRow = i - paces

        while (i > endRow) {
            i--
            val newHead = Pair(i, col)
            headNodes.addLast(newHead)
            moveTail(tailNodes, newHead)
        }
    }

    fun part1(inputReader: BufferedReader): Int {
        val tailNodes = LinkedList<Pair<Int, Int>>()
        tailNodes.addLast(Pair(0, 0))
        val headNodes = LinkedList<Pair<Int, Int>>()
        headNodes.addLast(Pair(0, 0))

        inputReader.forEachLine { line ->
            val lineSplit = line.split(" ")
            val dir = lineSplit[0]
            val paces = lineSplit[1].toInt()

            when(dir) {
                "R" -> goRight(headNodes, tailNodes, paces)
                "L" -> goLeft(headNodes, tailNodes, paces)
                "D" -> goDown(headNodes, tailNodes, paces)
                "U" -> goUp(headNodes, tailNodes, paces)
            }
//            println("headNodes = $headNodes")
//            println("tailNodes = $tailNodes")
//            println()
        }

        val result = tailNodes.toSet().size
        println(result)
        return result
    }

    fun moveNode(newKnot: Pair<Int, Int>, currentKnotToMove: Pair<Int, Int>): Pair<Int, Int> {
        val cRow = currentKnotToMove.first
        val cCol = currentKnotToMove.second

        val nRow = newKnot.first
        val nCol = newKnot.second

        // Same row
        if (nRow == cRow) {
            if (cCol < nCol-1) {
                return Pair(cRow, nCol-1)
            }
            if (cCol > nCol+1) {
                return Pair(cRow, nCol+1)
            }
        }

        //Same Col
        if (nCol == cCol) {
            if (cRow < nRow-1) {
                return Pair(nRow-1, cCol)
            }
            if (cRow > nRow+1) {
                return Pair(nRow+1, cCol)
            }
        }

        //Head is diagonal horizontal
        if (nCol-1 == cCol || nCol+1 == cCol) {
            if (cRow < nRow-1) {
                return Pair(nRow-1, nCol)
            }
            if (cRow > nRow+1) {
                return Pair(nRow+1, nCol)
            }
        }

        //Head is diagonal vertical
        if (nRow-1 == cRow || nRow+1 == cRow) {
            if (cCol < nCol-1) {
                return Pair(nRow, nCol-1)
            }
            if (cCol > nCol+1) {
                return Pair(nRow, nCol+1)
            }
        }

        //New is diagonal top right
        if (nRow-2 == cRow && nCol-2 == cCol) {
            return Pair(nRow-1, nCol-1)
        }

        //New is diagonal bottom right
        if (nRow+2 == cRow && nCol-2 == cCol) {
            return Pair(nRow+1, nCol-1)
        }

        //New is diagonal bottom left
        if (nRow+2 == cRow && nCol+2 == cCol) {
            return Pair(nRow+1, nCol+1)
        }

        //New is diagonal top left
        if (nRow-2 == cRow && nCol+2 == cCol) {
            return Pair(nRow-1, nCol+1)
        }

        return currentKnotToMove
    }

    fun moveKnots(newHead: Pair<Int, Int>, knots: MutableList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>) {
        knots[0] = moveNode(newHead, knots[0])
        var i = 1
        while (i < 8) {
            knots[i] = moveNode(knots[i-1], knots[i])
            i++
        }
        val currentTail = tailNodes.last()
        val newTail = moveNode(knots[7], currentTail)
        if (currentTail != newTail) {
            tailNodes.addLast(newTail)
        }
    }

    fun goRight2(currentHead: Pair<Int, Int>, knots: MutableList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int): Pair<Int, Int> {
        val row = currentHead.first
        var j = currentHead.second
        val endCol = j + paces

        var newHead = currentHead
        while (j < endCol) {
            j++
            newHead = Pair(row, j)
            moveKnots(newHead, knots, tailNodes)
        }
        return newHead
    }

    fun goLeft2(currentHead: Pair<Int, Int>, knots: MutableList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int): Pair<Int, Int> {
        val row = currentHead.first
        var j = currentHead.second
        val endCol = j - paces

        var newHead = currentHead
        while (j > endCol) {
            j--
            newHead = Pair(row, j)
            moveKnots(newHead, knots, tailNodes)
        }
        return newHead
    }

    fun goUp2(currentHead: Pair<Int, Int>, knots: MutableList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int): Pair<Int, Int> {
        var i = currentHead.first
        val col = currentHead.second
        val endRow = i + paces

        var newHead = currentHead
        while (i < endRow) {
            i++
            newHead = Pair(i, col)
            moveKnots(newHead, knots, tailNodes)
        }
        return newHead
    }

    fun goDown2(currentHead: Pair<Int, Int>, knots: MutableList<Pair<Int, Int>>, tailNodes: LinkedList<Pair<Int, Int>>, paces: Int): Pair<Int, Int> {
        var i = currentHead.first
        val col = currentHead.second
        val endRow = i - paces

        var newHead = currentHead
        while (i > endRow) {
            i--
            newHead = Pair(i, col)
            moveKnots(newHead, knots, tailNodes)
        }
        return newHead
    }

    fun part2(inputReader: BufferedReader): Int {
        val tailNodes = LinkedList<Pair<Int, Int>>()
        tailNodes.addLast(Pair(0, 0))
        var headNode = Pair(0, 0)
        val knots = mutableListOf(
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
        )

        inputReader.forEachLine { line ->
            val lineSplit = line.split(" ")
            val dir = lineSplit[0]
            val paces = lineSplit[1].toInt()

            when(dir) {
                "R" -> headNode = goRight2(headNode, knots, tailNodes, paces)
                "L" -> headNode = goLeft2(headNode, knots, tailNodes, paces)
                "D" -> headNode = goDown2(headNode, knots, tailNodes, paces)
                "U" -> headNode = goUp2(headNode, knots, tailNodes, paces)
            }
//                    println("tailNodes = $tailNodes")
//                    println()
        }

        val result = tailNodes.toSet().size
        println(result)
        return result
    }

    val testInputReader = readBufferedInput("tests/Day%02d".format(day))
//    check(part1(testInputReader) == 13)
    check(part2(testInputReader) == 36)

    val input = readBufferedInput("input/Day%02d".format(day))
//    println(part1(input))
    println(part2(input))
}