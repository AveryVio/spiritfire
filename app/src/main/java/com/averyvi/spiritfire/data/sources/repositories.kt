package com.averyvi.spiritfire.data.sources

import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.toHabitRow
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
    fun getAllLogsForHabit(habitId: Int): Flow<List<HabitLogItem>>
    fun getHabitsForList(): Flow<List<HabitForList>>

    suspend fun insertHabit(habit: HabitRegistryDBEntity)
    suspend fun deleteHabit(habit: HabitRegistryDBEntity)
    suspend fun insertLog(log: HabitLogDBEntity)
    suspend fun deleteLog(log: HabitLogDBEntity)
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

    override fun getAllLogsForHabit(habitId: Int): Flow<List<HabitLogItem>> {
        return logDao.getAllByHabit(habitId)
            .flowOn(Dispatchers.IO)
    }

    override fun getHabitsForList(): Flow<List<HabitForList>> {
        return habitDao.getAllHabitsForList()
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
}