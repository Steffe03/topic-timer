package com.example.topictimer.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.topictimer.ui.theme.TopicTimerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ApiKeyDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onClearKey: () -> Unit,
    apiKeySaved: Boolean
) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp
        ) {
            Column (modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if (!apiKeySaved) {
                    Text("Give your personal Google AI API key")
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("API key") },
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                        ),
                        singleLine = true
                    )
                    Button(onClick = {
                        onConfirm(text)
                    }) {
                        Text("Submit")
                    }
                } else {
                    Text("Your API key has been saved")
                    Button(onClick = {
                        onClearKey()
                    }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text("Delete saved key")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun ApiKeyDialogPreview() {
    TopicTimerTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ApiKeyDialog({}, {}, {}, false)
        }
    }
}