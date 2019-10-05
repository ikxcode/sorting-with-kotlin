package algos.intsort.merge

import kotlin.concurrent.thread


class MergeSortOnIntArrayUsingThreads(private val threadSplitLevels: Int) : MergeSortOnIntArray() {

    override fun sort(src: IntArray): IntArray {
        return sortWithThreads(src, threadSplitLevels)
    }

    private fun sortWithThreads(src: IntArray, availableThreads: Int): IntArray {
        if (src.size < 2) return src
        val splitList = split(src)
        return if (availableThreads > 1) {
            forkAndSort(splitList, availableThreads/2)
        } else {
            sortSync(splitList, availableThreads)
        }
    }

    private fun forkAndSort(splitList: Pair<IntArray, IntArray>, availableThreads: Int): IntArray {
        var list1 = IntArray(0)
        var list2 = IntArray(0)
        val t1 = thread { list1 = sortWithThreads(splitList.first, availableThreads) }
        val t2 = thread { list2 = sortWithThreads(splitList.second, availableThreads) }
        t1.join()
        t2.join()
        return sortAndMerge(list1, list2)
    }


    private fun sortSync(splitList: Pair<IntArray, IntArray>, at: Int): IntArray {
        val list1 = sortWithThreads(splitList.first, at)
        val list2 = sortWithThreads(splitList.second, at)
        return sortAndMerge(list1, list2)
    }

}
