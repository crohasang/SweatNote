package com.example.sweatnote.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.example.DiaryViewModel

@Composable
fun Statistics(navController: NavHostController, viewModel: DiaryViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val exerciseCounts = viewModel.getExerciseCount().collectAsState(initial = emptyList())
    val emotionCounts = viewModel.getEmotionCount().collectAsState(initial = emptyList())


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(50.dp))

        Text("통계", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}