package algos.intsort.merge

import kotlinx.coroutines.*


class MergeSortOnIntArrayUsingAsync(val maxThreads: Int) : MergeSortOnIntArray() {

    var threads = 0;

    override fun sort(src: IntArray): IntArray {
        if (src.size < 2) return src
        val splitList = split(src)
        if (threads < maxThreads) {
            return sortAsync(splitList)
            ++threads
        } else {
            return sortSync(splitList)
        }
    }


    private fun sortAsync(splitList: Pair<IntArray, IntArray>): IntArray = runBlocking{
        val list1 = async { sort(splitList.first) }
        val list2 = async { sort(splitList.second) }
        return@runBlocking sortAndMerge(list1.await(), list2.await())
    }


    private fun sortSync(splitList: Pair<IntArray, IntArray>): IntArray {
        val list1 = sort(splitList.first)
        val list2 = sort(splitList.second)
        return sortAndMerge(list1, list2)
    }

}