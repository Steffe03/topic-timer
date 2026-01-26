package com.example.tapplecompanion

import android.media.MediaPlayer
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.os.Vibrator
import android.os.VibrationEffect
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TappleCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        inPreview = false
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(modifier: Modifier = Modifier, inPreview: Boolean) {
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val topics = tappleTopics.shuffled()
    var topicNum1 = 0
    var topicNum2 = 1
    var job by remember { mutableStateOf<Job?>(null) }
    var topic1 by remember { mutableStateOf(topics[topicNum1]) }
    var topic2 by remember { mutableStateOf(topics[topicNum2]) }
    val mediaBuzzer = if (!inPreview) MediaPlayer.create(context, R.raw.buzzer) else null  // Preview doesn't support MediaPlayer
    val mediaDing = if (!inPreview) MediaPlayer.create(context, R.raw.ding) else null
    val vibrator = context.getSystemService(Vibrator::class.java)

    Surface {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = {
                    job?.cancel()  // Cancel the previous timer

                    job = scope.launch {
                        mediaDing?.start()
                        delay(10000)
                        mediaBuzzer?.seekTo(1000)  // TODO: Cut the audio file so this isn't needed
                        mediaBuzzer?.start()
                        vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 400, 100, 400), -1))
                    }
                },
                modifier = Modifier.padding(bottom = 64.dp).size(width = 130.dp, height = 130.dp),
                content = { Text(text = "Aloita") },
            )
            Text(
                text = "$topic1\n\n$topic2",
                modifier = modifier.padding(bottom = 64.dp),
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
        Greeting(inPreview = true)
    }
}
