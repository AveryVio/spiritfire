package com.averyvi.spiritfire.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitForList
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitRegistryUserDao {
    @Insert
    fun insert(habit: HabitRegistryDBEntity)

    @Delete
    fun delete(habit: HabitRegistryDBEntity)

    @Query("SELECT * FROM habit_registry")
    fun getAll(): List<HabitRegistryDBEntity>

    @Query("SELECT * FROM habit_registry WHERE id LIKE :selectId")
    fun getAllById(selectId: Int): List<HabitRegistryDBEntity>

    @Query("SELECT name FROM habit_registry")
    fun getAllNames(): List<String>


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

    @Query("SELECT * FROM habit_log WHERE habit LIKE :selectId")
    fun getAllByHabit(selectId: Int): List<HabitLogDBEntity>
}