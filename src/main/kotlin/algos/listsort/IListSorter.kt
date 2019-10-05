package algos.listsort


interface IListSorter {
    fun <T : Comparable<T>> sort(src: ArrayList<T>): ArrayList<T>
}