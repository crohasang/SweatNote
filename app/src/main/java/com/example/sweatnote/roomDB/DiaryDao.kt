package com.example.sweatnote.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Insert
    suspend fun insert(diary: Diary)

    @Update
    suspend fun update(diary: Diary)

    @Delete
    suspend fun delete(diary: Diary)

    @Query("SELECT * FROM diary ORDER BY date DESC")
    fun getAllDiaries(): Flow<List<Diary>> // LiveData는 데이터베이스 변경을 자동으로 UI에 반영

    @Query("SELECT * FROM diary WHERE keywords LIKE '%' || :keyword || '%'")
    fun searchDiariesByKeyword(keyword: String): Flow<List<Diary>>

    @Query("SELECT * FROM diary WHERE date = :date")
    fun getDiaryByDate(date: String): Flow<Diary?>

    @Query("SELECT exercises, COUNT(*) as count FROM diary GROUP BY exercises")
    fun getExerciseCount(): Flow<List<ExerciseCount>>

    @Query("SELECT emotion, COUNT(*) as count FROM diary GROUP BY emotion")
    fun getEmotionCount(): Flow<List<EmotionCount>>
}

data class ExerciseCount(
    val exercises: String,
    val count: Int
)
data class EmotionCount(
    val emotion: String,
    val count: Int
)
