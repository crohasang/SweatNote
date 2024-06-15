import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sweatnote.R
import com.example.sweatnote.roomDB.ExerciseType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun BarChartView(modifier: Modifier = Modifier, exerciseCounts: Map<ExerciseType, Int>) {
    val context = LocalContext.current
    Box(modifier = modifier.height(300.dp)) {
        if (exerciseCounts.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 정렬 추가
                verticalArrangement = Arrangement.Center // 수직 정렬 추가
            ) {
                Image(
                    painter = painterResource(id = R.drawable.running3),
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
        } else {
            AndroidView(
                factory = {
                    BarChart(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        description.isEnabled = false
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            textColor = Color.BLACK
                            textSize = 12f
                            typeface = Typeface.DEFAULT_BOLD
                            granularity = 1f
                            axisMinimum = -0.5f // X축 최소값을 -0.5로 설정하여 모든 레이블이 보이도록 설정
                            axisMaximum = exerciseCounts.size - 0.5f // X축 최대값을 데이터 크기 - 0.5로 설정
                            labelCount = exerciseCounts.size
                            valueFormatter = object : ValueFormatter() {
                                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                                    return exerciseCounts.entries.elementAtOrNull(value.toInt())?.key?.name ?: ""
                                }
                            }
                        }

                        axisLeft.apply {
                            setDrawGridLines(false)
                            textColor = Color.BLACK
                            textSize = 12f
                            typeface = Typeface.DEFAULT_BOLD
                            axisMinimum = 0f
                            granularity = 1f
                        }

                        axisRight.apply {
                            setDrawGridLines(false)
                            setDrawAxisLine(false)
                            setDrawLabels(false)
                        }

                        legend.isEnabled = true
                    }
                },
                modifier = modifier,
                update = { barChart ->
                    val entries = exerciseCounts.entries.mapIndexed { index, entry ->
                        BarEntry(
                            index.toFloat(),
                            entry.value.toFloat()
                        )
                    }
                    val dataSet = BarDataSet(entries, "운동 횟수").apply {
                        color = Color.GRAY
                        valueTextColor = Color.BLACK
                        valueTextSize = 12f
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return value.toInt().toString()
                            }
                        }
                    }
                    val barData = BarData(dataSet)
                    barData.barWidth = 0.5f
                    barChart.data = barData
                    barChart.invalidate()
                }
            )
        }
    }
}
