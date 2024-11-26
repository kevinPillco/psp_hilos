import kotlin.concurrent.thread
import java.util.concurrent.CountDownLatch

const val SIZE = 4

fun sumMatricesPart(matrixA: Array<IntArray>, matrixB: Array<IntArray>, result: Array<IntArray>, rowStart: Int, rowEnd: Int, latch: CountDownLatch) {
    for (i in rowStart until rowEnd) {
        for (j in 0 until SIZE) {
            result[i][j] = matrixA[i][j] + matrixB[i][j]
        }
    }
    latch.countDown()
}

fun printMatrix(matrix: Array<IntArray>) {
    for (i in matrix) {
        for (j in i) {
            print("$j ")
        }
        println()
    }
}

fun main() {
    val matrixA = Array(SIZE) { IntArray(SIZE) { (1..10).random() } }
    val matrixB = Array(SIZE) { IntArray(SIZE) { (1..10).random() } }
    val result = Array(SIZE) { IntArray(SIZE) }

    println("Matriz A:")
    printMatrix(matrixA)
    println("\nMatriz B:")
    printMatrix(matrixB)

    val latch = CountDownLatch(2)

    val thread1 = thread(start = true) {
        sumMatricesPart(matrixA, matrixB, result, 0, SIZE / 2, latch)
    }

    val thread2 = thread(start = true) {
        sumMatricesPart(matrixA, matrixB, result, SIZE / 2, SIZE, latch)
    }

    latch.await()

    println("\nResultado de la suma:")
    printMatrix(result)
}
