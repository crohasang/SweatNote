package com.example.sweatnote.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sweatnote.roomDB.Diary
import com.example.sweatnote.roomDB.DiaryDao
import com.example.sweatnote.roomDB.DiaryDatabase
import com.example.sweatnote.roomDB.EmotionCount
import com.example.sweatnote.roomDB.ExerciseCount
import kotlinx.coroutines.flow.Flow

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

    fun getDiaryByDate(date: String): Flow<Diary?>{
        return diaryDao.getDiaryByDate(date)
    }

    fun getExerciseCount(): Flow<List<ExerciseCount>> {
        return diaryDao.getExerciseCount()
    }

    fun getEmotionCount(): Flow<List<EmotionCount>> {
        return diaryDao.getEmotionCount()
    }
}
