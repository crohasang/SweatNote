
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun DiaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    lineCount: Int = 40
) {
    val lineHeight = 30
    val textFieldHeight = lineHeight * lineCount
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier.height(textFieldHeight.dp)) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight.dp)
                .background(Color.White),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                background = Color.Transparent
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            singleLine = false,
            maxLines = lineCount,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // '완료' 액션 추가
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }) // '완료'를 누르면 키보드 숭김
        )

        if (value.isEmpty()) {
            Text(
                text = "일기를 작성하세요",
                modifier = Modifier.align(Alignment.TopStart),
                color = Color.LightGray
            )
        }
    }
}
