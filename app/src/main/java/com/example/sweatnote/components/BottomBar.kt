package com.example.sweatnote.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.R
import com.example.sweatnote.navigation.Routes

@Composable
fun BottomBar(navController: NavHostController) {
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