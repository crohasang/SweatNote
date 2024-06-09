package com.example.Mproject.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.Mproject.roomDB.Diary
import com.example.Mproject.roomDB.DiaryDao
import com.example.Mproject.roomDB.DiaryDatabase
import com.example.sweatnote.roomDB.DiaryDao

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

    fun getDiaryByDate(date: String): LiveData<Diary?> {
        return diaryDao.getDiaryByDate(date)
    }
}
