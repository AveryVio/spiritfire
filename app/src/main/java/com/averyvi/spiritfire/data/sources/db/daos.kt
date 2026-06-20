package com.averyvi.spiritfire.data.sources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitLogItem
import com.averyvi.spiritfire.data.definitions.habits.HabitRow
import com.averyvi.spiritfire.data.definitions.habits.HabitTagCrossRef
import com.averyvi.spiritfire.data.definitions.habits.HabitWithTags
import com.averyvi.spiritfire.data.definitions.habits.TagDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitRegistryUserDao {
    @Insert
    fun insert(habit: HabitRegistryDBEntity)

    @Delete
    fun delete(habit: HabitRegistryDBEntity)

    @Query("SELECT * FROM habit_registry")
    fun getAll(): List<HabitRegistryDBEntity>

    @Query("SELECT * FROM habit_registry WHERE id = :selectId")
    fun getAllById(selectId: Int): List<HabitRegistryDBEntity>

    @Query("SELECT name FROM habit_registry")
    fun getAllNames(): List<String>

    @Transaction
    @Query("SELECT * FROM habit_registry")
    fun getAllHabits(): Flow<List<HabitWithTags>>

    @Query("SELECT id, name, icon, colour FROM habit_registry")
    fun getAllHabitsForList(): Flow<List<HabitForList>>
}

@Dao
interface HabitLogUserDao {
    @Insert
    fun insert(log: HabitLogDBEntity)

    @Delete
    fun delete(log: HabitLogDBEntity)

    @Query("SELECT * FROM habit_log")
    fun getAll(): List<HabitLogDBEntity>

    @Query("SELECT * FROM habit_log WHERE habit = :selectId")
    fun getAllByHabit(selectId: Int): Flow<List<HabitLogItem>>
}

@Dao
interface TagUserDao {
    @Insert
    fun insert(log: TagDBEntity)

    @Delete
    fun delete(log: TagDBEntity)

    @Query("SELECT * FROM habit_log")
    fun getAll(): List<TagDBEntity>

    @Query("SELECT * FROM habit_log WHERE habit = :selectId")
    fun getAllByHabit(selectId: Int): Flow<List<TagDBEntity>>
}

@Dao
interface HabitTagCrossRefUserDao {
    @Insert
    fun insert(log: HabitTagCrossRef)

    @Delete
    fun delete(log: HabitTagCrossRef)

    @Query("SELECT * FROM habit_tag_cross_ref")
    fun getAll(): List<HabitTagCrossRef>

    @Query("SELECT habitId FROM habit_tag_cross_ref WHERE tagId = :selectTag")
    fun getAllByTag(selectTag: Int): List<HabitRegistryDBEntity>
}