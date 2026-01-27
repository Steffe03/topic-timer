package com.example.tapplecompanion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.tapplecompanion.ui.theme.TappleCompanionTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TappleCompanionTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") {
                        HomePage(
                            onOpenTimer = { navController.navigate("timer") }
                        )
                    }
                    composable("timer") {
                        TimerPage(onBack = { navController.popBackStack() }, inPreview = false)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(onOpenTimer: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current

    val topics = tappleTopics.shuffled()
    var topicNum1 = 0
    var topicNum2 = 1
    var topic1 by remember { mutableStateOf(topics[topicNum1]) }
    var topic2 by remember { mutableStateOf(topics[topicNum2]) }

    Surface {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = {
                    onOpenTimer()
                },
                modifier = Modifier.padding(bottom = 64.dp).size(width = 130.dp, height = 130.dp),
                content = { Text(text = "Aloita") },
            )
            Text(
                text = "$topic1\n\n$topic2",
                modifier = Modifier.padding(bottom = 64.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    if (topicNum2 < topics.size - 2) {  // Check if there are enough topics left. Else start from the beginning.
                        topicNum1 += 2
                        topicNum2 += 2
                    } else {
                        topicNum1 = 0
                        topicNum2 = 1
                    }
                    topic1 = topics[topicNum1]
                    topic2 = topics[topicNum2]
                },
                content = { Text(text = "Seuraava") },
                modifier = Modifier.padding(bottom = 64.dp).size(width = 130.dp, height = 65.dp),
                contentPadding = PaddingValues(8.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun GreetingPreview() {
    TappleCompanionTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomePage(
                    onOpenTimer = { navController.navigate("timer") }
                )
            }
            composable("timer") {
                TimerPage(onBack = { navController.popBackStack() }, inPreview = true)
            }
        }
    }
}
