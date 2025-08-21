package com.sergeyfierce.testapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sergeyfierce.testapp.model.Category

@Composable
fun HomeScreen(
    onCategory: (Category) -> Unit,
    onAddContact: () -> Unit,
    openDrawer: () -> Unit
) {
    val categories = listOf(
        Category.PARTNER to 0,
        Category.CLIENT to 0,
        Category.POTENTIAL to 0
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Touch NoteBook") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Group, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddContact) {
                Text("+ Добавить контакт")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.forEach { (cat, count) ->
                CategoryCard(category = cat, count = count, onClick = { onCategory(cat) })
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, count: Int, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = category.title, style = MaterialTheme.typography.titleMedium)
                Text(text = countText(count), style = MaterialTheme.typography.bodySmall)
            }
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

private fun countText(count: Int): String {
    val suffix = when {
        count % 10 == 1 && count % 100 != 11 -> "контакт"
        count % 10 in 2..4 && count % 100 !in 12..14 -> "контакта"
        else -> "контактов"
    }
    return "$count $suffix"
}
