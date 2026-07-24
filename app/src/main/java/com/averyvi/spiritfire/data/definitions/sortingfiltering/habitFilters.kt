package com.averyvi.spiritfire.data.definitions.sortingfiltering

import com.averyvi.spiritfire.R

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
                .addSorting(HabitColumnType.PRIORITY, true)
                .addSorting(HabitColumnType.DIFFICULTY, true)
                .addSorting(HabitColumnType.NAME, true)
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

        fun HabitSortingFiltering.reverseSorting(
            index: Int
        ): HabitSortingFiltering {
            val new = this.sortingReverse.toMutableList()
            new[index] = !new[index]
            return this.copy(sortingReverse = new)
        }

        fun HabitSortingFiltering.inverseFilter(
            index: Int
        ): HabitSortingFiltering {
            val new = this.filterInverse.toMutableList()
            new[index] = !new[index]
            return this.copy(filterInverse = new)
        }
    }
}

enum class HabitColumnType(
    val uiText: Int,
    val userSide: Boolean,
) {
    NAME(
        uiText = R.string.SortingFilteringName,
        userSide = true,
    ),
    ID(
        uiText = R.string.SortingFilteringId,
        userSide = false,
    ),
    TAGS_ID(
        uiText = R.string.SortingFilteringTagsId,
        userSide = false,
    ),
    TAGS_NAME(
        uiText = R.string.SortingFilteringTagsName,
        userSide = true,
    ),
    URGENCY(
        uiText = R.string.SortingFilteringUrgency,
        userSide = true,
    ), // closest next reset
    DIFFICULTY(
        uiText = R.string.SortingFilteringDifficulty,
        userSide = true,
    ),
    PRIORITY(
        uiText = R.string.SortingFilteringPriority,
        userSide = true,
    ),
}

enum class HabitFilteringType(
    val uiText: Int,
    val userSide: Boolean,
) {
    AMOUNT(
        uiText = R.string.SortingFilteringAmount,
        userSide = true,
    ), // get certain amount of values
    THRESHOLD(
        uiText = R.string.SortingFilteringThreshold,
        userSide = true,
    ), // the value has to be above or below a certain value (default above)
    VALUE(
        uiText = R.string.SortingFilteringValue,
        userSide = true,
    ), // include only one value or exclue only one value (default include)
}