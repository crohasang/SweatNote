package com.example.sweatnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.navigation.NavGraph
import com.example.sweatnote.ui.theme.SweatNoteTheme
import com.example.sweatnote.example.DiaryViewModel

class MainActivity : ComponentActivity() {
    private lateinit var diaryViewModel: DiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        diaryViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(DiaryViewModel::class.java)

        setContent {
            SweatNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController, diaryViewModel = diaryViewModel)
                }
            }
        }
    }
}
