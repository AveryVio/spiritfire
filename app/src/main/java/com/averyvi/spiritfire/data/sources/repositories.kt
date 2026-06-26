package com.averyvi.spiritfire.data.sources

import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.HabitTagCrossRef
import com.averyvi.spiritfire.data.definitions.habits.TagDBEntity
import com.averyvi.spiritfire.data.definitions.habits.toHabitRow
import com.averyvi.spiritfire.data.sources.db.HabitLogUserDao
import com.averyvi.spiritfire.data.sources.db.HabitRegistryUserDao
import com.averyvi.spiritfire.data.sources.db.HabitTagCrossRefUserDao
import com.averyvi.spiritfire.data.sources.db.TagUserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext

interface HabitRepository {
    fun getAllHabits(): Flow<List<HabitRow>>
    fun getSelectHabits(selectIds: List<Int>): Flow<List<HabitRow>>
    fun getAllHabitEntities(): Flow<List<HabitRegistryDBEntity>>
    fun getAllTags(): Flow<List<TagDBEntity>>
    fun getAllHabtTagCrossRefs(): Flow<List<HabitTagCrossRef>>
    fun getAllLogsForHabit(habitId: Int): Flow<List<HabitLogItem>>
    fun getAllLogsFromListOfHabits(selectIds: List<Int>): Flow<List<HabitLogItem>>
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