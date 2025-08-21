package com.sergeyfierce.testapp.ui.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.sergeyfierce.testapp.model.Category
import com.sergeyfierce.testapp.model.Contact
import com.sergeyfierce.testapp.model.Status

@Composable
fun ContactsListScreen(
    category: String,
    onAdd: () -> Unit,
    onBack: () -> Unit
) {
    val search = remember { mutableStateOf("") }
    val sample = remember {
        listOf(
            Contact(1, "Иван Иванов", Status.ACTIVE, listOf("Новый"), Category.PARTNER)
        )
    }
    val filtered = sample.filter { it.fullName.contains(search.value, ignoreCase = true) }
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(category) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    IconButton(onClick = { /* open sort */ }) { Icon(Icons.Default.Sort, null) }
                    IconButton(onClick = { /* open filter */ }) { Icon(Icons.Default.FilterList, null) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = search.value,
                onValueChange = { search.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Поиск по ФИО") }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filtered) { contact ->
                    ContactCard(contact = contact)
                }
            }
        }
    }
}

@Composable
fun ContactCard(contact: Contact) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = CardDefaults.outlinedCardBorder(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(contact.fullName, style = MaterialTheme.typography.titleMedium)
            AssistChip(onClick = {}, label = { Text(contact.status.title) })
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                contact.tags.forEach { tag ->
                    FilterChip(selected = false, onClick = {}, label = { Text(tag) })
                }
            }
        }
    }
}
