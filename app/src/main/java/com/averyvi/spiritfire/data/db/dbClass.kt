package com.averyvi.spiritfire.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.averyvi.spiritfire.data.definitions.habits.HabitLogDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitRegistryDBEntity
import com.averyvi.spiritfire.data.definitions.habits.HabitTagCrossRef
import com.averyvi.spiritfire.data.definitions.habits.TagDBEntity

@Database(
    entities = [
        HabitRegistryDBEntity::class,
        HabitLogDBEntity::class,
        TagDBEntity::class,
        HabitTagCrossRef::class,
               ],
    version = 1,
    exportSchema = false)
abstract class HabitRegistry : RoomDatabase() {
    abstract fun HabitRegistryDAO(): HabitRegistryUserDao

    abstract fun HabitLogDAO(): HabitLogUserDao
}