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
import com.example.topictimer.ui.theme.TappleCompanionTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
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
                val appViewModel: AppViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") {
                        HomePage(
                            onOpenTimer = { navController.navigate("timer") },
                            appViewModel = appViewModel
                        )
                    }
                    composable("timer") {
                        TimerPage(
                            onBack = { navController.popBackStack() },
                            inPreview = false,
                            appViewModel = appViewModel)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(onOpenTimer: () -> Unit = {}, appViewModel: AppViewModel) {
    val haptic = LocalHapticFeedback.current

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
                text = "${appViewModel.topic1}\n\n${appViewModel.topic2}",
                modifier = Modifier.padding(bottom = 64.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    appViewModel.nextTopics()
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
        val appViewModel: AppViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomePage(
                    onOpenTimer = { navController.navigate("timer") },
                    appViewModel = appViewModel
                )
            }
            composable("timer") {
                TimerPage(
                    onBack = { navController.popBackStack() },
                    inPreview = true,
                    appViewModel = appViewModel)
            }
        }
    }
}
