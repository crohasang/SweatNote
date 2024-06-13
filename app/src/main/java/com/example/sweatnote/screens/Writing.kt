package com.example.sweatnote.screens

import DiaryTextField
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.components.BottomBar
import com.example.sweatnote.components.TopBar
import com.example.sweatnote.components.writing.CheckboxWithText
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.navigation.Routes
import com.example.sweatnote.roomDB.Diary
import com.example.sweatnote.roomDB.EmotionType
import com.example.sweatnote.roomDB.ExerciseType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Writing(navController: NavHostController, viewModel: DiaryViewModel, date: String) {
    val workoutOptions = listOf("어깨", "가슴", "등", "하체", "유산소")


    // 감정을 선택할 수 있는 버튼 추가
    val feelings = listOf("최악이에요", "별로에요", "보통이에요", "좋아요", "최고에요")


    // navController에서 날짜를 가져옴
    var diary by remember { mutableStateOf<Diary?>(null) }
    var selectedWorkouts by remember { mutableStateOf(listOf<String>()) }
    var selectedFeeling by remember { mutableStateOf("") }
    var diaryEntry by remember { mutableStateOf("") }


    LaunchedEffect(key1 = date) {
        viewModel.getDiaryByDate(date).collect {
            diary = it
            // 일기가 존재하면 해당 일기의 운동, 감정, 내용을 가져옴(그렇지 않으면 기본값을 사용)
            selectedWorkouts = diary?.exercises?.map { it.name } ?: listOf()
            selectedFeeling = (diary?.emotion ?: "").toString()
            diaryEntry = diary?.content ?: ""

        }
    }


    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // 제출하기 버튼을 클릭했을 때 실행되는 함수
    fun handleSubmitClick() {
        coroutineScope.launch {
            try {
                val diary = Diary(
                    date = date,
                    content = diaryEntry,
                    emotion = EmotionType.valueOf(selectedFeeling.toString()),
                    exercises = selectedWorkouts.map {ExerciseType.valueOf(it)},
                    keywords = diaryEntry

                )
                Log.d("Writing", "Inserting diary entry: $diary")
                viewModel.insert(diary)
                Log.d("Writing", "Diary entry inserted successfully")
                Toast.makeText(context, "Diary entry inserted", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.Main.route)
            } catch (e: Exception) {
                Log.e("Writing", "Error inserting diary entry", e)
                Toast.makeText(context, "Error inserting diary entry", Toast.LENGTH_SHORT).show()
            }
        }
    }


    Scaffold(
        topBar = { TopBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) { keyboardController?.hide() },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,

                ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    "일기 작성", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Text("수행한 운동을 선택하세요", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    itemsIndexed(workoutOptions) { index, workout ->
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
                        if (index < workoutOptions.size - 1) {
                            Spacer(modifier = Modifier.width(16.dp)) // 체크박스 사이에 간격을 추가합니다.
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Gray, thickness = 2.dp)
                Spacer(modifier = Modifier.height(8.dp))

                // 감정 체크박스
                Text("감정을 선택하세요", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    itemsIndexed(feelings) { index, feeling ->
                        CheckboxWithText(
                            isChecked = feeling == selectedFeeling,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedFeeling = feeling
                                }
                            },
                            text = feeling
                        )
                        if (index < feelings.size - 1) {
                            Spacer(modifier = Modifier.width(3.dp)) // 체크박스 사이에 간격을 추가합니다.
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Gray, thickness = 2.dp)
                Spacer(modifier = Modifier.height(8.dp))

                Text("일기 작성", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), // 버튼과 TextField 사이에 간격을 추가합니다.
                    verticalArrangement = Arrangement.Bottom, // 버튼을 아래에 위치시킵니다.
                    horizontalAlignment = Alignment.End // 버튼을 오른쪽에 위치시킵니다.
                ) {
                    DiaryTextField(
                        value = diaryEntry,
                        onValueChange = { diaryEntry = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { handleSubmitClick() },
                        colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)) // 배경색을 투명하게 설정합니다.
                    ) {
                        Text(
                            "제출하기",
                            color = Black,
                            fontWeight = FontWeight.Bold
                        ) // 글자 색깔을 검은색으로, 글자를 굵게 설정합니다.
                    }
                }

            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}
