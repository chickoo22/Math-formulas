package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UnitCategory
import com.example.data.repository.UnitConverterData
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(UnitCategory.ANGLE) }
    val unitsForCategory = remember(selectedCategory) {
        UnitConverterData.CATEGORY_UNITS[selectedCategory] ?: emptyList()
    }

    var fromUnitIndex by remember(selectedCategory) { mutableIntStateOf(0) }
    var toUnitIndex by remember(selectedCategory) { mutableIntStateOf(if (unitsForCategory.size > 1) 1 else 0) }

    var inputValue by remember(selectedCategory) {
        mutableStateOf(
            if (selectedCategory == UnitCategory.NUMBER_BASE) "255"
            else if (selectedCategory == UnitCategory.TEMPERATURE) "100"
            else "1"
        )
    }

    val context = LocalContext.current

    val convertedResult: String = remember(inputValue, fromUnitIndex, toUnitIndex, selectedCategory) {
        if (unitsForCategory.isEmpty()) return@remember "0"
        val fromUnit = unitsForCategory.getOrNull(fromUnitIndex) ?: unitsForCategory[0]
        val toUnit = unitsForCategory.getOrNull(toUnitIndex) ?: unitsForCategory[0]

        if (selectedCategory == UnitCategory.NUMBER_BASE) {
            val res = UnitConverterData.convertNumberBase(inputValue, fromUnit.name, toUnit.name)
            res.getOrElse { "Invalid base input" }
        } else {
            val num = inputValue.toDoubleOrNull()
            if (num != null) {
                val resultNum = UnitConverterData.convert(num, fromUnit, toUnit, selectedCategory)
                if (resultNum % 1.0 == 0.0 && resultNum < 1e12 && resultNum > -1e12) {
                    String.format(Locale.US, "%.0f", resultNum)
                } else {
                    String.format(Locale.US, "%.6g", resultNum)
                }
            } else {
                "Invalid number"
            }
        }
    }

    val tfColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("unit_converter_screen"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Scientific & Engineering Unit Converter",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    letterSpacing = (-0.3).sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "High-precision conversion across angles, physical metrics, temperatures, and computer number bases.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Category Scroll Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UnitCategory.entries.forEach { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = {
                            Text(
                                text = category.displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurface,
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }

        // Converter Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // FROM UNIT
                    Column {
                        Text(
                            text = "FROM",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        var fromExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = fromExpanded,
                            onExpandedChange = { fromExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = unitsForCategory.getOrNull(fromUnitIndex)?.let { "${it.name} (${it.symbol})" } ?: "",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = tfColors,
                                shape = RoundedCornerShape(10.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = fromExpanded,
                                onDismissRequest = { fromExpanded = false }
                            ) {
                                unitsForCategory.forEachIndexed { index, u ->
                                    DropdownMenuItem(
                                        text = { Text("${u.name} (${u.symbol})", color = MaterialTheme.colorScheme.onSurface) },
                                        onClick = {
                                            fromUnitIndex = index
                                            fromExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = inputValue,
                            onValueChange = { inputValue = it },
                            label = { Text("Enter Value", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("converter_input_value"),
                            colors = tfColors,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (selectedCategory == UnitCategory.NUMBER_BASE) KeyboardType.Ascii else KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    // SWAP BUTTON
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FilledTonalIconButton(
                            onClick = {
                                val temp = fromUnitIndex
                                fromUnitIndex = toUnitIndex
                                toUnitIndex = temp
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.SwapVert, contentDescription = "Swap units", tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    // TO UNIT
                    Column {
                        Text(
                            text = "TO (RESULT)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        var toExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = toExpanded,
                            onExpandedChange = { toExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = unitsForCategory.getOrNull(toUnitIndex)?.let { "${it.name} (${it.symbol})" } ?: "",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = tfColors,
                                shape = RoundedCornerShape(10.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = toExpanded,
                                onDismissRequest = { toExpanded = false }
                            ) {
                                unitsForCategory.forEachIndexed { index, u ->
                                    DropdownMenuItem(
                                        text = { Text("${u.name} (${u.symbol})", color = MaterialTheme.colorScheme.onSurface) },
                                        onClick = {
                                            toUnitIndex = index
                                            toExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Result Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = convertedResult,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = unitsForCategory.getOrNull(toUnitIndex)?.symbol ?: "",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Converted Result", convertedResult))
                                        Toast.makeText(context, "Result copied", Toast.LENGTH_SHORT).show()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy result",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
