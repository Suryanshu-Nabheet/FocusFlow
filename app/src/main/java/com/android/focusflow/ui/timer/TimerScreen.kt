package com.android.focusflow.ui.timer

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.focusflow.ui.theme.*
import com.android.focusflow.viewmodel.TimerState
import com.android.focusflow.viewmodel.TimerMode
import com.android.focusflow.viewmodel.TimerViewModel
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel()
) {
    val timerState by viewModel.timerState.collectAsState()
    val timerMode by viewModel.timerMode.collectAsState()
    val remainingTime by viewModel.remainingTime.collectAsState()
    val progress by viewModel.progress.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus Timer", color = PureWhite) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkGray
                )
            )
        },
        containerColor = DarkBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mode indicator
            Text(
                text = if (timerMode == TimerMode.WORK) "Work Session" else "Break Time",
                style = MaterialTheme.typography.titleLarge,
                color = ElectricBlue,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Circular progress timer
            CircularTimerProgress(
                progress = progress,
                remainingTime = remainingTime,
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Time display
            val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displayMedium,
                color = PureWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Control buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset button
                IconButton(
                    onClick = { viewModel.resetTimer() },
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MediumGray)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = PureWhite,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Start/Pause button
                Button(
                    onClick = {
                        when (timerState) {
                            TimerState.IDLE, TimerState.PAUSED -> viewModel.startTimer()
                            TimerState.RUNNING -> viewModel.pauseTimer()
                            TimerState.COMPLETED -> {
                                viewModel.resetTimer()
                                viewModel.startTimer()
                            }
                        }
                    },
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue
                    )
                ) {
                    Icon(
                        if (timerState == TimerState.RUNNING) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (timerState == TimerState.RUNNING) "Pause" else "Start",
                        tint = PureWhite,
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Placeholder for symmetry
                Spacer(modifier = Modifier.size(64.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Status text
            Text(
                text = when (timerState) {
                    TimerState.IDLE -> "Ready to focus"
                    TimerState.RUNNING -> "Stay focused!"
                    TimerState.PAUSED -> "Paused"
                    TimerState.COMPLETED -> "Session complete!"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = if (timerState == TimerState.COMPLETED) ElectricBlue else MediumGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CircularTimerProgress(
    progress: Float,
    remainingTime: Long,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        ),
        label = "Timer Progress"
    )

    val sweepAngle = 360f * animatedProgress

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            val size = size.minDimension
            val radius = (size - strokeWidth) / 2f

            // Background circle
            drawCircle(
                color = MediumGray,
                radius = radius,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress circle
            drawArc(
                color = ElectricBlue,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(
                    (size - radius * 2) / 2f,
                    (size - radius * 2) / 2f
                ),
                size = Size(radius * 2, radius * 2)
            )
        }

        // Glow effect for active timer
        if (progress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.95f)
                    .clip(CircleShape)
                    .background(ElectricBlue.copy(alpha = 0.1f))
            )
        }
    }
}

