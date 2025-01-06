package com.hub.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.hub.common.R
import com.hub.designsystem.theme.WorkoutTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("") }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { 
                Text(
                    text = stringResource(R.string.language_change_title),
                    color = WorkoutTheme.colors.textColor
                ) 
            },
            text = { 
                Text(
                    text = stringResource(R.string.language_change_message),
                    color = WorkoutTheme.colors.textColor
                ) 
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.setLanguage(context, selectedLanguage)
                        showConfirmDialog = false
                        (context as? Activity)?.let { activity ->
                            activity.finish()
                            activity.startActivity(activity.intent)
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.no))
                }
            },
            containerColor = WorkoutTheme.colors.bottomSheetCardBackground
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WorkoutTheme.colors.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.headlineMedium,
                color = WorkoutTheme.colors.textColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.titleMedium,
                color = WorkoutTheme.colors.textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = currentLanguage == "tr",
                    onClick = {
                        if (currentLanguage != "tr") {
                            selectedLanguage = "tr"
                            showConfirmDialog = true
                        }
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = WorkoutTheme.colors.primaryColor,
                        unselectedColor = WorkoutTheme.colors.opacityTextColor
                    )
                )
                Text(
                    text = stringResource(R.string.language_tr),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    color = WorkoutTheme.colors.textColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = currentLanguage == "en",
                    onClick = {
                        if (currentLanguage != "en") {
                            selectedLanguage = "en"
                            showConfirmDialog = true
                        }
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = WorkoutTheme.colors.primaryColor,
                        unselectedColor = WorkoutTheme.colors.opacityTextColor
                    )
                )
                Text(
                    text = stringResource(R.string.language_en),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    color = WorkoutTheme.colors.textColor
                )
            }
        }
    }
} 