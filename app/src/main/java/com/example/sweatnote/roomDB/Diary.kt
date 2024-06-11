package com.example.sweatnote.roomDB

import android.os.Parcelable
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
    어깨, 가슴, 등, 하체, 유산소
}

enum class EmotionType {
    최악이에요, 별로에요, 보통이에요, 좋아요, 최고에요
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
