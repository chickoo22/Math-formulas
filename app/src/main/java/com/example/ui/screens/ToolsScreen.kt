package com.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.repository.FormulaWithBookmark

/** Groups reference utilities so they are discoverable without crowding the main navigation. */
@Composable
fun ToolsScreen(
    formulasWithBookmarks: List<FormulaWithBookmark>,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTool by remember { mutableIntStateOf(0) }
    val tools = listOf("Unit converter", "Roots & tables")

    Column(modifier = modifier.fillMaxSize()) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            tools.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = selectedTool == index,
                    onClick = { selectedTool = index },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = tools.size)
                ) {
                    Text(
                        label,
                        fontWeight = if (selectedTool == index) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedTool == index) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        when (selectedTool) {
            0 -> UnitConverterScreen()
            else -> RootsSectionScreen(formulasWithBookmarks, onToggleBookmark)
        }
    }
}
