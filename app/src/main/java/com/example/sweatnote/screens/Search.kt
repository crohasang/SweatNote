
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavHostController) {

    fun handleSearchClick() {

    // 데이터베이스에서 키워드가 포함된 일기들을 검색하는 코드
    // 밑은 예시
//            val diaryDatabase = DiaryDatabase.getInstance(context)
//            diaryEntries = diaryDatabase.diaryDao().search(text)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(50.dp))

        Text("검색", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            keyboardActions = KeyboardActions(onDone = { handleSearchClick() }),
            placeholder = { Text("키워드를 입력하세요") },
            leadingIcon = { IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray) } },
            shape = RoundedCornerShape(50), // 타원형 모양
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color(0xFFF5F5F5))
        )

        // 키워드가 포함된 일기 출력 코드(임시)
        // 해당 일기 클릭 시 일기 화면으로 넘어가야함
//        LazyColumn {
//            items(diaryEntries) { diaryEntry ->
//                Text(
//                    diaryEntry.entry, // 각 일기 항목의 내용을 표시합니다.
//                    modifier = Modifier.clickable {
//                        // 일기 항목을 클릭하면 해당 일기로 이동합니다.
//                        navController.navigate("diary/${diaryEntry.id}")
//                    }
//                )
//            }
//        }
    }
}
