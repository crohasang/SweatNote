package com.example.sweatnote.graphViews

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sweatnote.R
import com.example.sweatnote.roomDB.EmotionType
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

@Composable
fun PieChartView(modifier: Modifier = Modifier, emotionCounts: Map<EmotionType, Int>) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(300.dp)) {
            if (emotionCounts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 정렬 추가
                    verticalArrangement = Arrangement.Center // 수직 정렬 추가
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.feel_free),
                        contentDescription = "running image",
                        modifier = Modifier
                            .size(200.dp) // 원하는 사이즈로 조절
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // 이미지와 텍스트 사이에 공간 추가
                    BasicText(
                        text = "수행한 운동이 없습니다. 운동을 시작해보세요!",
                        style = TextStyle(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
                else{
                    AndroidView(
                        factory = {
                            PieChart(context).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                setUsePercentValues(true)
                                description.isEnabled = false
                                isDrawHoleEnabled = false
                                setEntryLabelColor(Color.BLACK)
                                setEntryLabelTextSize(12f)
                                legend.isEnabled = false
                            }
                        },
                        update = { pieChart ->
                            val entries =
                                emotionCounts.map { PieEntry(it.value.toFloat(), it.key.name) }
                            val dataSet = PieDataSet(entries, "운동 감정").apply {
                                colors = listOf(
                                    Color.rgb(244, 67, 54),
                                    Color.rgb(33, 150, 243),
                                    Color.rgb(76, 175, 80),
                                    Color.rgb(255, 235, 59),
                                    Color.rgb(156, 39, 176)
                                )
                                valueTextColor = Color.BLACK
                                valueTextSize = 12f
                                valueFormatter = PercentFormatter(pieChart)
                            }
                            val pieData = PieData(dataSet)
                            pieChart.data = pieData
                            pieChart.invalidate()
                        }
                    )
                }

            }
        }
    }
