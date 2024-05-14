package com.example.sweatnote.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sweatnote.navigation.Routes

@Composable
fun Search(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text="Screen Search",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Button(onClick = {
            navController.navigate(Routes.Main.route)
        }){
            Text(text = "Go to Screen Main")
        }

        Button(onClick = {
            navController.navigate(Routes.Statistics.route)
        }){
            Text(text = "Go to Screen Statistics")
        }
        Button(onClick = {
            navController.navigate(Routes.Writing.route)
        }){
            Text(text = "Go to Screen Writing")
        }
    }
}