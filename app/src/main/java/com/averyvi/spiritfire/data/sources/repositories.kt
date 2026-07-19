package com.averyvi.spiritfire.data.sources

import android.util.Log
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.HabitTagCrossRef
import com.averyvi.spiritfire.data.definitions.habits.TagDBEntity
import com.averyvi.spiritfire.data.definitions.habits.toHabitRow
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitFilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.HabitSortingFiltering
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogColumnType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogFilteringType
import com.averyvi.spiritfire.data.definitions.sortingfiltering.LogSortingFiltering
import com.averyvi.spiritfire.data.sources.db.HabitLogUserDao
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.sources.db.HabitTagCrossRefUserDao
import com.averyvi.spiritfire.data.sources.db.TagUserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface HabitRepository {
    fun getAllHabits(): Flow<List<HabitRow>>
    fun getSelectHabits(selectIds: List<Int>): Flow<List<HabitRow>>
    fun getAllHabitEntities(): Flow<List<HabitRegistryDBEntity>>
    fun getFilteredAndSortedHabits(habitSortingFiltering: HabitSortingFiltering): Flow<List<HabitRow>>

    fun getAllTags(): Flow<List<TagDBEntity>>
    fun getAllHabtTagCrossRefs(): Flow<List<HabitTagCrossRef>>

    fun getAllLogsForHabit(habitId: Int): Flow<List<HabitLogItem>>
    fun getAllLogsFromListOfHabits(selectIds: List<Int>): Flow<List<HabitLogItem>>
    fun getFilteredAndSortedLogs(
        logSortingFiltering: LogSortingFiltering,
        selectIds: List<Int>,
    ): Flow<List<HabitLogItem>>

    fun getHabitsForList(): Flow<List<HabitForList>>

    fun getTagsById(habitId: Int): Flow<List<TagDBEntity>>
    fun getAllTagsByHabit(habitId: Int): Flow<List<TagDBEntity>>
    fun getAllHabitsByTag(tagId: Int): Flow<List<HabitRow>>

    suspend fun insertHabit(habit: HabitRegistryDBEntity)
    suspend fun insertLog(log: HabitLogDBEntity)
    suspend fun insertTag(tag: TagDBEntity)
    suspend fun insertCrossRef(crossRef: HabitTagCrossRef)

    suspend fun deleteHabit(habit: HabitRegistryDBEntity)
    suspend fun deleteLog(log: HabitLogDBEntity)
    suspend fun deleteTag(tag: TagDBEntity)
    suspend fun deleteCrossRef(crossRef: HabitTagCrossRef)
}

