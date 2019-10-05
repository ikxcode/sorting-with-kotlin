package algos.listsort.merge

import algos.listsort.IListSorter

class MergeSorterSimpleForIntList : IListSorter {
    override fun <T : Comparable<T>> sort(src: ArrayList<T>): ArrayList<T> {
        if (src.size < 2) return src
        val splitList = split(src)
        val list1 = sort(splitList.first)
        val list2 = sort(splitList.second)
        return sortAndMerge(list1, list2)
    }


    private fun <T> split(src: ArrayList<T>): Pair<ArrayList<T>, ArrayList<T>> {
        val len = src.size
        val mid = len / 2

        val a1 = ArrayList(src.subList(0, mid));
        val a2 = ArrayList(src.subList(mid, len));

        return Pair(a1, a2)
    }


    private fun <T : Comparable<T>> sortAndMerge(array1: ArrayList<T>, array2: ArrayList<T>): ArrayList<T> {
        val result = ArrayList<T>(array1.size + array2.size)
        compareAndMoveItemsToResultList(array1, array2, result)  // SLOOOOOW for large datasets
        return result
    }


    private fun <T : Comparable<T>> compareAndMoveItemsToResultList(array1: ArrayList<T>, array2: ArrayList<T>, result: ArrayList<T>) {
        while (array1.isNotEmpty() && array2.isNotEmpty()) {
            if (array1[0] < array2[0]) {
                result.add(array1.removeAt(0))
            } else {
                result.add(array2.removeAt(0))
            }
        }

        if (array1.isNotEmpty()) {
            result.addAll(array1)
        } else {
            result.addAll(array2)
        }
    }
}