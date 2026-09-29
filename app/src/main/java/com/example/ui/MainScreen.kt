package com.example.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.ExamNoteEntity
import com.example.ui.components.BannerAdView
import com.example.ui.screens.*
import com.example.ui.theme.ThemeMode

enum class NavDestination(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val testTag: String
) {
    FORMULAS("Formulas", Icons.Default.Functions, "nav_formulas"),
    ROOTS("Roots & Powers", Icons.Default.SquareFoot, "nav_roots"),
    SOLVERS("Solved Steps", Icons.Default.LibraryBooks, "nav_solvers"),
    CONVERTER("Converter", Icons.Default.CompareArrows, "nav_converter"),
    NOTES("Exam Notes", Icons.Default.EditNote, "nav_notes")
}

fun openPlayStoreRating(context: Context) {
    val packageName = context.packageName
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    } catch (_: ActivityNotFoundException) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MathViewModel,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf(NavDestination.FORMULAS) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showRateDialog by remember { mutableStateOf(false) }

    val formulas by viewModel.formulas.collectAsStateWithLifecycle()
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "∑∫",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "MathVerse",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.3).sp
                                )
                                Text(
                                    text = "• ${currentDestination.title}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "Student Handbook & Step Solver",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.5.sp
                            )
                        }
                    }
                },
                actions = {
                    // Rate Us Button (Google Play)
                    IconButton(
                        onClick = { showRateDialog = true },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("rate_app_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rate on Play Store",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    // Dark / Light Theme Toggle Button
                    IconButton(
                        onClick = { viewModel.toggleThemeMode() },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("theme_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (themeMode == ThemeMode.DARK) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = if (themeMode == ThemeMode.DARK) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                        )
                    }

                    // Help & Info
                    IconButton(
                        onClick = { showHelpDialog = true },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("help_info_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Help,
                            contentDescription = "App info",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // Banner ad monetized only when internet/wifi is active
                BannerAdView(isOnline = isOnline)

                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    NavDestination.entries.forEach { destination ->
                        val isSelected = currentDestination == destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentDestination = destination },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = destination.title,
                                    fontSize = 10.5.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    maxLines = 1
                                )
                            },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier.testTag(destination.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = currentDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = "tab_crossfade"
        ) { target ->
            when (target) {
                NavDestination.FORMULAS -> {
                    FormulasScreen(
                        formulasWithBookmarks = formulas,
                        onToggleBookmark = { viewModel.toggleBookmark(it) }
                    )
                }
                NavDestination.ROOTS -> {
                    RootsSectionScreen(
                        formulasWithBookmarks = formulas,
                        onToggleBookmark = { viewModel.toggleBookmark(it) }
                    )
                }
                NavDestination.SOLVERS -> {
                    SolvedEquationsScreen()
                }
                NavDestination.CONVERTER -> {
                    UnitConverterScreen()
                }
                NavDestination.NOTES -> {
                    NotesScreen(
                        notes = notes,
                        onAddNote = { viewModel.addNote(it) },
                        onUpdateNote = { viewModel.updateNote(it) },
                        onDeleteNote = { viewModel.deleteNote(it) },
                        onTogglePin = { viewModel.togglePinNote(it) }
                    )
                }
            }
        }
    }

    // Rate App on Google Play Dialog
    if (showRateDialog) {
        AlertDialog(
            onDismissRequest = { showRateDialog = false },
            icon = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            title = {
                Text(
                    text = "Rate MathVerse on Google Play",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "If MathVerse is helping you revise formulas and solve equations, please support us with a quick rating on Google Play!",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Your review helps other students discover this free mathematics handbook.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRateDialog = false
                        openPlayStoreRating(context)
                    },
                    modifier = Modifier.testTag("rate_playstore_confirm_btn")
                ) {
                    Text("Rate on Play Store")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRateDialog = false }) {
                    Text("Maybe Later")
                }
            }
        )
    }

    // Help & App Info Dialog
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = {
                Text("About MathVerse", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• 100% Offline Access: All formula references, solved equations, units, and custom notes operate completely offline with zero data consumption.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Banner Ads: Non-intrusive banner ads load only when Wi-Fi or mobile network is connected, leaving 100% of study features accessible offline.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Comprehensive Formula Library: Full coverage across Roots & Radicals, Algebra, Trigonometry, Calculus, Geometry, Statistics, Linear Algebra, and Complex Numbers.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Solved Equations & Solvers: Self-study step derivations, quadratic roots and vertex solvers, 2x2 linear systems, and right triangle calculators.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Exam Prep Notes: Persistent notes with mobile-optimized math symbol toolbar and pin support.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        showHelpDialog = false
                        openPlayStoreRating(context)
                    }) {
                        Text("Rate Us")
                    }
                    Button(onClick = { showHelpDialog = false }) {
                        Text("Close")
                    }
                }
            }
        )
    }
}
