package com.example.sweatnote.components.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.sweatnote.R
import com.example.sweatnote.navigation.Routes

@SuppressLint("SuspiciousIndentation")
@Composable
fun DateButtonRow(flag: Boolean, date: String, navController: NavHostController) {
    println("date : " + date)

        Row(modifier = Modifier.fillMaxWidth().padding(top=10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            if(flag){
                Text("작성된 일기가 있습니다.", modifier = Modifier.padding(end = 20.dp), fontSize = 20.sp)
                Button(onClick= { try {
                    navController.navigate(Routes.Written.createRoute(date))
                } catch (e: Exception) {
                    Log.e("Written", "Error during going written", e)
                } },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(
                        ContextCompat.getColor(LocalContext.current, R.color.teal_700)),
                        contentColor = Color.White)){
                    Text("조회", fontWeight = FontWeight.Bold)
                }
            }else{
                Text("작성된 일기가 없습니다.", modifier = Modifier.padding(end = 20.dp), fontSize = 20.sp)
                Button(onClick={ try {
                    navController.navigate(Routes.Writing.createRoute(date))
                } catch (e: Exception) {
                    Log.e("Writing", "Error during going writing", e)
                } },
                    colors = ButtonDefaults.buttonColors(Color.LightGray)){
                    Text("작성", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

