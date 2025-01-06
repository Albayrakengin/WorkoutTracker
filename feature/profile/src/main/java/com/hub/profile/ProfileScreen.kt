package com.hub.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.res.stringResource
import com.hub.common.R
import com.hub.designsystem.theme.WorkoutTheme
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isSignedOut) {
        if (state.isSignedOut) {
            onSignOut()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WorkoutTheme.colors.backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    AsyncImage(
                        model = state.user?.photoUrl ?: "https://via.placeholder.com/150",
                        contentDescription = "Profil fotoğrafı",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (state.isEditMode) {
                    OutlinedTextField(
                        value = state.user?.displayName ?: "",
                        onValueChange = { viewModel.onEvent(ProfileEvent.UpdateDisplayName(it)) },
                        label = { 
                            Text(
                                text = stringResource(R.string.name),
                                color = WorkoutTheme.colors.textColor
                            ) 
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = WorkoutTheme.colors.textColor,
                            unfocusedTextColor = WorkoutTheme.colors.textColor,
                            focusedBorderColor = WorkoutTheme.colors.primaryColor,
                            unfocusedBorderColor = WorkoutTheme.colors.borderAndLightBorder
                        )
                    )
                } else {
                    Text(
                        text = state.user?.displayName ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = WorkoutTheme.colors.textColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.user?.email ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = WorkoutTheme.colors.opacityTextColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { viewModel.onEvent(ProfileEvent.ToggleEditMode) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WorkoutTheme.colors.primaryColor,
                            contentColor = WorkoutTheme.colors.textColorReverse
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Düzenle"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (state.isEditMode) stringResource(R.string.cancel) else stringResource(R.string.edit))
                    }

                    if (state.isEditMode) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { viewModel.onEvent(ProfileEvent.SaveProfile) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WorkoutTheme.colors.primaryColor,
                                contentColor = WorkoutTheme.colors.textColorReverse
                            )
                        ) {
                            Text(stringResource(R.string.save))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = { viewModel.onEvent(ProfileEvent.SignOut) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = WorkoutTheme.colors.primaryColor
                    )
                ) {
                    Text(stringResource(R.string.logout))
                }
            }

            state.error?.let { error ->
                AlertDialog(
                    onDismissRequest = { viewModel.onEvent(ProfileEvent.ErrorDismissed) },
                    title = { 
                        Text(
                            text = stringResource(R.string.error),
                            color = WorkoutTheme.colors.textColor
                        ) 
                    },
                    text = { 
                        Text(
                            text = error,
                            color = WorkoutTheme.colors.textColor
                        ) 
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { viewModel.onEvent(ProfileEvent.ErrorDismissed) },
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

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = WorkoutTheme.colors.primaryColor
                )
            }
        }
    }
} 