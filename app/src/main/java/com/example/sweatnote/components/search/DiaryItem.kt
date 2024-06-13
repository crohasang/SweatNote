package com.example.sweatnote.components.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sweatnote.navigation.Routes
import com.example.sweatnote.roomDB.Diary
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DiaryItem(diary: Diary, navController: NavHostController) {
    val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())
    val date = inputFormat.parse(diary.date)
    val formattedDate = outputFormat.format(date)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable() {
                try {
                    navController.navigate(Routes.Written.createRoute(diary.date))
                } catch (e: Exception) {
                    Log.e("Written", "Error during going written", e)
                }
            },
        color = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = formattedDate, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = diary.content)
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp)
}
