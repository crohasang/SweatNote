import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun BarChartView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier = modifier.height(300.dp)) { // 차트 높이 조정
        AndroidView(
            factory = {
                BarChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    description.isEnabled = false
                    setDrawGridBackground(false)

                    // X축 설정
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false) // X축의 그리드라인을 표시하지 않음
                        textColor = Color.BLACK
                        textSize = 12f
                        typeface = Typeface.DEFAULT_BOLD
                        granularity = 1f
                        axisMinimum = 0f
                        labelCount = 5 // X축에 표시할 라벨 수
                        valueFormatter = object : ValueFormatter() {
                            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                                return when (value.toInt()) {
                                    1 -> "가슴"
                                    2 -> "등"
                                    3 -> "어깨"
                                    4 -> "하체"
                                    5 -> "유산소"
                                    else -> ""
                                }
                            }
                        }
                    }

                    // Y축 설정 (왼쪽)
                    axisLeft.apply {
                        setDrawGridLines(false) // Y축의 그리드라인을 표시하지 않음
                        textColor = Color.BLACK
                        textSize = 12f
                        typeface = Typeface.DEFAULT_BOLD
                    }

                    // Y축 라벨
                    axisLeft.axisMinimum = 0f
                    axisLeft.granularity = 2f // 그리드라인 간격 설정
                    axisLeft.valueFormatter = object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            return "${value.toInt()} 회"
                        }
                    }

                    // Y축 설정 (오른쪽)
                    axisRight.apply {
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        setDrawLabels(false)
                    }

                    // 범례 설정
                    legend.isEnabled = true
                }
            },
            modifier = modifier,
            update = { barChart ->
                val entries = listOf(
                    BarEntry(1f, 10f),
                    BarEntry(2f, 5f),
                    BarEntry(3f, 6f),
                    BarEntry(4f, 7f),
                    BarEntry(5f, 3f)
                )
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
                barData.barWidth = 0.7f // 막대 너비 설정
                barChart.data = barData
                barChart.invalidate()
            }
        )
    }
}
