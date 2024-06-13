package com.example.sweatnote.Calender

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.R
import com.example.sweatnote.components.BottomBar
import com.example.sweatnote.components.main.DateButtonRow
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.roomDB.Diary
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun CalendarScreen(navController: NavHostController) {
    var date by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)) }

    var flag by remember { mutableStateOf(false)}
    val coroutineScope = rememberCoroutineScope()
    val viewModel: DiaryViewModel = viewModel()

    val dancingscript = FontFamily(Font(R.font.dancingscript_semibold, FontWeight.SemiBold, FontStyle.Italic))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text ="Sweat Note", fontSize=50.sp, fontFamily = dancingscript, fontWeight = FontWeight.SemiBold)},
                modifier = Modifier.padding(top = 50.dp)
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(factory = { CalendarView(it) }, update = {
                    it.setOnDateChangeListener { _, year, month, day ->
//                        date = "$day - ${month + 1} - $year"
//                        date = "$year-${String.format("%02d", month + 1)}-${String.format("%02d", day)}"
                        date = "$year${String.format("%02d", month + 1)}${String.format("%02d", day)}"
                        coroutineScope.launch {
                            viewModel.getDiaryByDate(date).collect { diary: Diary? ->
                                if (diary != null) {
                                    flag = true
                                } else {
                                    flag = false

                                }
                            }
                        }
                    }
                    it.dateTextAppearance

//                    it.maxDate=Calendar.getInstance().timeInMillis
//                    it.setDate(dateFormat.parse(date)?.time ?:0)
                })
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.Gray
                )

                println("flag in Calendar: " + flag)
                println("date in Calendar: " + date)
                DateButtonRow(flag, date, navController)
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    )

}
@Preview
@Composable
fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(navController)
}