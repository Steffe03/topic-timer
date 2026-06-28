package com.example.topictimer

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
import androidx.compose.ui.Modifier
import com.example.topictimer.ui.theme.TopicTimerTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.ChevronRight
import com.example.topictimer.pages.TimerPage
import com.example.topictimer.pages.TopicSetsPage
import com.example.topictimer.pages.TopicsPage

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopicTimerTheme {
                val navController = rememberNavController()
                val appViewModel: AppViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") {
                        HomePage(
                            onOpenTimer = { navController.navigate("timer") },
                            onOpenTopicSetsPage = { navController.navigate("topicsets_page") },
                            appViewModel = appViewModel
                        )
                    }
                    composable("timer") {
                        TimerPage(
                            onBack = { navController.popBackStack() },
                            appViewModel = appViewModel
                        )
                    }
                    composable("topicsets_page") {
                        TopicSetsPage(
                            onBack = { navController.popBackStack() },
                            onOpenTopicsPage = { topicSetId -> navController.navigate("topics_page/$topicSetId") },
                            appViewModel = appViewModel
                        )
                    }
                    composable("topics_page/{topicSetId}") { backStackEntry ->
                        val topicSetId = backStackEntry.arguments?.getString("topicSetId")?.toInt() ?: 0
                        TopicsPage(
                            onBack = { navController.popBackStack() },
                            appViewModel = appViewModel,
                            topicSetId = topicSetId
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(onOpenTimer: () -> Unit = {}, onOpenTopicSetsPage: () -> Unit = {}, appViewModel: AppViewModel? = null) {
    val haptic = LocalHapticFeedback.current

    Surface {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxSize().padding(16.dp).windowInsetsPadding(WindowInsets.systemBars)  // Keep content below phone's status bar
        ) {
            Button(
                onClick = {
                    onOpenTopicSetsPage()
                },
                content = { Text("Configure topics") },
            )
        }
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    onOpenTimer()
                },
                modifier = Modifier.padding(bottom = 64.dp).size(128.dp),
                content = { Icon(Icons.Outlined.Timer, contentDescription = "Aloita", modifier = Modifier.size(64.dp)) },
            )
        }
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(
                text = if (appViewModel != null) {"${appViewModel.topic1}\n\n${appViewModel.topic2}"} else {"Esimerkkiaihe 1\n\nEsimerkkiaihe 2"},
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    appViewModel?.getNextTopics()
                },
                content = { Icon(Icons.Outlined.ChevronRight, contentDescription = "Seuraava", modifier = Modifier.size(64.dp)) },
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
    TopicTimerTheme {
        HomePage({})
    }
}