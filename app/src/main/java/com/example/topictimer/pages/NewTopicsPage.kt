package com.example.topictimer.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.topictimer.AppViewModel
import com.example.topictimer.ui.theme.TopicTimerTheme
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewTopicsPage(onBack: () -> Unit, appViewModel: AppViewModel? = null) {

    var prompt: String? by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Surface {
        // TODO: Use Gemini for asking for new topics
        Column (modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        try {
                            prompt = appViewModel?.askGemini("What is the square root of 21")
                        } catch (e: Exception) {
                            prompt = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading
            ) {
                Text("Test Gemini")
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(prompt ?: "No response from Gemini")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun NewTopicsPagePreview() {
    TopicTimerTheme {
        NewTopicsPage({})
    }
}