package com.example.sweatnote.Calender

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavHostController) {
    var date by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text ="Sweat Note", fontSize=50.sp, fontStyle = FontStyle.Italic)},
                modifier = Modifier.padding(top = 50.dp)
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){
                AndroidView(factory = {CalendarView(it)}, update = {
                    it.setOnDateChangeListener { calendarView, year, month, day ->
                        date = "$day - ${month + 1} - $year"
                        navController.navigate(Routes.Writing.route)
                    }
                })
                Text(text = date)
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp).padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "일기",
                    modifier = Modifier
                        .weight(1f) // 가로 공간을 동일하게 분할하여 차지하도록 설정
                        .clickable { navController.navigate(Routes.Main.route) }, // 클릭 시 navigate 호출
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                Text(
                    text = "통계",
                    modifier = Modifier
                        .weight(1f) // 가로 공간을 동일하게 분할하여 차지하도록 설정
                        .clickable { navController.navigate(Routes.Statistics.route) }, // 클릭 시 navigate 호출
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                Text(
                    text = "검색",
                    modifier = Modifier
                        .weight(1f) // 가로 공간을 동일하게 분할하여 차지하도록 설정
                        .clickable { navController.navigate(Routes.Search.route) }, // 클릭 시 navigate 호출
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
        }
    )

}

@Preview
@Composable
fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(navController)
}