package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.weight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    formulaCount: Int,
    savedCount: Int,
    noteCount: Int,
    onOpenLibrary: () -> Unit,
    onOpenQuiz: () -> Unit,
    onOpenSolve: () -> Unit,
    onOpenTools: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("What do you want to study?", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Pick a task to get started. Everything works offline.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        HomeActionCard("Find a formula", "Search $formulaCount maths and physics formulas", Icons.Default.Functions, onOpenLibrary)
        HomeActionCard("Test yourself", "Practice with active-recall flashcards", Icons.Default.Psychology, onOpenQuiz)
        HomeActionCard("Solve a problem", "Worked examples and step-by-step solvers", Icons.Default.AutoFixHigh, onOpenSolve)
        HomeActionCard("Use a quick tool", "Convert units or check roots and tables", Icons.Default.CompareArrows, onOpenTools)
        Text("Your study space", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HomeStat("$savedCount", "Saved", Modifier.weight(1f))
            HomeStat("$noteCount", "Notes", Modifier.weight(1f))
        }
    }
}

@Composable
private fun HomeActionCard(title: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun HomeStat(value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}
