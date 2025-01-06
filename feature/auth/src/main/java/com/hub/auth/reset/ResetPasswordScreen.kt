package com.hub.feature.auth.reset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import com.hub.common.R
import com.hub.designsystem.theme.WorkoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    onBackClick: () -> Unit,
    onResetSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onResetSuccess()
        }
    }

    if (state.error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(ResetPasswordEvent.ErrorDismissed) },
            title = { 
                Text(
                    text = stringResource(R.string.error),
                    color = WorkoutTheme.colors.textColor
                ) 
            },
            text = { 
                Text(
                    text = state.error,
                    color = WorkoutTheme.colors.textColor
                ) 
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.onEvent(ResetPasswordEvent.ErrorDismissed) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            containerColor = WorkoutTheme.colors.bottomSheetCardBackground
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = WorkoutTheme.colors.backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.reset_password),
                    style = MaterialTheme.typography.headlineMedium,
                    color = WorkoutTheme.colors.textColor
                )

                Text(
                    text = "Şifrenizi sıfırlamak için e-posta adresinizi girin. Size sıfırlama bağlantısı göndereceğiz.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp),
                    color = WorkoutTheme.colors.opacityTextColor
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(ResetPasswordEvent.EmailChanged(it)) },
                    label = { 
                        Text(
                            text = stringResource(R.string.email),
                            color = WorkoutTheme.colors.textColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email, 
                            contentDescription = null,
                            tint = WorkoutTheme.colors.iconTint
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = WorkoutTheme.colors.textColor,
                        unfocusedTextColor = WorkoutTheme.colors.textColor,
                        focusedBorderColor = WorkoutTheme.colors.primaryColor,
                        unfocusedBorderColor = WorkoutTheme.colors.borderAndLightBorder,
                        cursorColor = WorkoutTheme.colors.primaryColor
                    )
                )

                Button(
                    onClick = { viewModel.onEvent(ResetPasswordEvent.ResetClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WorkoutTheme.colors.primaryColor,
                        contentColor = WorkoutTheme.colors.textColorReverse,
                        disabledContainerColor = WorkoutTheme.colors.primaryColor.copy(alpha = 0.6f)
                    )
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = WorkoutTheme.colors.textColorReverse
                        )
                    } else {
                        Text(stringResource(R.string.send_reset_link))
                    }
                }

                TextButton(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.go_back))
                }
            }
        }
    }
} 