package com.example.topictimer.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.topictimer.AppViewModel
import com.example.topictimer.ui.theme.TopicTimerTheme
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewTopicsPage(onBack: () -> Unit, appViewModel: AppViewModel? = null) {

    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var topicArea by remember { mutableStateOf("") }
    var noOfTopics by remember { mutableStateOf("") }
    val noOfTopicsInt = noOfTopics.toIntOrNull()
    val wrongNoOfTopics = noOfTopicsInt != null && noOfTopicsInt !in 1..100

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars).padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = topicArea,
                    onValueChange = { topicArea = it },
                    label = { Text("Topic area") },
                )
                TextField(
                    value = noOfTopics,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            noOfTopics = input
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("Number of topics") },
                    isError = wrongNoOfTopics,
                    supportingText =
                        if (wrongNoOfTopics) {
                            { Text("1 to 100") }
                        } else {
                            null
                        },
                )
                // TODO: Select language for topics
                Button(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            try {
                                appViewModel?.askGemini(
                                    "Anna minulle $noOfTopics aihetta Tapple-lautapelille. Tässä ohjeesi: $topicArea",
                                    topicArea  // TODO: Let the user choose the name of the topic set
                                )
                                snackbarHostState.showSnackbar("Topic set created")
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    enabled = !isLoading && noOfTopicsInt in 1..100
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text("Get topic set")
                    }
                }
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