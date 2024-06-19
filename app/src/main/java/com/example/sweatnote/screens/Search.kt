package com.example.sweatnote.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sweatnote.components.BottomBar
import com.example.sweatnote.components.TopBar
import com.example.sweatnote.components.search.DiaryItem
import com.example.sweatnote.example.DiaryViewModel
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
    val isTextBlank = remember { mutableStateOf(false) }
    val shake by remember {mutableStateOf(Animatable(0f))}
    val keyboardController = LocalSoftwareKeyboardController.current


    fun handleSearchClick() {
        // 검색 텍스트가 비어있는지 확인
        if (text.isBlank()) {
            isTextBlank.value = true

            scope.launch {
                shake.animateTo(
                    targetValue = 10f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
                shake.snapTo(0f)
            }
        } else {
            isTextBlank.value = false
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
    }

    Scaffold(
        topBar = { TopBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .padding(bottom = 80.dp),
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
                        focusedBorderColor = if(isTextBlank.value) Color.Red else Color.Black,
                        unfocusedBorderColor = if(isTextBlank.value) Color.Red else Color.Black
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.Transparent)
                        .offset(x = shake.value.dp),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            handleSearchClick()
                            keyboardController?.hide()
                        }
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
