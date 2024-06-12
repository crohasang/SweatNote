package com.example.sweatnote.Calender

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.R
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.navigation.Routes
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavHostController) {
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var flag by remember { mutableStateOf(false)}
    val coroutineScope = rememberCoroutineScope()
    val viewModel: DiaryViewModel = viewModel()

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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
                        //date = "$day - ${month + 1} - $year"
                        date = "$year-${String.format("%02d", month + 1)}-${String.format("%02d", day)}"
                        coroutineScope.launch {
                            viewModel.getDiaryByDate(date).collect { diary ->
                                if (diary != null) {
                                    flag = true
                                    //navController.navigate(Routes.Written.createRoute(date))
                                } else {
                                    flag = false
                                    //navController.navigate(Routes.Writing.createRoute(date))
                                }
                            }
                        }
                    }
                    it.maxDate=Calendar.getInstance().timeInMillis
                    it.setDate(dateFormat.parse(date)?.time ?:0)
                })
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.Gray
                )
                Row(modifier = Modifier.fillMaxWidth().padding(top=10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    if(flag){
                        Text("작성된 일기가 있습니다.", modifier = Modifier.padding(end = 20.dp), fontSize = 20.sp)
                        Button(onClick= { navController.navigate(Routes.Written.createRoute(date)) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(
                                ContextCompat.getColor(LocalContext.current, R.color.teal_700)),
                                contentColor = Color.White)){
                            Text("조회")
                        }
                    }else{
                        Text("작성된 일기가 없습니다.", modifier = Modifier.padding(end = 20.dp), fontSize = 20.sp)
                        Button(onClick={ navController.navigate(Routes.Writing.createRoute(date)) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(
                                ContextCompat.getColor(LocalContext.current, R.color.teal_700)),
                                contentColor = Color.White)){
                            Text("작성")
                        }
                    }
                }
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
@Composable
fun BottomBarItem(text: String, iconResId: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Preview
@Composable
fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(navController)
}