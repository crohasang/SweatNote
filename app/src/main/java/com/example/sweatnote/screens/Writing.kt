package com.example.sweatnote.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sweatnote.components.CheckboxWithText

@Composable
fun Writing(navController: NavHostController) {
    val workoutOptions = listOf("어깨", "가슴", "등", "하체", "유산소")
    var selectedWorkouts by remember { mutableStateOf(listOf<String>()) }

    // 감정을 선택할 수 있는 버튼 추가
    val feelings = listOf("매우 부정적", "부정적", "보통", "긍정적", "매우 긍정적")
    var selectedFeeling by remember { mutableStateOf("") }


    // 상세한 일기를 작성할 수 있는 TextField 추가
    var diaryEntry by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("수행한 운동을 선택하세요")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..4) {
                val workout = workoutOptions[i]
                CheckboxWithText(
                    isChecked = workout in selectedWorkouts,
                    onCheckedChange = { isChecked ->
                        if (isChecked && workout !in selectedWorkouts) {
                            selectedWorkouts += workout
                        } else if (!isChecked && workout in selectedWorkouts) {
                            selectedWorkouts -= workout
                        }
                    },
                    text = workout
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Text("감정을 선택하세요")
        // 감정 체크박스
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..4) {
                val feeling = feelings[i]
                CheckboxWithText(
                    isChecked = feeling == selectedFeeling,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedFeeling = feeling
                        }
                    },
                    text = feeling
                )
                Spacer(modifier = Modifier.width(8.dp))


            }
        }

        Text("일기 작성")

    }
}
