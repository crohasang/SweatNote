package com.example.sweatnote.screens

import DiaryTextField
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.Calender.BottomBarItem
import com.example.sweatnote.R
import com.example.sweatnote.components.writing.CheckboxWithText
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Writing(navController: NavHostController, viewModel: DiaryViewModel) {
    val workoutOptions = listOf("어깨", "가슴", "등", "하체", "유산소")
    var selectedWorkouts by remember { mutableStateOf(listOf<String>()) }

    // 감정을 선택할 수 있는 버튼 추가
    val feelings = listOf("매우 부정적", "부정적", "보통", "긍정적", "매우 긍정적")
    var selectedFeeling by remember { mutableStateOf("") }


    // 상세한 일기를 작성할 수 있는 TextField 추가
    var diaryEntry by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 제출하기 버튼을 클릭했을 때 실행되는 함수
    fun handleSubmitClick() {

        // coroutineScope.launch를 통해 코드가 동기적으로(순차적으로) 실행되도록

        coroutineScope.launch {

            // 데이터베이스에 저장하는 코드를 추가

//            val diaryDatabase = DiaryDatabase.getInstance(context)
//            val diaryEntry = DiaryEntry(
//                workouts = selectedWorkouts,
//                feeling = selectedFeeling,
//                entry = diaryEntry
//            )
//            diaryDatabase.diaryDao().insert(diaryEntry)

            // 메인 화면으로 돌아감
            suspend {navController.navigate(Routes.Main.route)}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text ="Sweat Note", fontSize=30.sp, fontStyle = FontStyle.Italic)},
                modifier = Modifier.clickable{
                    navController.navigate(Routes.Main.route)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .padding(horizontal = 16.dp),
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp).padding(bottom = 30.dp, start = 40.dp, end = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                BottomBarItem(
                    text = "일기",
                    iconResId = R.drawable.baseline_edit_calendar_24,
                    onClick = { navController.navigate(Routes.Written.route) }
                )
                BottomBarItem(
                    text = "통계",
                    iconResId = R.drawable.baseline_insert_chart_outlined_24,
                    onClick = { navController.navigate(Routes.Statistics.route) }
                )
                BottomBarItem(
                    text = "검색",
                    iconResId = R.drawable.baseline_manage_search_24,
                    onClick = { navController.navigate(Routes.Search.route) }
                )
            }
        }
    )
}
