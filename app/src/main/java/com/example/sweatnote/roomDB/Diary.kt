package com.example.sweatnote.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val content: String,
    val emotion: EmotionType,
    val exercises: List<ExerciseType>,
    val keywords: String?
)

enum class ExerciseType {
    SHOULDER, CHEST, BACK, LEGS, CARDIO
}

enum class EmotionType {
    VERY_NEGATIVE, NEGATIVE, NEUTRAL, POSITIVE, VERY_POSITIVE
}

class Converters {
    @TypeConverter
    fun fromEmotionType(value: EmotionType): String = value.name

    @TypeConverter
    fun toEmotionType(value: String): EmotionType = EmotionType.valueOf(value)

    @TypeConverter
    fun fromExerciseTypeList(value: List<ExerciseType>): String =
        value.joinToString(",") { it.name }

    @TypeConverter
    fun toExerciseTypeList(value: String): List<ExerciseType> =
        value.split(",").map { ExerciseType.valueOf(it) }
}
