package com.sergeyfierce.testapp.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergeyfierce.testapp.model.Note
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun NotesListScreen(onBack: () -> Unit, onAdd: () -> Unit) {
    val search = remember { mutableStateOf("") }
    val notes = remember { listOf(Note(1, "Пример заметки", System.currentTimeMillis())) }
    val filtered = notes.filter { it.text.contains(search.value, ignoreCase = true) }
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Заметки") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } },
                actions = { IconButton(onClick = { /* sort */ }) { Icon(Icons.Default.Sort, null) } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, null) }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = search.value,
                onValueChange = { search.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Поиск по тексту") }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filtered) { note ->
                    NoteCard(note)
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = CardDefaults.outlinedCardBorder(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.text, maxLines = 3)
            Text(text = java.text.SimpleDateFormat("dd.MM.yyyy").format(note.date), style = MaterialTheme.typography.bodySmall)
        }
    }
}
