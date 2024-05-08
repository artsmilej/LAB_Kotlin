package com.example.laboratorka_5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockScreen()
        }
    }
}

@Composable
fun ClockScreen() {
    val currentTime = remember { mutableStateOf(Calendar.getInstance()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2

            val secondsRotation = currentTime.value.get(Calendar.SECOND) * 6f
            val minutesRotation = currentTime.value.get(Calendar.MINUTE) * 6f
            val hoursRotation =
                (currentTime.value.get(Calendar.HOUR) * 30 + currentTime.value.get(Calendar.MINUTE) / 2).toFloat()

            drawCircle(
                color = Color.White,
                center = Offset(centerX, centerY),
                radius = radius,
                style = Stroke(width = 2.dp.toPx())
            )

            repeat(60) {
                val angle = Math.toRadians(it.toDouble() * 6)
                val innerRadius = if (it % 5 == 0) radius - 16.dp.toPx() else radius - 8.dp.toPx()
                val outerRadius = radius

                val startX = centerX + (innerRadius * Math.cos(angle)).toFloat()
                val startY = centerY + (innerRadius * Math.sin(angle)).toFloat()
                val stopX = centerX + (outerRadius * Math.cos(angle)).toFloat()
                val stopY = centerY + (outerRadius * Math.sin(angle)).toFloat()

                drawLine(
                    color = Color.Black,
                    start = Offset(startX, startY),
                    end = Offset(stopX, stopY),
                    strokeWidth = 2.dp.toPx()
                )
            }

            rotate(degrees = hoursRotation, pivot = Offset(centerX, centerY)) {
                drawLine(
                    color = Color.Red,
                    start = Offset(centerX, centerY),
                    end = Offset(centerX, centerY - radius * 0.5f),
                    strokeWidth = 4.dp.toPx()
                )
            }

            rotate(degrees = minutesRotation, pivot = Offset(centerX, centerY)) {
                drawLine(
                    color = Color.Black,
                    start = Offset(centerX, centerY),
                    end = Offset(centerX, centerY - radius * 0.7f),
                    strokeWidth = 3.dp.toPx()
                )
            }

            rotate(degrees = secondsRotation, pivot = Offset(centerX, centerY)) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(centerX, centerY),
                    end = Offset(centerX, centerY - radius * 0.9f),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = Calendar.getInstance()
            delay(1000)
        }
    }
}

@Preview
@Composable
fun ClockPreview() {
    ClockScreen()
}