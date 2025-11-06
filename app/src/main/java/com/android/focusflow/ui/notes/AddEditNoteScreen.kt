package com.android.focusflow.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.focusflow.data.model.Note
import com.android.focusflow.ui.theme.*
import com.android.focusflow.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Long? = null,
    viewModel: NotesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(NoteColors[4].value.toLong()) } // Default blue

    val notes by viewModel.notes.collectAsState()
    
    LaunchedEffect(noteId) {
        if (noteId != null) {
            notes.firstOrNull { it.id == noteId }?.let { note ->
                title = note.title
                content = note.content
                selectedColor = note.colorTag.toLong()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "New Note" else "Edit Note", color = PureWhite) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PureWhite)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val note = if (noteId == null) {
                                    Note(
                                        title = title,
                                        content = content,
                                        colorTag = selectedColor.toInt()
                                    )
                                } else {
                                    Note(
                                        id = noteId,
                                        title = title,
                                        content = content,
                                        colorTag = selectedColor.toInt()
                                    )
                                }
                                if (noteId == null) {
                                    viewModel.insertNote(note)
                                } else {
                                    viewModel.updateNote(note)
                                }
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save", tint = ElectricBlue)
                    }
                },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Color picker
            Text(
                text = "Color Tag",
                style = MaterialTheme.typography.titleSmall,
                color = PureWhite
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NoteColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { selectedColor = color.value.toLong() }
                            .then(
                                if (selectedColor == color.value.toLong()) {
                                    Modifier.padding(4.dp)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        if (selectedColor == color.value.toLong()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(ElectricBlue.copy(alpha = 0.3f))
                            )
                        }
                    }
                }
            }

            // Title field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = LightGray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite,
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedIndicatorColor = ElectricBlue,
                    unfocusedIndicatorColor = MediumGray,
                    focusedLabelColor = ElectricBlue,
                    unfocusedLabelColor = LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Content field
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content", color = LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite,
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedIndicatorColor = ElectricBlue,
                    unfocusedIndicatorColor = MediumGray,
                    focusedLabelColor = ElectricBlue,
                    unfocusedLabelColor = LightGray
                ),
                shape = RoundedCornerShape(12.dp),
                minLines = 10,
                maxLines = 20
            )
        }
    }
}

