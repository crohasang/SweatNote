package com.example.sweatnote.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sweatnote.Calender.BottomBarItem
import com.example.sweatnote.R
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Written(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val viewModel: DiaryViewModel = viewModel()

    val date = navController.currentBackStackEntry?.arguments?.getString("date") ?: ""
    val diary = viewModel.getDiaryByDate(date).collectAsState(initial = null)

    // 일기 삭제 여부 모달 상태
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }


    // 편집 버튼 클릭 시 실행되는 함수
    fun handleEditClick() {
        navController.navigate(Routes.Writing.route + "/$date")
    }

    // 삭제 버튼 클릭 시 실행되는 함수
    fun handleDeleteClick() {
        setShowDialog(true)
    }

    // 일기 삭제 모달이 켜졌을 때
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            title = {
                Text(text = "일기를 삭제하시겠습니까?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if(diary != null) {
                                diary.value?.let { viewModel.delete(it) }
                                navController.navigate(Routes.Main.route)
                            }

                        }
                    }
                ) {
                    Text("예")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        setShowDialog(false)
                    }
                ) {
                    Text("아니오")
                }
            }
        )
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp)
                    .padding(bottom = 50.dp)
                    .verticalScroll(scrollState), // 세로 스크롤을 적용합니다.
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,

                ) {
                Spacer(modifier = Modifier.height(50.dp))



                Text(
                    "일기", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(
                        onClick = { handleEditClick() },
                        colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)) // 배경색을 투명하게 설정합니다.
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "수정",
                            tint = Color.Black
                        ) // 수정 아이콘으로 변경
                    }
                    Button(
                        onClick = { handleDeleteClick() },
                        colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)) // 배경색을 투명하게 설정합니다.
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "삭제",
                            tint = Color.Black
                        ) // 삭제 아이콘으로 변경
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text("수행한 운동", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("수행한 운동: ${diary.value?.exercises?.joinToString(", ") ?: "운동 없음"}")



                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // 감정 체크박스
                Text("감정을 선택하세요", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("감정 상태: ${diary.value?.emotion?.name ?: "감정 없음"}")

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Text("작성된 일기", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text("${diary.value?.content ?: "내용 없음"}")

                Spacer(modifier = Modifier.height(50.dp))

            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(bottom = 30.dp, start = 40.dp, end = 40.dp),
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