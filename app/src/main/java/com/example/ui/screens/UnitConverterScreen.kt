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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ConversionUnit
import com.example.data.model.UnitCategory
import com.example.data.model.UnitConverterData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(UnitCategory.ANGLE) }
    val units = remember(selectedCategory) { UnitConverterData.getUnitsForCategory(selectedCategory) }

    var fromUnit by remember(selectedCategory) { mutableStateOf(units.first()) }
    var toUnit by remember(selectedCategory) { mutableStateOf(if (units.size > 1) units[1] else units.first()) }
    var inputValue by remember(selectedCategory) { mutableStateOf(if (selectedCategory == UnitCategory.ANGLE) "180" else "1") }

    var fromDropdownExpanded by remember { mutableStateOf(false) }
    var toDropdownExpanded by remember { mutableStateOf(false) }

    val convertedValue = remember(inputValue, fromUnit, toUnit, selectedCategory) {
        UnitConverterData.convert(inputValue, fromUnit, toUnit, selectedCategory)
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("unit_converter_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Category selection horizontal scroll
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UnitConverterData.CATEGORIES.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            val newUnits = UnitConverterData.getUnitsForCategory(category)
                            fromUnit = newUnits.first()
                            toUnit = if (newUnits.size > 1) newUnits[1] else newUnits.first()
                        },
                        label = { Text(category.displayName, fontSize = 12.sp) },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("converter_cat_${category.name.lowercase()}")
                    )
                }
            }
        }

        // Conversion Card From
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CONVERT FROM",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Unit selector dropdown
                    ExposedDropdownMenuBox(
                        expanded = fromDropdownExpanded,
                        onExpandedChange = { fromDropdownExpanded = !fromDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = "${fromUnit.name} (${fromUnit.symbol})",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("from_unit_dropdown"),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = fromDropdownExpanded,
                            onDismissRequest = { fromDropdownExpanded = false }
                        ) {
                            units.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text("${unit.name} (${unit.symbol})") },
                                    onClick = {
                                        fromUnit = unit
                                        fromDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        label = { Text("Magnitude") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("converter_input_value"),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (selectedCategory == UnitCategory.NUMBER_BASE && fromUnit.id == "hex") KeyboardType.Ascii else KeyboardType.Number
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        // Swap button centered
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                FilledTonalIconButton(
                    onClick = {
                        val temp = fromUnit
                        fromUnit = toUnit
                        toUnit = temp
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("converter_swap_btn")
                ) {
                    Icon(Icons.Default.SwapVert, contentDescription = "Swap units")
                }
            }
        }

        // Conversion Card To (Result)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CONVERT TO (EQUIVALENT)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Unit selector dropdown
                    ExposedDropdownMenuBox(
                        expanded = toDropdownExpanded,
                        onExpandedChange = { toDropdownExpanded = !toDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = "${toUnit.name} (${toUnit.symbol})",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("to_unit_dropdown"),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = toDropdownExpanded,
                            onDismissRequest = { toDropdownExpanded = false }
                        ) {
                            units.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text("${unit.name} (${unit.symbol})") },
                                    onClick = {
                                        toUnit = unit
                                        toDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Result display
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (convertedValue.isEmpty()) "0" else convertedValue,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.testTag("converter_result_text")
                                )
                                Text(
                                    text = "${toUnit.name} (${toUnit.symbol})",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (convertedValue.isNotEmpty() && convertedValue != "Invalid") {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Converted Unit", convertedValue))
                                        Toast.makeText(context, "Copied: $convertedValue", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.size(48.dp)
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

        // Conversion formula reference / insight
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Conversion Reference & Constants",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    val insight = when (selectedCategory) {
                        UnitCategory.ANGLE -> "1 Radian = 180° / π ≈ 57.2957795°  •  1° = π / 180 rad ≈ 0.017453 rad  •  1 Turn = 2π rad = 360°"
                        UnitCategory.TEMPERATURE -> "°F = (°C × 9/5) + 32  •  K = °C + 273.15  •  Absolute zero = 0 K = -273.15 °C"
                        UnitCategory.NUMBER_BASE -> "Radix conversions: Base 2 (Binary), Base 8 (Octal), Base 10 (Decimal), Base 16 (Hexadecimal)"
                        UnitCategory.LENGTH -> "1 meter = 3.28084 feet = 39.3701 inches  •  1 statute mile = 1.609344 km = 5,280 ft"
                        UnitCategory.AREA -> "1 Hectare = 10,000 m²  •  1 Acre ≈ 4,046.856 m² = 43,560 ft²  •  1 km² = 100 ha"
                        UnitCategory.VOLUME -> "1 Liter = 1,000 mL = 0.001 m³  •  1 US Gallon ≈ 3.78541 Liters  •  1 Imperial Gal ≈ 4.54609 L"
                        UnitCategory.MASS -> "1 kg = 1,000 g ≈ 2.20462 lbs  •  1 Metric Ton = 1,000 kg  •  1 Troy Ounce ≈ 31.1035 g"
                        UnitCategory.SPEED -> "1 m/s = 3.6 km/h ≈ 2.23694 mph  •  1 knot ≈ 1.852 km/h  •  c ≈ 299,792,458 m/s"
                        UnitCategory.TIME -> "1 Day = 24 h = 1,440 min = 86,400 s  •  1 Julian Year = 365.25 days = 31,557,600 s"
                    }
                    Text(
                        text = insight,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, lineHeight = 18.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
