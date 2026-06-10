package com.averyvi.spiritfire.data.definitions.habits

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.averyvi.spiritfire.R
import java.util.Date

@Entity(
    tableName = "habit_registry"
)
data class HabitRegistryDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "Flame",
    @ColumnInfo(name = "description") val description: String = "Something to do.",
    @ColumnInfo(name = "icon") val icon: Int = R.drawable.r_outline_dark_mode_2,
    @ColumnInfo(name = "colour") val colour: Int = FColour.Red.color.toArgb(),
    @ColumnInfo(name = "resetType") val resetType: ResetDaysType = ResetDays.DAILY.resetType,
    @ColumnInfo(name = "resetDays") val resetDays: Int = ResetDays.DAILY.resetDays,
    @ColumnInfo(name = "resetHour") val resetHour: Int = 0,
    @ColumnInfo(name = "resetMinute") val resetMinute: Int = 0,
    @ColumnInfo(name = "resetOffset") val resetOffset: Int = 0,
    @ColumnInfo(name = "checksAmount") val checksAmount: Int = 0,
    @ColumnInfo(name = "checksComplete") val checksComplete: Int = 0,
    @ColumnInfo(name = "checksType") val checksType: Int = checkTypes.COMPLETIONS.stringRef,
    @ColumnInfo(name = "checksSkipGrace") val checksSkipGrace: Int = 0,
    @ColumnInfo(name = "checksNames") val checksNames: String = "",
    @ColumnInfo(name = "priority") val priority: Int = 0,
    @ColumnInfo(name = "cooldownHours") val cooldownHours: Int = 0,
    @ColumnInfo(name = "cooldownMinute") val cooldownMinutes: Int = 0,
    @ColumnInfo(name = "cooldownSeconds") val cooldownSeconds: Int = 0,
    @ColumnInfo(name = "difficulty") val difficulty: Int = 0,
)

@Entity(
    tableName = "habit_log",
    foreignKeys = [
        ForeignKey(
            entity = HabitRegistryDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habit"])]
)
data class HabitLogDBEntity(
    @PrimaryKey(autoGenerate = false) val logTime: Long = Date(0).time,
    @ColumnInfo(name = "checks") val checks: Int = 0,
    @ColumnInfo(name = "habit") val habit: Int = 0,
)




@Entity(
    tableName = "habit_tag_cross_ref",
    primaryKeys = ["habitId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = HabitRegistryDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("tagId")]
)
data class HabitTagCrossRef(
    val habitId: Int,
    val tagId: Int
)

data class HabitWithTags(
    @Embedded
    val habit: HabitRegistryDBEntity,

    @Relation(
        parentColumn = "habitId",
        entityColumn = "tagId",
        associateBy = Junction(HabitTagCrossRef::class)
    )
    val tags: List<TagDBEntity>
)





@Entity(
    tableName = "tag_registry",
    foreignKeys = [
        ForeignKey(
            entity = HabitRegistryDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habit"])]
)
data class TagDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "colour") val colour: Int = FColour.Red.color.toArgb(),
)