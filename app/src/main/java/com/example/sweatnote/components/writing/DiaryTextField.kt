import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun DiaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    lineCount: Int = 40 // 라인 수를 늘렸습니다.
) {
    val lineHeight = 30 // 줄 간격을 늘렸습니다.
    val textFieldHeight = lineHeight * lineCount

    Box(modifier.height(textFieldHeight.dp)) { // 높이를 크게 설정했습니다.
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight.dp)
                .background(Color.LightGray), // 밝은 배경색으로 변경했습니다.
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                background = Color.Transparent
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = false,
            maxLines = lineCount
        )
    }
}
