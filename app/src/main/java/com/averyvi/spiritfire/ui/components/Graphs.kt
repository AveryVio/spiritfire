package com.averyvi.spiritfire.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.averyvi.spiritfire.data.definitions.ui.ChartData
import com.averyvi.spiritfire.data.transformations.degreeToAngle
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChartWithLabels(
    data: List<ChartData>,
    size: Float,
    strokeWith: Dp,
    strokeSpaces: Float,
) {
    val dataSum = data.sumOf { it.data.toInt() }
    var dataAsAngles = listOf<ChartData>()
    data.forEach { dataAsAngles = dataAsAngles.plus( ChartData(it.color, (it.data / dataSum) * 360 ) ) }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size((size).dp)
                .aspectRatio(1f)
        ) {
            val width = size * 3
            val radius = width / 2f
            val strokeWidth = strokeWith.toPx()

            var startAngle = -90f

            for (index in dataAsAngles.indices) {

                val chartData = dataAsAngles[index]
                val sweepAngle = chartData.data - strokeSpaces
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle


                drawArc(
                    color = chartData.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(x = strokeWidth / 2, y = strokeWidth / 2),
                    size = Size(width = width - strokeWidth, height = width - strokeWidth),
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                        )
                )


                /*val rectWidth = 20.dp.toPx()
                drawRect(
                    color = Color.Red,
                    size = Size(rectWidth, rectWidth),
                    topLeft = Offset(
                        -rectWidth / 2 + center.x + (radius + strokeWidth) * cos(
                            angleInRadians
                        ),
                        -rectWidth / 2 + center.y + (radius + strokeWidth) * sin(
                            angleInRadians
                        )
                    )
                )*/

                startAngle += sweepAngle + strokeSpaces
            }
        }
    }
}