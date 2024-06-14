package com.example.sweatnote.screens

import BarChartView
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.components.BottomBar
import com.example.sweatnote.components.TopBar
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.graphViews.PieChartView


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatsScreen(navController: NavHostController, viewModel: DiaryViewModel) {
    val exerciseCounts by viewModel.exerciseCounts.collectAsState()
    val emotionCounts by viewModel.emotionCounts.collectAsState()
    val scrollState = rememberScrollState()


    Scaffold(
        topBar = { TopBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .padding(top = 70.dp)
                    .verticalScroll(scrollState)
            ) {
                Text("운동 횟수 통계", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "횟수",
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                BarChartView(modifier = Modifier.height(300.dp), exerciseCounts = exerciseCounts)
                Spacer(modifier = Modifier.height(30.dp))
                Text("운동 감정 통계", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "백분율",
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                PieChartView(modifier = Modifier.height(300.dp), emotionCounts = emotionCounts)
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}
