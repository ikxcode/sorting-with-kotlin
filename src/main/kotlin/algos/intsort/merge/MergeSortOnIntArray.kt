package algos.intsort.merge

import algos.intsort.IIntSorter


open class MergeSortOnIntArray : IIntSorter {

    override fun sort(src: IntArray): IntArray {
        if (src.size < 2) return src
        val splitList = split(src)
        val list1 = sort(splitList.first)
        val list2 = sort(splitList.second)
        return sortAndMerge(list1, list2)
    }


    protected fun split(src: IntArray): Pair<IntArray, IntArray> {
        val it = src.iterator()
        val len = src.size
        val mid = len / 2

        val a1 = IntArray(mid)
        for (i in 0 until mid) {
            a1[i] = it.next()
        }

        val secondHalfLength = len - mid
        val a2 = IntArray(secondHalfLength)
        for (i in 0 until secondHalfLength) {
            a2[i] = it.next()
        }

        return Pair(a1, a2)
    }


    protected fun sortAndMerge(array1: IntArray, array2: IntArray): IntArray {
        val result = IntArray(array1.size + array2.size)
        var ri = 0

        var a1i = 0
        var a2i = 0

        while (a1i < array1.size && a2i < array2.size) {
            val a1Val = array1[a1i]
            val a2Val = array2[a2i]
            if (a1Val < a2Val) {
                result[ri] = a1Val
                ++a1i
            } else {
                result[ri] = a2Val
                ++a2i
            }

            ++ri
        }

        if (a1i < array1.size) {
            for (i in a1i until array1.size) {
                result[ri] = array1[i]
                ++ri
            }
        } else {
            for (i in a2i until array2.size) {
                result[ri] = array2[i]
                ++ri
            }
        }

        return result
    }
}