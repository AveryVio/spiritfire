package com.averyvi.spiritfire.data.definitions.sortingfiltering

data class HabitSortingFiltering(
    val sorting: List<HabitColumnType>,
    val sortingReverse: List<Boolean>,
    val filterTypes: List<HabitFilteringType>,
    val filterColumns: List<HabitColumnType>,
    val filterValues: List<String>,
    val filterInverse: List<Boolean>,
) {
    companion object {
        val EMPTY =
            HabitSortingFiltering(
                sorting = emptyList(),
                sortingReverse = emptyList(),
                filterTypes = emptyList(),
                filterColumns = emptyList(),
                filterValues = emptyList(),
                filterInverse = emptyList(),
            )

            val TESTING =
            HabitSortingFiltering.EMPTY
                //.addSorting(ColumnType.ID, false)
                .addSorting(HabitColumnType.PRIORITY, false)
                .addSorting(HabitColumnType.DIFFICULTY, false)
                .addSorting(HabitColumnType.NAME, false)
                .addSorting(HabitColumnType.TAGS_NAME, false)
                .addSorting(HabitColumnType.TAGS_ID, false)
                .addSorting(HabitColumnType.ID, false)
                .addSorting(HabitColumnType.ID, false)
                .addFilter(HabitFilteringType.AMOUNT, HabitColumnType.NAME, "35", false)

        fun HabitSortingFiltering.addSorting(
            habitColumnType: HabitColumnType,
            reverse: Boolean
        ): HabitSortingFiltering {
            return this.copy(
                sorting = this.sorting.plus(habitColumnType),
                sortingReverse = this.sortingReverse.plus(reverse)
            )
        }
        fun HabitSortingFiltering.removeSorting(
            habitColumnType: HabitColumnType,
            reverse: Boolean
        ): HabitSortingFiltering {
            val index = this.sorting.indexOf(habitColumnType)

            if (index == -1) return this

            return this.copy(
                sorting = this.sorting.filterIndexed { i, _ -> i != index },
                sortingReverse = this.sortingReverse.filterIndexed { i, _ -> i != index }
            )
        }

        fun HabitSortingFiltering.addFilter(
            type: HabitFilteringType,
            column: HabitColumnType,
            value: String,
            inverse: Boolean,
        ): HabitSortingFiltering {
            return this.copy(
                filterTypes = this.filterTypes.plus(type),
                filterColumns = this.filterColumns.plus(column),
                filterValues = this.filterValues.plus(value),
                filterInverse = this.filterInverse.plus(inverse),
            )
        }
        fun HabitSortingFiltering.removeFilter(
            type: HabitFilteringType,
            column: HabitColumnType,
            value: String,
            inverse: Boolean
        ): HabitSortingFiltering {
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

enum class HabitColumnType {
    NAME,
    ID,
    TAGS_ID,
    TAGS_NAME,
    URGENCY, // closest next reset
    DIFFICULTY,
    PRIORITY,
}

enum class HabitFilteringType {
    AMOUNT, // get certain amount of values
    THRESHOLD, // the value has to be above or below a certain value (default above)
    VALUE, // include only one value or exclue only one value (default include)
}