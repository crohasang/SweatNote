package com.example.sweatnote.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxWithText(text: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small, // 테두리를 둥글게 만듭니다.
        color = if (isChecked) Color(0xFF000080) else Color.White, // 체크박스가 체크되면 배경색을 진한 파란색으로, 아니면 하얀색으로 설정합니다.
        contentColor = if (isChecked) Color.White else Color.Black, // 체크박스가 체크되면 글자색을 하얀색으로, 아니면 검은색으로 설정합니다.
        border = if (isChecked) null else BorderStroke(width = 1.dp, color = Color.Gray), // 체크박스가 체크되면 테두리를 없애고, 아니면 회색 테두리를 유지합니다.
        modifier = Modifier.clickable { onCheckedChange(!isChecked) }
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Text(text = text)
        }
    }
}
