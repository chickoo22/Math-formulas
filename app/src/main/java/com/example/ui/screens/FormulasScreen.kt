package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EducationLevel
import com.example.data.model.MathCategory
import com.example.data.model.Subject
import com.example.data.repository.FormulaWithBookmark
import com.example.ui.components.EducationLevelChipRow
import com.example.ui.components.MathFormulaCard
import com.example.ui.components.SubjectSelectorRow
import com.example.util.PdfGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulasScreen(
    formulasWithBookmarks: List<FormulaWithBookmark>,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialSubject: Subject = Subject.MATH
) {
    var selectedSubject by remember(initialSubject) { mutableStateOf(initialSubject) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedEducationLevel by remember { mutableStateOf(EducationLevel.ALL) }
    var selectedCategory by remember { mutableStateOf(MathCategory.ALL) }
    var showOnlyBookmarked by remember { mutableStateOf(false) }
    var isMemorizeMode by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val mathCategories = remember {
        listOf(
            MathCategory.ALL,
            MathCategory.ROOTS_POWERS,
            MathCategory.ALGEBRA,
            MathCategory.TRIGONOMETRY,
            MathCategory.CALCULUS,
            MathCategory.GEOMETRY,
            MathCategory.STATISTICS,
            MathCategory.LINEAR_ALGEBRA,
            MathCategory.COMPLEX_NUMBERS
        )
    }

    val physicsCategories = remember {
        listOf(
            MathCategory.ALL,
            MathCategory.ELECTROSTATICS,
            MathCategory.CURRENT_ELECTRICITY,
            MathCategory.MAGNETISM_EMI,
            MathCategory.OPTICS,
            MathCategory.MODERN_PHYSICS
        )
    }

    val currentCategories = if (selectedSubject == Subject.MATH) mathCategories else physicsCategories

    val filteredFormulas = remember(formulasWithBookmarks, searchQuery, selectedSubject, selectedCategory, selectedEducationLevel, showOnlyBookmarked) {
        formulasWithBookmarks.filter { item ->
            val matchesSubject = item.formula.subject == selectedSubject
            val matchesLevel = if (selectedSubject == Subject.MATH) {
                selectedEducationLevel == EducationLevel.ALL || item.formula.educationLevel == selectedEducationLevel
            } else {
                true // Physics is specifically Class 12th
            }
            val matchesCategory = selectedCategory == MathCategory.ALL || item.formula.category == selectedCategory
            val matchesBookmark = !showOnlyBookmarked || item.isBookmarked
            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                val q = searchQuery.trim()
                item.formula.title.contains(q, ignoreCase = true) ||
                item.formula.formulaDisplay.contains(q, ignoreCase = true) ||
                item.formula.description.contains(q, ignoreCase = true) ||
                item.formula.subcategory.contains(q, ignoreCase = true) ||
                item.formula.educationLevel.label.contains(q, ignoreCase = true) ||
                item.formula.educationLevel.shortBadge.contains(q, ignoreCase = true) ||
                item.formula.searchTags.any { it.contains(q, ignoreCase = true) }
            }
            matchesSubject && matchesLevel && matchesCategory && matchesBookmark && matchesSearch
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("formulas_screen"),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
        // Top Subject Selector (Mathematics vs Class 12 Physics)
        item {
            SubjectSelectorRow(
                selectedSubject = selectedSubject,
                onSelectSubject = {
                    selectedSubject = it
                    selectedCategory = MathCategory.ALL
                },
                mathCount = formulasWithBookmarks.count { it.formula.subject == Subject.MATH },
                physicsCount = formulasWithBookmarks.count { it.formula.subject == Subject.PHYSICS }
            )
        }

        // Search is the primary way to find a formula. Less-common controls live in Filters.
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(if (selectedSubject == Subject.MATH) "Search formulas, topics, or symbols" else "Search physics formulas or topics") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = { if (searchQuery.isNotEmpty()) IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Clear, contentDescription = "Clear search") } },
                    modifier = Modifier.weight(1f).testTag("formula_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalIconButton(onClick = { showFilters = true }, modifier = Modifier.testTag("open_formula_filters_btn")) {
                    Icon(Icons.Default.FilterList, contentDescription = "Open filters")
                }
            }
        }

        item {
            Text(
                text = "${filteredFormulas.size} formulas${if (selectedCategory != MathCategory.ALL) " · ${selectedCategory.title}" else ""}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Formula Cards List
        if (filteredFormulas.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "No formulas found",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Try adjusting your search query or selecting 'All' categories.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(
                items = filteredFormulas,
                key = { it.formula.id }
            ) { item ->
                MathFormulaCard(
                    formula = item.formula,
                    isBookmarked = item.isBookmarked,
                    onToggleBookmark = { onToggleBookmark(item.formula.id) },
                    globalMemorizeMode = isMemorizeMode,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

  }

    if (showFilters) {
        ModalBottomSheet(onDismissRequest = { showFilters = false }) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Browse and filter", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                if (selectedSubject == Subject.MATH) {
                    Text("Class level", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    EducationLevelChipRow(selectedLevel = selectedEducationLevel, onSelectLevel = { selectedEducationLevel = it })
                }
                Text("Topic", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                currentCategories.forEach { category ->
                    val count = formulasWithBookmarks.count { it.formula.subject == selectedSubject && (category == MathCategory.ALL || it.formula.category == category) }
                    ListItem(
                        headlineContent = { Text(category.title) },
                        supportingContent = { Text("$count formulas") },
                        trailingContent = { if (selectedCategory == category) Icon(Icons.Default.Visibility, contentDescription = "Selected") },
                        modifier = Modifier.clickable { selectedCategory = category; showFilters = false }.testTag("chapter_item_${category.name.lowercase()}")
                    )
                }
                HorizontalDivider()
                FilterChip(selected = isMemorizeMode, onClick = { isMemorizeMode = !isMemorizeMode }, label = { Text("Quiz me (hide formulas)") }, leadingIcon = { Icon(Icons.Default.Psychology, null) }, modifier = Modifier.testTag("memorize_mode_chip"))
                FilterChip(selected = showOnlyBookmarked, onClick = { showOnlyBookmarked = !showOnlyBookmarked }, label = { Text("Saved formulas only") }, leadingIcon = { Icon(Icons.Default.Bookmark, null) }, modifier = Modifier.testTag("bookmark_filter_chip"))
                OutlinedButton(onClick = { PdfGenerator.generateFormulaSheetPdf(context, filteredFormulas) }, modifier = Modifier.fillMaxWidth().testTag("download_formula_pdf_btn")) {
                    Icon(Icons.Default.Download, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Download current formula sheet")
                }
                TextButton(onClick = {
                    selectedEducationLevel = EducationLevel.ALL
                    selectedCategory = MathCategory.ALL
                    showOnlyBookmarked = false
                }, modifier = Modifier.align(Alignment.End)) { Text("Clear filters") }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
