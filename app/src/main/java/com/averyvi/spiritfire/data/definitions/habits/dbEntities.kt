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
    @ColumnInfo(name = "cooldownMinutes") val cooldownMinutes: Int = 0,
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
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = HabitTagCrossRef::class,
            parentColumn = "habitId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagDBEntity>
)

fun HabitWithTags.toHabitRow(): HabitRow {
    return HabitRow(
        id = this.habit.id,
        name = this.habit.name,
        description = this.habit.description,
        icon = this.habit.icon,
        colour = Color(this.habit.colour),
        resetType = this.habit.resetType,
        resetDays = this.habit.resetDays,
        resetHour = this.habit.resetHour,
        resetMinute = this.habit.resetMinute,
        resetOffset = this.habit.resetOffset,
        checksAmount = this.habit.checksAmount,
        checksComplete = this.habit.checksComplete,
        checksType = this.habit.checksType,
        checksSkipGrace = this.habit.checksSkipGrace,
        checksNames = this.habit.checksNames,
        priority = this.habit.priority,
        cooldownHours = this.habit.cooldownHours,
        cooldownMinutes = this.habit.cooldownMinutes,
        cooldownSeconds = this.habit.cooldownSeconds,
        difficulty = this.habit.difficulty,
        tags = this.tags.map { tagDbEntity ->
            TagUIEntity(
                id = tagDbEntity.id,
                name = tagDbEntity.name,
                colour = tagDbEntity.colour
            )
        }
    )
}





@Entity(
    tableName = "tag_registry",
)
data class TagDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "colour") val colour: Int = FColour.Red.color.toArgb(),
)