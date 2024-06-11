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
import com.example.sweatnote.roomDB.ExerciseType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun BarChartView(modifier: Modifier = Modifier, exerciseCounts: Map<ExerciseType, Int>) {
    val context = LocalContext.current
    Box(modifier = modifier.height(300.dp)) {
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
                            axisMinimum = 0f
                            labelCount = exerciseCounts.size
                            valueFormatter = object : ValueFormatter() {
                                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                                    return ExerciseType.entries.getOrNull(value.toInt() - 1)?.name ?: ""
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
                    val entries = exerciseCounts.map { BarEntry(it.key.ordinal.toFloat() + 1, it.value.toFloat()) }
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
                    barData.barWidth = 0.7f
                    barChart.data = barData
                    barChart.invalidate()
                }
        )
    }
}
