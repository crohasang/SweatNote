package com.example.sweatnote.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sweatnote.R
import com.example.sweatnote.navigation.Routes

@Composable
fun BottomBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(/*, start = 30.dp, end = 30.dp*/),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        BottomBarItem(
            text = "일기",
            iconResId = R.drawable.baseline_edit_calendar_24,
            isSelected = currentRoute == Routes.Main.route,
            onClick = { navController.navigate(Routes.Main.route) },
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            text = "통계",
            iconResId = R.drawable.baseline_insert_chart_outlined_24,
            isSelected = currentRoute == Routes.Statistics.route,
            onClick = { navController.navigate(Routes.Statistics.route) },
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            text = "검색",
            iconResId = R.drawable.baseline_manage_search_24,
            isSelected = currentRoute == Routes.Search.route,
            onClick = { navController.navigate(Routes.Search.route) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomBarItem(text: String, iconResId: Int, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    val itemcolor = if(isSelected) Color.White else Color.LightGray
    val textcolor = Color.Black

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = itemcolor)
            .fillMaxWidth()
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textcolor
        )
    }
}
