package com.example.sweatnote.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.sweatnote.roomDB.Diary
import com.example.sweatnote.roomDB.DiaryDao
import com.example.sweatnote.roomDB.DiaryDatabase
import com.example.sweatnote.roomDB.EmotionType
import com.example.sweatnote.roomDB.ExerciseType
import kotlinx.coroutines.flow.Flow

class DiaryRepository(application: Application) {
    private val diaryDao: DiaryDao
    val allDiaries: LiveData<List<Diary>>

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

    fun searchDiariesByKeyword(keyword: String): LiveData<List<Diary>> {
        return diaryDao.searchDiariesByKeyword(keyword)
    }

    fun getDiaryByDate(date: String): Flow<Diary?>{
        return diaryDao.getDiaryByDate(date)
    }

    // 운동 횟수와 감정의 횟수를 가져오는 메서드 추가
    fun getExerciseCounts(): LiveData<Map<ExerciseType, Int>> {
        val result = MediatorLiveData<Map<ExerciseType, Int>>()
        result.addSource(allDiaries) { diaries ->
            result.value = diaries.flatMap { it.exercises }.groupingBy { it }.eachCount()
        }
        return result
    }

    fun getEmotionCounts(): LiveData<Map<EmotionType, Int>> {
        val result = MediatorLiveData<Map<EmotionType, Int>>()
        result.addSource(allDiaries) { diaries ->
            result.value = diaries.groupingBy { it.emotion }.eachCount()
        }
        return result
    }

}
