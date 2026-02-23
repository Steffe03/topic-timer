package com.example.tapplecompanion

import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimerPage(onBack: () -> Unit, inPreview: Boolean, appViewModel: AppViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var job by remember { mutableStateOf<Job?>(null) }
    val mediaBuzzer = if (!inPreview) MediaPlayer.create(context, R.raw.buzzer) else null  // Preview doesn't support MediaPlayer
    val mediaDing = if (!inPreview) MediaPlayer.create(context, R.raw.ding) else null
    val vibrator = context.getSystemService(Vibrator::class.java)

    fun nextTurn() {
        job?.cancel()

        job = scope.launch {
            mediaDing?.start()
            delay(10000)
            mediaBuzzer?.seekTo(1000)  // TODO: Cut the audio file so this isn't needed
            mediaBuzzer?.start()
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 400, 100, 400), -1))
        }

    }

    LaunchedEffect(Unit) {
        nextTurn()
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isPressed) Color(114, 47, 55) else Color(6, 64, 43))
            .clickable (
                interactionSource = interactionSource
            ) {
                nextTurn()
            },
    ) {
        Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "${appViewModel.topic1}\n\n${appViewModel.topic2}",
                modifier = Modifier.padding(bottom = 64.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
