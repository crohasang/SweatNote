package com.example.sweatnote.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweatnote.roomDB.Diary
import com.example.sweatnote.roomDB.EmotionType
import com.example.sweatnote.roomDB.ExerciseType
import com.example.sweatnote.viewmodel.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DiaryRepository = DiaryRepository(application)
    val allDiaries: Flow<List<Diary>> = repository.allDiaries

    private val _exerciseCounts = MutableStateFlow<Map<ExerciseType, Int>>(emptyMap())
    val exerciseCounts: StateFlow<Map<ExerciseType, Int>> = _exerciseCounts

    private val _emotionCounts = MutableStateFlow<Map<EmotionType, Int>>(emptyMap())
    val emotionCounts: StateFlow<Map<EmotionType, Int>> = _emotionCounts

    init {
        viewModelScope.launch {
            repository.getExerciseCounts().collect {
                _exerciseCounts.value = it
            }
        }
        viewModelScope.launch {
            repository.getEmotionCounts().collect {
                _emotionCounts.value = it
            }
        }
    }

    fun getExerciseCount(): Flow<Map<ExerciseType, Int>> {
        return repository.getExerciseCounts()
    }

    fun getEmotionCount(): Flow<Map<EmotionType, Int>> {
        return repository.getEmotionCounts()
    }

    fun getDiaryByDate(date: String): Flow<Diary?> {
        return repository.getDiaryByDate(date)
    }

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

    fun searchDiariesByKeyword(keyword: String): Flow<List<Diary>> {
        return repository.searchDiariesByKeyword(keyword)
    }
}
