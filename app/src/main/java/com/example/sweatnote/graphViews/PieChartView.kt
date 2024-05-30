package com.example.sweatnote.graphViews

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

@Composable
fun PieChartView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(300.dp)) {
            AndroidView(
                factory = {
                    PieChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setUsePercentValues(true) // 백분율 사용
                        description.isEnabled = false
                        isDrawHoleEnabled = false
                        setEntryLabelColor(Color.BLACK)
                        setEntryLabelTextSize(12f)
                        legend.isEnabled = false // 범례 비활성화
                    }
                },
                update = { pieChart ->
                    val entries = listOf(
                        PieEntry(20f, "감정 1단계"),
                        PieEntry(15f, "감정 2단계"),
                        PieEntry(25f, "감정 3단계"),
                        PieEntry(17.5f, "감정 4단계"),
                        PieEntry(22.5f, "감정 5단계")
                    )
                    val dataSet = PieDataSet(entries, "운동 감정").apply {
                        colors = listOf(
                            Color.rgb(244, 67, 54),   // 빨강
                            Color.rgb(33, 150, 243),  // 파랑
                            Color.rgb(76, 175, 80),   // 초록
                            Color.rgb(255, 235, 59),  // 노랑
                            Color.rgb(156, 39, 176)   // 보라
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
