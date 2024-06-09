package com.example.sweatnote.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.Mproject.viewmodel.DiaryRepository
import com.example.sweatnote.roomDB.Diary
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DiaryRepository = DiaryRepository(application)
    val allDiaries: LiveData<List<Diary>> = repository.allDiaries

    fun insert(diary: Diary) {
        viewModelScope.launch {
            repository.insert(diary)
        }
    }

    fun update(diary: Diary) {
        viewModelScope.launch {
            repository.update(diary)
        }
    }

    fun delete(diary: Diary) {
        viewModelScope.launch {
            repository.delete(diary)
        }
    }

    fun searchDiariesByKeyword(keyword: String): LiveData<List<Diary>> {
        return repository.searchDiariesByKeyword(keyword)
    }

    fun getDiaryByDate(date: String): LiveData<Diary?> {
        return repository.getDiaryByDate(date)
    }
}