class OfflineFirstHabitRepository(
    private val habitDao: HabitRegistryUserDao,
    private val logDao: HabitLogUserDao,
    private val tagDao: TagUserDao,
    private val habitTagCrossRefDao: HabitTagCrossRefUserDao,
) : HabitRepository { // todo finish adding tag and cross ref + implement this into the app instead of the daos
    // get
    override fun getAllHabits(): Flow<List<HabitRow>> {
        return habitDao.getAllHabits()
            .map { dbHabitList ->
                dbHabitList.map { habitWithTags -> habitWithTags.toHabitRow() }
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getSelectHabits(selectIds: List<Int>): Flow<List<HabitRow>> {
        return habitDao.getAllFromListOfIds(selectIds)
            .map { dbHabitList ->
                dbHabitList.map { habitWithTags -> habitWithTags.toHabitRow() }
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getAllHabitEntities(): Flow<List<HabitRegistryDBEntity>> {
        return habitDao.getAllEntities()
            .flowOn(Dispatchers.IO)
    }

    override fun getFilteredAndSortedHabits(habitSortingFiltering: HabitSortingFiltering): Flow<List<HabitRow>> {
        return getAllHabits().map { habits ->
            var processedList = habits

            // sorting
            if (habitSortingFiltering.sorting.isNotEmpty()) {
                processedList = processedList.sortedWith(Comparator { h1, h2 ->
                    var comparisonResult = 0

                    for (i in habitSortingFiltering.sorting.indices) {
                        val column = habitSortingFiltering.sorting[i]
                        val reverse = habitSortingFiltering.sortingReverse.getOrNull(i) ?: false

                        val cmp = when (column) {
                            HabitColumnType.NAME -> h1.name.compareTo(h2.name, ignoreCase = true)
                            HabitColumnType.ID -> h1.id.compareTo(h2.id)
                            HabitColumnType.DIFFICULTY -> h1.difficulty.compareTo(h2.difficulty)
                            HabitColumnType.PRIORITY -> h1.priority.compareTo(h2.priority)
                            HabitColumnType.TAGS_ID -> {
                                val min1 = h1.tags.minOfOrNull { it.id } ?: Int.MAX_VALUE
                                val min2 = h2.tags.minOfOrNull { it.id } ?: Int.MAX_VALUE
                                min1.compareTo(min2)
                            }
                            HabitColumnType.TAGS_NAME -> {
                                val min1 = h1.tags.minOfOrNull { it.name } ?: ""
                                val min2 = h2.tags.minOfOrNull { it.name } ?: ""
                                min1.compareTo(min2)
                            }
                            HabitColumnType.URGENCY -> {
                                // TODO: Replace with your actual 'closest next reset' urgency comparison
                                0
                            }
                        }

                        if (cmp != 0) {
                            comparisonResult = if (reverse) -cmp else cmp
                            break
                        }
                    }
                    comparisonResult
                })
            }

            // filters
            for (i in habitSortingFiltering.filterTypes.indices) {
                val type = habitSortingFiltering.filterTypes.getOrNull(i) ?: continue
                val column = habitSortingFiltering.filterColumns.getOrNull(i) ?: continue
                val valueStr = habitSortingFiltering.filterValues.getOrNull(i) ?: ""
                val inverse = habitSortingFiltering.filterInverse.getOrNull(i) ?: false

                if (type == HabitFilteringType.AMOUNT) {
                    val amount = valueStr.toIntOrNull() ?: continue
                    processedList = if (inverse) processedList.drop(amount) else processedList.take(amount)
                    continue
                }

                processedList = processedList.filter { habit ->
                    val match = when (type) {
                        HabitFilteringType.VALUE -> {
                            when (column) {
                                HabitColumnType.NAME -> habit.name == valueStr
                                HabitColumnType.ID -> habit.id.toString() == valueStr
                                HabitColumnType.TAGS_ID -> habit.tags.any { it.id.toString() == valueStr }
                                HabitColumnType.TAGS_NAME -> habit.tags.any { it.name == valueStr }
                                HabitColumnType.DIFFICULTY -> habit.difficulty.toString() == valueStr
                                HabitColumnType.PRIORITY -> habit.priority.toString() == valueStr
                                HabitColumnType.URGENCY -> true // TODO: Add exact string match logic for your urgency states if applicable
                            }
                        }
                        HabitFilteringType.THRESHOLD -> {
                            val threshold = valueStr.toIntOrNull() ?: 0
                            when (column) {
                                HabitColumnType.ID -> habit.id >= threshold
                                HabitColumnType.DIFFICULTY -> habit.difficulty >= threshold
                                HabitColumnType.PRIORITY -> habit.priority >= threshold
                                HabitColumnType.NAME -> habit.name >= valueStr
                                else -> true
                            }
                        }
                        else -> true
                    }

                    if (inverse) !match else match
                }
            }

            processedList
        }.flowOn(Dispatchers.IO)
    }



    override fun getAllTags(): Flow<List<TagDBEntity>> {
        return tagDao.getAll()
            .flowOn(Dispatchers.IO)
    }

    override fun getAllHabtTagCrossRefs(): Flow<List<HabitTagCrossRef>> {
        return habitTagCrossRefDao.getAll()
            .flowOn(Dispatchers.IO)
    }



    override fun getAllLogsForHabit(habitId: Int): Flow<List<HabitLogItem>> {
        return logDao.getAllByHabit(habitId)
            .flowOn(Dispatchers.IO)
    }

    override fun getAllLogsFromListOfHabits(selectIds: List<Int>): Flow<List<HabitLogItem>> {
        return logDao.getAllFromListOfHabits(selectIds)
            .flowOn(Dispatchers.IO)
    }

    override fun getFilteredAndSortedLogs(
        logSortingFiltering: LogSortingFiltering,
        selectIds: List<Int>,
    ): Flow<List<HabitLogItem>> {
        return logDao.getAllFromListOfHabits(selectIds).map { logs ->
            var processedList = logs

            // sorting
            if (logSortingFiltering.sorting.isNotEmpty()) {
                processedList = processedList.sortedWith(Comparator { l1, l2 ->
                    var comparisonResult = 0

                    for (i in logSortingFiltering.sorting.indices) {
                        val column = logSortingFiltering.sorting[i]
                        val reverse = logSortingFiltering.sortingReverse.getOrNull(i) ?: false

                        val cmp = when (column) {
                            LogColumnType.TIME -> l1.logTime.compareTo(l2.logTime)
                            LogColumnType.CHECKS -> l1.checks.compareTo(l2.checks)
                            LogColumnType.HABIT -> l1.habit.compareTo(l2.habit)
                            LogColumnType.URGENCY -> {
                                // TODO: Replace with your actual 'closest next reset' urgency comparison
                                0
                            }
                        }

                        if (cmp != 0) {
                            comparisonResult = if (reverse) -cmp else cmp
                            break
                        }
                    }
                    comparisonResult
                })
            }

            // filters
            for (i in logSortingFiltering.filterTypes.indices) {
                val type = logSortingFiltering.filterTypes.getOrNull(i) ?: continue
                val column = logSortingFiltering.filterColumns.getOrNull(i) ?: continue
                val valueStr = logSortingFiltering.filterValues.getOrNull(i) ?: ""
                val inverse = logSortingFiltering.filterInverse.getOrNull(i) ?: false

                if (type == LogFilteringType.AMOUNT) {
                    val amount = valueStr.toIntOrNull() ?: continue
                    processedList = if (inverse) processedList.drop(amount) else processedList.take(amount)
                    continue
                }

                processedList = processedList.filter { log ->
                    val match = when (type) {
                        LogFilteringType.VALUE -> {
                            when (column) {
                                LogColumnType.TIME -> log.logTime.toString() == valueStr
                                LogColumnType.CHECKS -> log.checks.toString() == valueStr
                                LogColumnType.HABIT -> log.habit.toString() == valueStr
                                LogColumnType.URGENCY -> true // TODO: Add exact string match logic for your urgency states if applicable
                            }
                        }
                        LogFilteringType.THRESHOLD -> {
                            val threshold = valueStr.toLongOrNull() ?: 0L
                            when (column) {
                                LogColumnType.TIME -> log.logTime >= threshold
                                LogColumnType.CHECKS -> log.checks >= threshold
                                LogColumnType.HABIT -> log.habit >= threshold
                                else -> true
                            }
                        }
                        else -> true
                    }

                    if (inverse) !match else match
                }
            }

            processedList
        }.flowOn(Dispatchers.IO)
    }



    override fun getHabitsForList(): Flow<List<HabitForList>> {
        return habitDao.getAllHabitsForList()
            .flowOn(Dispatchers.IO)
    }



    override fun getTagsById(habitId: Int): Flow<List<TagDBEntity>> {
        return tagDao.getAllById(habitId)
            .flowOn(Dispatchers.IO)
    }

    override fun getAllTagsByHabit(habitId: Int): Flow<List<TagDBEntity>> {
        return tagDao.getAllTagsByHabit(habitId)
            .flowOn(Dispatchers.IO)


    }

    override fun getAllHabitsByTag(tagId: Int): Flow<List<HabitRow>> {
        return habitDao.getAllByTag(tagId)
            .map { dbHabitList ->
                dbHabitList.map { habitWithTags -> habitWithTags.toHabitRow() }
            }
            .flowOn(Dispatchers.IO)
    }



    // insert
    override suspend fun insertHabit(habit: HabitRegistryDBEntity) {
        withContext(Dispatchers.IO) {
            habitDao.insert(habit)
        }
    }

    override suspend fun insertLog(log: HabitLogDBEntity) {
        withContext(Dispatchers.IO) {
            logDao.insert(log)
        }
    }

    override suspend fun insertTag(tag: TagDBEntity) {
        withContext(Dispatchers.IO) {
            tagDao.insert(tag)
        }
    }

    override suspend fun insertCrossRef(crossRef: HabitTagCrossRef) {
        withContext(Dispatchers.IO) {
            habitTagCrossRefDao.insert(crossRef)
        }
    }



    // delete
    override suspend fun deleteHabit(habit: HabitRegistryDBEntity) {
        withContext(Dispatchers.IO) {
            habitDao.delete(habit)
        }
    }

    override suspend fun deleteLog(log: HabitLogDBEntity) {
        withContext(Dispatchers.IO) {
            logDao.delete(log)
        }
    }

    override suspend fun deleteTag(tag: TagDBEntity) {
        withContext(Dispatchers.IO) {
            tagDao.delete(tag)
        }
    }

    override suspend fun deleteCrossRef(crossRef: HabitTagCrossRef) {
        withContext(Dispatchers.IO) {
            habitTagCrossRefDao.delete(crossRef)
        }
    }
}