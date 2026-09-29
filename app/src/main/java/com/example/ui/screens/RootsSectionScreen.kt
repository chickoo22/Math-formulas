package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FormulaCategory
import com.example.data.model.NumberPowerRoot
import com.example.data.repository.FormulaWithBookmark
import com.example.data.repository.MathDataProvider
import com.example.math.EquationSolvers
import com.example.ui.components.MathFormulaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootsSectionScreen(
    formulasWithBookmarks: List<FormulaWithBookmark>,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Table 1–50", "Radical Simplifier", "Identities & Rules")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("roots_section_screen")
    ) {
        // Compact student top switcher with NO wasted blank space
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    tabs.forEachIndexed { index, title ->
                        SegmentedButton(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = tabs.size),
                            icon = {
                                when (index) {
                                    0 -> Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(16.dp))
                                    1 -> Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                    else -> Icon(Icons.Default.Functions, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                            }
                        ) {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedTab) {
                0 -> RootsTableTab()
                1 -> RadicalSimplifierTab()
                2 -> RootsFormulasTab(formulasWithBookmarks, onToggleBookmark)
            }
        }
    }
}

@Composable
fun RootsTableTab() {
    var filterText by remember { mutableStateOf("") }
    val allNumbers = MathDataProvider.NUMBERS_1_TO_50

    val filteredList = remember(filterText) {
        if (filterText.isBlank()) {
            allNumbers
        } else {
            val query = filterText.trim()
            allNumbers.filter {
                it.n.toString().contains(query) ||
                it.square.toString().contains(query) ||
                it.cube.toString().contains(query)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search in Table
        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("roots_table_search"),
            placeholder = { Text("Filter numbers (e.g. 16, 25, 49)...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (filterText.isNotEmpty()) {
                    IconButton(onClick = { filterText = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Precision Scientific Table Header
        Surface(
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("n", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.7f), color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
                Text("n²", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.1f), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
                Text("√n", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.3f), color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
                Text("n³", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.3f), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
                Text("∛n", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.3f), color = MaterialTheme.colorScheme.tertiary, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
            }
        }

        // Table Rows
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
        ) {
            items(filteredList) { item ->
                val isEven = item.n % 2 == 0
                val rowColor = if (isEven) {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                } else {
                    MaterialTheme.colorScheme.surface
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(rowColor)
                        .padding(vertical = 8.5.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.n.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(0.7f),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp
                    )
                    Text(
                        text = item.square.toString(),
                        modifier = Modifier.weight(1.1f),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = item.squareRootFormatted,
                        modifier = Modifier.weight(1.3f),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.cube.toString(),
                        modifier = Modifier.weight(1.3f),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                    Text(
                        text = item.cubeRootFormatted,
                        modifier = Modifier.weight(1.3f),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), thickness = 0.5.dp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RadicalSimplifierTab() {
    var inputNum by remember { mutableStateOf("72") }
    var rootType by remember { mutableIntStateOf(2) } // 2 for square root, 3 for cube root
    val focusManager = LocalFocusManager.current

    val result = remember(inputNum, rootType) {
        val n = inputNum.trim().toLongOrNull()
        if (n != null && n > 0) {
            EquationSolvers.simplifyRadical(n, rootType)
        } else null
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                        text = "Exact Radical Decomposition",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Factors and simplifies any positive integer into canonical radical form a√b or a∛b with prime power extraction.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Degree Switcher
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        SegmentedButton(
                            selected = rootType == 2,
                            onClick = { rootType = 2 },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text("Square Root (√n)", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                        SegmentedButton(
                            selected = rootType == 3,
                            onClick = { rootType = 3 },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text("Cube Root (∛n)", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = inputNum,
                        onValueChange = { inputNum = it.filter { char -> char.isDigit() } },
                        label = { Text("Radicand Integer (N)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("radical_input_num"),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        singleLine = true,
                        leadingIcon = {
                            Text(
                                text = if (rootType == 3) "∛" else "√",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Preset buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("18", "48", "72", "108", "250").forEach { preset ->
                            SuggestionChip(
                                onClick = { inputNum = preset },
                                label = { Text((if (rootType == 3) "∛" else "√") + preset, fontFamily = FontFamily.Monospace) }
                            )
                        }
                    }
                }
            }
        }

        // Result presentation
        result?.let { solverResult ->
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                    ),
                    modifier = Modifier.testTag("radical_result_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "CANONICAL SIMPLIFIED FORM",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = solverResult.finalAnswer,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Derivation & Prime Factorization:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        solverResult.steps.forEachIndexed { idx, step ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${idx + 1}.",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = step,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                                        color = MaterialTheme.colorScheme.onSurface
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

@Composable
fun RootsFormulasTab(
    formulasWithBookmarks: List<FormulaWithBookmark>,
    onToggleBookmark: (String) -> Unit
) {
    val rootsFormulas = remember(formulasWithBookmarks) {
        formulasWithBookmarks.filter { it.formula.category == FormulaCategory.ROOTS_AND_POWERS }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(rootsFormulas, key = { it.formula.id }) { item ->
            MathFormulaCard(
                formula = item.formula,
                isBookmarked = item.isBookmarked,
                onToggleBookmark = { onToggleBookmark(item.formula.id) }
            )
        }
    }
}
