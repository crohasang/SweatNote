package com.example.sweatnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sweatnote.navigation.NavGraph
import com.example.sweatnote.ui.theme.SweatNoteTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SweatNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)

                ) {
                    val navController = rememberNavController()
                    var showSplash by remember { mutableStateOf(true) }

                    // 스플래시 화면이 표시되는 동안 3초 동안 대기
                    LaunchedEffect(Unit) {
                        delay(1500L)
                        showSplash = false
                    }

                    // 스플래시 화면이 표시되는 동안은 SplashScreen을 표시하고,
                    // 그렇지 않으면 NavGraph를 표시
                    if (showSplash) {
                        SplashScreen()
                    } else {
                        NavGraph(navController)
                    }
                }

            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SweatNoteTheme {
        Greeting("Android")
    }
}


@Composable
fun SplashScreen() {
    Image(
        painter = painterResource(id = R.drawable.splash),
        contentDescription = "Splash Screen",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}
