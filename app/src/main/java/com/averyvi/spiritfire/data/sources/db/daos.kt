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
    fun getAllEntities(): Flow<List<HabitRegistryDBEntity>>

    @Query("SELECT * FROM habit_registry WHERE id = :selectId")
    fun getAllById(selectId: Int): Flow<List<HabitRegistryDBEntity>>

    @Query("SELECT * FROM habit_registry WHERE id IN (:selectIds)")
    fun getAllFromListOfIds(selectIds: List<Int>): Flow<List<HabitWithTags>>

    @Query("SELECT name FROM habit_registry")
    fun getAllNames(): Flow<List<String>>

    @Transaction
    @Query("SELECT * FROM habit_registry")
    fun getAllHabits(): Flow<List<HabitWithTags>>

    @Query("SELECT id, name, icon, colour FROM habit_registry")
    fun getAllHabitsForList(): Flow<List<HabitForList>>

    @Query("""
                SELECT habit_registry.* FROM habit_registry
                INNER JOIN habit_tag_cross_ref ON habit_registry.id = habit_tag_cross_ref.habitId
                WHERE habit_tag_cross_ref.tagId = :selectTag
            """)
    fun getAllByTag(selectTag: Int): Flow<List<HabitWithTags>>
}

@Dao
interface HabitLogUserDao {
    @Insert
    fun insert(log: HabitLogDBEntity)

    @Delete
    fun delete(log: HabitLogDBEntity)

    @Query("SELECT * FROM habit_log")
    fun getAll(): Flow<List<HabitLogDBEntity>>

    @Query("SELECT * FROM habit_log WHERE habit = :selectId")
    fun getAllByHabit(selectId: Int): Flow<List<HabitLogItem>>

    @Query("SELECT * FROM habit_log WHERE habit IN (:selectIds)")
    fun getAllFromListOfHabits(selectIds: List<Int>): Flow<List<HabitLogItem>>
}

@Dao
interface TagUserDao {
    @Insert
    fun insert(log: TagDBEntity)

    @Delete
    fun delete(log: TagDBEntity)

    @Query("SELECT * FROM tag_registry")
    fun getAll(): Flow<List<TagDBEntity>>

    @Query("SELECT * FROM tag_registry WHERE id = :selectId")
    fun getAllById(selectId: Int): Flow<List<TagDBEntity>>

    @Query("""
                SELECT tag_registry.* FROM tag_registry
                INNER JOIN habit_tag_cross_ref ON tag_registry.id = habit_tag_cross_ref.tagId
                WHERE habit_tag_cross_ref.habitId = :selectHabit
        """)
    fun getAllTagsByHabit(selectHabit: Int): Flow<List<TagDBEntity>>
}

@Dao
interface HabitTagCrossRefUserDao {
    @Insert
    fun insert(log: HabitTagCrossRef)

    @Delete
    fun delete(log: HabitTagCrossRef)

    @Query("SELECT * FROM habit_tag_cross_ref")
    fun getAll(): Flow<List<HabitTagCrossRef>>
}