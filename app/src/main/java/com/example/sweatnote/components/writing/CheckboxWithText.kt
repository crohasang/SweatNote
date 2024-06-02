package com.example.sweatnote.components.writing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.sweatnote.ui.theme.pretendardFamily

@Composable
fun CheckboxWithText(text: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp), // 테두리를 더욱 둥글게 만듭니다.
        color = if (isChecked) Black else White, // 체크박스가 체크되면 배경색을 진한 회색으로, 아니면 연한 회색으로 설정합니다.
        contentColor = if (isChecked) White else Black, // 체크박스가 체크되면 글자색을 하얀색으로, 아니면 검은색으로 설정합니다.
//        border = if (isChecked) null else BorderStroke(width = 1.dp, color = Color.Gray), // 체크박스가 체크되면 테두리를 없애고, 아니면 회색 테두리를 유지합니다.
        modifier = Modifier
            .clickable { onCheckedChange(!isChecked) }
            .width(100.dp)
            .height(35.dp)
    ) {
        Box(modifier = Modifier
            .padding(8.dp),
            contentAlignment = Alignment.Center
        )

        {
            Text(text = text, fontFamily = pretendardFamily)
        }
    }
}
