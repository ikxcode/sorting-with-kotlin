import algos.intsort.merge.MergeSortOnIntArray
import algos.intsort.merge.MergeSortOnIntArrayUsingThreads
import algos.listsort.merge.MergeSorterForIntList
import algos.listsort.merge.MergeSorterSimpleForIntList
import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    runIntArrayMergeSort()
}

fun runIntArrayMergeSort() {
    //val array = intArrayOf(100, 3, 2, 70, 22, 11, 27, 94, 23, 83)
    val array = generateArray(1_000_000)
    val referenceSortedList = array.sorted()
    if (array.size < 11) println("Source List: " + array.joinToString())

    sortIntList(array.toCollection(ArrayList()), referenceSortedList)
    sortIntArrays(array, referenceSortedList)
}

fun sortIntList(srcList: ArrayList<Int>, referenceSortedList: List<Int>) {
    mergeSortIntList(srcList, referenceSortedList)
    mergeSortIntListSimple(srcList, referenceSortedList)
}

fun mergeSortIntList(srcList: ArrayList<Int>, referenceSortedList: List<Int>) {
    println()
    println("Running Single-Threaded Optimised MergeSort on Int List.")
    val countInfo = getCountInfo(srcList.size)
    var result = ArrayList<Int>(countInfo.first)
    var elapsed = measureTimeMillis {
        val sorter = MergeSorterForIntList()
        result = sorter.sort(srcList)
    }
    println("Sorted ${countInfo.second} entries using Single-Threaded Optimised MergeSort on Int List in $elapsed mills.")

    //println(Arrays.toString(sorted))
    print("Comparing results ... ")
    val matched = compareResults(countInfo.first, result.iterator(), referenceSortedList)
    if (!matched) {
        if (srcList.size < 11) println("Result List: " + result.joinToString())
    }
}

fun mergeSortIntListSimple(srcList: ArrayList<Int>, referenceSortedList: List<Int>) {
    println()
    println("Running Single-Threaded Simple MergeSort on Int List.")
    val countInfo = getCountInfo(srcList.size)
    var result = ArrayList<Int>(countInfo.first)
    var elapsed = measureTimeMillis {
        val sorter = MergeSorterSimpleForIntList()
        result = sorter.sort(srcList)
    }
    println("Sorted ${countInfo.second} entries using Single-Threaded Simple MergeSort on Int List in $elapsed mills.")

    //println(Arrays.toString(sorted))
    print("Comparing results ... ")
    val matched = compareResults(countInfo.first, result.iterator(), referenceSortedList)
    if (!matched) {
        if (srcList.size < 11) println("Result List: " + result.joinToString())
    }
}


private fun sortIntArrays(srcArray: IntArray, referenceSortedList: List<Int>) {
    mergeSortIntArrayWithSingleThread(srcArray, referenceSortedList)
    mergeSortIntArrayWithMultipleThreads(srcArray, referenceSortedList)
}


private fun mergeSortIntArrayWithSingleThread(srcArray: IntArray, referenceSortedList: List<Int>) {
    println()
    println("Running Single-Threaded MergeSort on IntArray.")
    val countInfo = getCountInfo(srcArray.size)
    var result = IntArray(0)
    var elapsed = measureTimeMillis {
        val sorter = MergeSortOnIntArray()
        result = sorter.sort(srcArray)
    }
    println("Sorted ${countInfo.second} entries using Single-Threaded MergeSort on IntArray in $elapsed mills.")

    //println(Arrays.toString(sorted))
    print("Comparing results ... ")
    compareResults(countInfo.first, result.iterator(), referenceSortedList)
}


private fun mergeSortIntArrayWithMultipleThreads(srcArray: IntArray, referenceSortedList: List<Int>) {
    val threadCount = Runtime.getRuntime().availableProcessors();
    //val threadCount = 8
    println()
    println("Running Multi-Threaded MergeSort on IntArray with $threadCount threads.")

    val countInfo = getCountInfo(srcArray.size)
    var result = IntArray(0)
    val elapsed = measureTimeMillis {
        val sorter = MergeSortOnIntArrayUsingThreads(threadCount)
        result = sorter.sort(srcArray)
    }
    println("Sorted ${countInfo.second} entries using Multi-Threaded MergeSort on IntArray in $elapsed mills.")

    //println(Arrays.toString(sorted))
    compareResults(countInfo.first, result.iterator(), referenceSortedList)
}


private fun compareResults(count: Int, resultIterator: Iterator<Int>, referenceSortedList: List<Int>) : Boolean {
    print("Comparing results ... ")
    var allMatched = true
    for (i in 0 until count) {
        if (resultIterator.next() != referenceSortedList[i]) {
            allMatched = false
        }
    }

    if (allMatched) {
        println("ALL GOOD!")
    } else {
        println("FAIL!")
    }

    return allMatched
}



private fun getCountInfo(count: Int): Pair<Int, String> {
    val countString = "%,d".format(count)
    return Pair(count, countString)
}


fun generateArray(count: Int): IntArray {
    println()
    println("Generating List of $count random numbers ...")

    val array = IntArray(count)
    val r = Random()

    if (count < 11) {
        for (i in 0 until count) {
            array[i] = r.nextInt(100)
        }
    } else {
        for (i in 0 until count) {
            array[i] = r.nextInt()
        }
    }

    return array
}


