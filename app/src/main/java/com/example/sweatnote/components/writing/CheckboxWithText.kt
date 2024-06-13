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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sweatnote.ui.theme.pretendardFamily

@Composable
fun CheckboxWithText(text: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isChecked) DarkGray else Color(0xFFF5F5F5),
        contentColor = if (isChecked) White else Black,
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
            Text(text = text, fontFamily = pretendardFamily, fontWeight = FontWeight.SemiBold)
        }
    }
}
