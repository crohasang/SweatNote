package com.example.sweatnote.viewmodel

import android.app.Application
import com.example.sweatnote.roomDB.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiaryRepository(application: Application) {
    private val diaryDao: DiaryDao
    val allDiaries: Flow<List<Diary>>

    init {
        val database = DiaryDatabase.getInstance(application)
        diaryDao = database.diaryDao()
        allDiaries = diaryDao.getAllDiaries()
    }

    suspend fun insert(diary: Diary) {
        diaryDao.insert(diary)
    }

    suspend fun update(diary: Diary) {
        diaryDao.update(diary)
    }

    suspend fun delete(diary: Diary) {
        diaryDao.delete(diary)
    }

    fun searchDiariesByKeyword(keyword: String): Flow<List<Diary>> {
        return diaryDao.searchDiariesByKeyword(keyword)
    }

    fun getDiaryByDate(date: String): Flow<Diary?> {
        return diaryDao.getDiaryByDate(date)
    }

    // 운동 횟수와 감정의 횟수를 가져오는 메서드
    fun getExerciseCounts(): Flow<Map<ExerciseType, Int>> {
        return allDiaries.map { diaries ->
            diaries.flatMap { it.exercises }.groupingBy { it }.eachCount()
        }
    }

    fun getEmotionCounts(): Flow<Map<EmotionType, Int>> {
        return allDiaries.map { diaries ->
            diaries.groupingBy { it.emotion }.eachCount()
        }
    }
}
