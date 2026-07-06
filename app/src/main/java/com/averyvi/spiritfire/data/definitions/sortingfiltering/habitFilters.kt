package com.averyvi.spiritfire.data.definitions.sortingfiltering

data class SortingFiltering(
    val sorting: List<ColumnType>,
    val sortingReverse: List<Boolean>,
    val filterTypes: List<FilteringType>,
    val filterColumns: List<ColumnType>,
    val filterValues: List<String>,
) {
    companion object {
        val EMPTY =
            SortingFiltering(
                sorting = emptyList(),
                sortingReverse = emptyList(),
                filterTypes = emptyList(),
                filterColumns = emptyList(),
                filterValues = emptyList(),
            )

        fun SortingFiltering.addSorting(
            columnType: ColumnType,
            reverse: Boolean
        ): SortingFiltering {
            return this.copy(
                sorting = this.sorting.plus(columnType),
                sortingReverse = this.sortingReverse.plus(reverse)
            )
        }
        fun SortingFiltering.removeSorting(
            columnType: ColumnType,
            reverse: Boolean
        ): SortingFiltering {
            val index = this.sorting.indexOf(columnType)

            if (index == -1) return this

            return this.copy(
                sorting = this.sorting.filterIndexed { i, _ -> i != index },
                sortingReverse = this.sortingReverse.filterIndexed { i, _ -> i != index }
            )
        }

        fun SortingFiltering.addFilter(
            type: FilteringType,
            column: ColumnType,
            value: String,
        ): SortingFiltering {
            return this.copy(
                filterTypes = this.filterTypes.plus(type),
                filterColumns = this.filterColumns.plus(column),
                filterValues = this.filterValues.plus(value)
            )
        }
        fun SortingFiltering.removeFilter(
            type: FilteringType,
            column: ColumnType,
            value: String,
        ): SortingFiltering {
            val index = this.filterValues.indexOf(value)

            if (index == -1) return this

            return this.copy(
                filterTypes = this.filterTypes.filterIndexed { i, _ -> i != index },
                filterColumns = this.filterColumns.filterIndexed { i, _ -> i != index },
                filterValues = this.filterValues.filterIndexed { i, _ -> i != index },
            )
        }
    }
}

enum class ColumnType {
    NAME,
    ID,
    TAGS_ID,
    TAGS_NAME,
    URGENCY, // closest next reset
    DIFFICULTY,
    PRIORITY,
}

enum class FilteringType {
    AMOUNT,
    THRESHOLD,
    VALUE,
}