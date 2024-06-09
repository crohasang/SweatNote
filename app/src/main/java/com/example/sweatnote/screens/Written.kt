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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.sweatnote.Calender.BottomBarItem
import com.example.sweatnote.R
import com.example.sweatnote.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Written(navController: NavHostController) {
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 일기 삭제 여부 모달 상태
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    // 편집 버튼 클릭 시 실행되는 함수
    fun handleEditClick() {
        // Writing 페이지로 이동하는데, 해당 날짜의 기록된 정보들이 반영되어 있어야 함
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
                            // 데이터베이스에서 일기를 삭제하는 코드

                            // 예시
//                            val diaryDatabase = DiaryDatabase.getInstance(context)
//                            diaryDatabase.diaryDao().delete(/* diaryEntry */)

                            // 메인 화면으로 돌아가는 코드
                            suspend {navController.navigate(Routes.Main.route)}
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
                Text("가슴(예시)")
                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // 감정 체크박스
                Text("감정을 선택하세요", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("긍정적(예시)")

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Text("작성된 일기", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "대통령은 국무총리·국무위원·행정각부의 장 기타 법률이 정하는 공사의 직을 겸할 수 없다. 정기회의 회기는 100일을, 임시회의 회기는 30일을 초과할 수 없다. 모든 국민은 근로의 권리를 가진다. 국가는 사회적·경제적 방법으로 근로자의 고용의 증진과 적정임금의 보장에 노력하여야 하며, 법률이 정하는 바에 의하여 최저임금제를 시행하여야 한다.\n" +
                            "\n" +
                            "법원은 최고법원인 대법원과 각급법원으로 조직된다. 대통령이 궐위되거나 사고로 인하여 직무를 수행할 수 없을 때에는 국무총리, 법률이 정한 국무위원의 순서로 그 권한을 대행한다. 모든 국민은 신체의 자유를 가진다. 누구든지 법률에 의하지 아니하고는 체포·구속·압수·수색 또는 심문을 받지 아니하며, 법률과 적법한 절차에 의하지 아니하고는 처벌·보안처분 또는 강제노역을 받지 아니한다.\n" +
                            "\n" +
                            "대통령은 조약을 체결·비준하고, 외교사절을 신임·접수 또는 파견하며, 선전포고와 강화를 한다. 대통령은 국가의 독립·영토의 보전·국가의 계속성과 헌법을 수호할 책무를 진다. 모든 국민은 보건에 관하여 국가의 보호를 받는다. 국무위원은 국무총리의 제청으로 대통령이 임명한다. 의무교육은 무상으로 한다.\n"
                )


                Spacer(modifier = Modifier.height(50.dp))

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