package com.example.tapplecompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.tapplecompanion.ui.theme.TappleCompanionTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TappleCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val haptic = LocalHapticFeedback.current

    var topic1 by remember { mutableStateOf(tappleTopics.random()) }
    var topic2 by remember { mutableStateOf(tappleTopics.random()) }

    while (topic1 == topic2) {
        topic2 = tappleTopics.random()
    }

    Surface(color = Color(0xFF000000)) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "$topic1\n\n$topic2",
                modifier = modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 20.sp
            )
            Button(onClick = {
                topic1 = tappleTopics.random()
                topic2 = tappleTopics.random()
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                while (topic1 == topic2) {
                    topic2 = tappleTopics.random()
                } }, content = { Text(text = "Seuraava", fontSize = 24.sp) }, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp).size(width = 150.dp, height = 75.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TappleCompanionTheme {
        Greeting()
    }
}
