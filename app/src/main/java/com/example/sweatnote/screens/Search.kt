package com.example.sweatnote.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sweatnote.R
import com.example.sweatnote.components.BottomBar
import com.example.sweatnote.components.search.DiaryItem
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.navigation.Routes
import com.example.sweatnote.roomDB.Diary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(navController: NavHostController, viewModel: DiaryViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Diary>>(emptyList()) }
    var searchPerformed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val dancingscript = FontFamily(Font(R.font.dancingscript_semibold, FontWeight.SemiBold, FontStyle.Italic))

    // Log and error handling
    fun handleSearchClick() {
        scope.launch {
            try {
                viewModel.searchDiariesByKeyword(text).collect { results ->
                    searchResults = results
                    searchPerformed = true
                    Log.d("Search", "Search results: $searchResults")
                }
            } catch (e: Exception) {
                Log.e("Search", "Error during search", e)
            }
        }

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sweat Note", fontSize = 30.sp, fontFamily = dancingscript, fontWeight = FontWeight.SemiBold) },
                modifier = Modifier.clickable {
                    navController.navigate(Routes.Main.route)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    "검색", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("키워드를 입력하세요") },
                    leadingIcon = {
                        IconButton(onClick = { handleSearchClick() }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    },
                    shape = RoundedCornerShape(50), // 타원형 모양
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFFF5F5F5)),
                    keyboardActions = KeyboardActions(
                        onDone = { handleSearchClick() }
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (searchPerformed && searchResults.isEmpty()) {
                    Text("검색된 결과가 없습니다", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn {
                        items(searchResults) { diary ->
                            DiaryItem(diary = diary, navController = navController)
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    )
}
