package com.hub.feature.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import com.hub.common.R
import com.hub.designsystem.theme.WorkoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var passwordVisible by remember { mutableStateOf(false) }

    if (state.error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(LoginEvent.ErrorDismissed) },
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
                    onClick = { viewModel.onEvent(LoginEvent.ErrorDismissed) },
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
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineMedium,
                    color = WorkoutTheme.colors.textColor
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
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
                        imeAction = ImeAction.Next
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

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                    label = { 
                        Text(
                            text = stringResource(R.string.password),
                            color = WorkoutTheme.colors.textColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock, 
                            contentDescription = null,
                            tint = WorkoutTheme.colors.iconTint
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Close else Icons.Default.Face,
                                contentDescription = if (passwordVisible) "Şifreyi gizle" else "Şifreyi göster",
                                tint = WorkoutTheme.colors.iconTint
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
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

                TextButton(
                    onClick = onForgotPasswordClick,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.forgot_password))
                }

                Button(
                    onClick = { viewModel.onEvent(LoginEvent.LoginClicked) },
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
                        Text(stringResource(R.string.login))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dont_you_have_an_account),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = WorkoutTheme.colors.textColor
                    )
                    TextButton(
                        onClick = onSignUpClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = WorkoutTheme.colors.primaryColor
                        )
                    ) {
                        Text(stringResource(R.string.signup))
                    }
                }
            }
        }
    }
} 