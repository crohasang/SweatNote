package com.example.sweatnote.graphViews

import BarChartView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("운동 횟수 통계", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "횟수",
            color = Color.Gray,
            fontSize = 14.sp,
        )
        BarChartView(modifier = Modifier.height(300.dp))
        Spacer(modifier = Modifier.height(30.dp))
        Text("운동 감정 통계", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "백분율",
            color = Color.Gray,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PieChartView(modifier = Modifier.height(300.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    StatsScreen()
}