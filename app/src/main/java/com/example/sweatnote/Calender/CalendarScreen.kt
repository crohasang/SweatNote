package com.example.sweatnote.Calender

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.R
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
                title = { Text(text = "Sweat Note", fontSize = 50.sp, fontStyle = FontStyle.Italic) },
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
                        date = "$year-${month + 1}-$day"
                        navController.navigate("${Routes.Writing.route}/$date")
                    }
                })
                Text(text = date)
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp).padding(bottom = 30.dp, start = 40.dp, end = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
        modifier = Modifier.clickable(onClick = onClick),
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
