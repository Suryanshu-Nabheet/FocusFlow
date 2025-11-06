package com.android.focusflow.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.focusflow.data.model.Task
import com.android.focusflow.ui.theme.*
import com.android.focusflow.viewmodel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate = remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    
    // Simplified approach - use allTasks and filter by date
    val allTasks by viewModel.getAllTasks().collectAsState()
    val tasksForDate = remember(allTasks, selectedDate.value) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = selectedDate.value
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endTime = calendar.timeInMillis - 1
        
        allTasks.filter { task ->
            task.dueDate?.let { it >= startTime && it < endTime } ?: false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar", color = PureWhite) },
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
        ) {
            // Calendar header
            CalendarHeader(
                selectedDate = selectedDate.value,
                onPreviousMonth = {
                    val cal = Calendar.getInstance().apply { timeInMillis = selectedDate.value }
                    cal.add(Calendar.MONTH, -1)
                    selectedDate.value = cal.timeInMillis
                },
                onNextMonth = {
                    val cal = Calendar.getInstance().apply { timeInMillis = selectedDate.value }
                    cal.add(Calendar.MONTH, 1)
                    selectedDate.value = cal.timeInMillis
                }
            )

            // Calendar grid
            CalendarGrid(
                selectedDate = selectedDate.value,
                onDateSelected = { selectedDate.value = it },
                tasks = allTasks
            )

            Divider(
                color = MediumGray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Tasks for selected date
            Text(
                text = "Tasks for ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(selectedDate.value))}",
                style = MaterialTheme.typography.titleMedium,
                color = ElectricBlue,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (tasksForDate.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks for this date",
                        color = MediumGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasksForDate, key = { it.id }) { task ->
                        TaskCard(task = task)
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarHeader(
    selectedDate: Long,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val monthText = monthFormat.format(Date(selectedDate))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onPreviousMonth) {
            Text("←", color = ElectricBlue, style = MaterialTheme.typography.titleLarge)
        }
        Text(
            text = monthText,
            style = MaterialTheme.typography.titleLarge,
            color = PureWhite,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onNextMonth) {
            Text("→", color = ElectricBlue, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun CalendarGrid(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    tasks: List<Task>
) {
    val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)

    val firstDayOfMonth = Calendar.getInstance().apply {
        set(year, month, 1)
    }
    val firstDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1 // 0 = Sunday
    val daysInMonth = firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Day names header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayNames.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall,
                    color = MediumGray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar days
        var currentDay = 1
        var currentWeek = 0
        while (currentDay <= daysInMonth || currentWeek == 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    if (currentWeek == 0 && dayOfWeek < firstDayOfWeek) {
                        // Empty cells before first day of month
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    } else if (currentDay <= daysInMonth) {
                        val dayDate = Calendar.getInstance().apply {
                            set(year, month, currentDay)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.timeInMillis

                        val isSelected = Calendar.getInstance().apply {
                            timeInMillis = selectedDate
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.timeInMillis == dayDate

                        val hasTasks = tasks.any { task ->
                            task.dueDate?.let { dueDate ->
                                Calendar.getInstance().apply {
                                    timeInMillis = dueDate
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.timeInMillis == dayDate
                            } ?: false
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) ElectricBlue
                                    else if (hasTasks) ElectricBlue.copy(alpha = 0.3f)
                                    else Color.Transparent
                                )
                                .clickable { onDateSelected(dayDate) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentDay.toString(),
                                color = if (isSelected) PureWhite else PureWhite,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        currentDay++
                    } else {
                        // Empty cells after last day of month
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
            currentWeek++
        }
    }
}

@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                color = PureWhite,
                fontWeight = FontWeight.Bold
            )
            if (task.description.isNotBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightGray
                )
            }
        }
    }
}

