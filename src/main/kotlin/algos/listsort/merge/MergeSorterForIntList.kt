package algos.listsort.merge

import algos.listsort.IListSorter

class MergeSorterForIntList : IListSorter {
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
        compareAndCopyItemsToResultList(array1, array2, result)
        return result
    }


    private fun <T : Comparable<T>> compareAndCopyItemsToResultList(array1: ArrayList<T>, array2: ArrayList<T>, result: ArrayList<T>) {
        var arr1idx = 0
        var arr2idx = 0
        while (arr1idx < array1.size && arr2idx < array2.size) {
            if (array1[arr1idx] < array2[arr2idx]) {
                result.add(array1[arr1idx])
                ++arr1idx;
            } else {
                result.add(array2[arr2idx])
                ++arr2idx;
            }
        }

        if (arr1idx < array1.size) {
            for (i in arr1idx until array1.size) {
                result.add(array1[i])
            }
        } else {
            for (i in arr2idx until array2.size) {
                result.add(array2[i])
            }
        }
    }
}