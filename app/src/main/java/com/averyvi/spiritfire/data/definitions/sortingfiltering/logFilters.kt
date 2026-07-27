package com.averyvi.spiritfire.data.definitions.sortingfiltering

data class LogSortingFiltering(
    val sorting: List<LogColumnType>,
    val sortingReverse: List<Boolean>,
    val filterTypes: List<LogFilteringType>,
    val filterColumns: List<LogColumnType>,
    val filterValues: List<String>,
    val filterInverse: List<Boolean>,
) {
    companion object {
        val EMPTY =
            LogSortingFiltering(
                sorting = emptyList(),
                sortingReverse = emptyList(),
                filterTypes = emptyList(),
                filterColumns = emptyList(),
                filterValues = emptyList(),
                filterInverse = emptyList(),
            )

        val TESTING =
            LogSortingFiltering.EMPTY
                //.addSorting(ColumnType.ID, false)
                .addSorting(LogColumnType.TIME, true)
                .addSorting(LogColumnType.HABIT, true)
                .addSorting(LogColumnType.CHECKS, true)
                .addFilter(LogFilteringType.AMOUNT, LogColumnType.TIME, "60", false)

        fun LogSortingFiltering.addSorting(
            habitColumnType: LogColumnType,
            reverse: Boolean
        ): LogSortingFiltering {
            return this.copy(
                sorting = this.sorting.plus(habitColumnType),
                sortingReverse = this.sortingReverse.plus(reverse)
            )
        }
        fun LogSortingFiltering.removeSorting(
            habitColumnType: LogColumnType,
            reverse: Boolean
        ): LogSortingFiltering {
            val index = this.sorting.indexOf(habitColumnType)

            if (index == -1) return this

            return this.copy(
                sorting = this.sorting.filterIndexed { i, _ -> i != index },
                sortingReverse = this.sortingReverse.filterIndexed { i, _ -> i != index }
            )
        }

        fun LogSortingFiltering.addFilter(
            type: LogFilteringType,
            column: LogColumnType,
            value: String,
            inverse: Boolean,
        ): LogSortingFiltering {
            return this.copy(
                filterTypes = this.filterTypes.plus(type),
                filterColumns = this.filterColumns.plus(column),
                filterValues = this.filterValues.plus(value),
                filterInverse = this.filterInverse.plus(inverse),
            )
        }
        fun LogSortingFiltering.removeFilter(
            type: LogFilteringType,
            column: LogColumnType,
            value: String,
            inverse: Boolean
        ): LogSortingFiltering {
            val index = this.filterValues.indexOf(value)

            if (index == -1) return this

            return this.copy(
                filterTypes = this.filterTypes.filterIndexed { i, _ -> i != index },
                filterColumns = this.filterColumns.filterIndexed { i, _ -> i != index },
                filterValues = this.filterValues.filterIndexed { i, _ -> i != index },
                filterInverse = this.filterInverse.plus(inverse),
            )
        }
    }
}

enum class LogColumnType {
    TIME,
    CHECKS,
    HABIT,
    URGENCY, // closest next reset
}

enum class LogFilteringType {
    AMOUNT, // get certain amount of values
    THRESHOLD, // the value has to be above or below a certain value (default above)
    VALUE, // include only one value or exclue only one value (default include)
}