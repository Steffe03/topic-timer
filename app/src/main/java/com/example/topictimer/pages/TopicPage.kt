package com.example.topictimer.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.topictimer.AppViewModel
import com.example.topictimer.ui.theme.TopicTimerTheme
import androidx.compose.material3.Surface
import com.example.topictimer.exampleTopicSets


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopicPage(onBack: () -> Unit, appViewModel: AppViewModel? = null) {
    val topicSets = appViewModel?.getAllTopicSets()?.collectAsState(initial = null)?.value ?: exampleTopicSets

    Surface(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(topicSets.size) { index ->
                    val topicSet = topicSets[index]
                    Text(topicSet.name)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun TopicPagePreview() {
    TopicTimerTheme {
        TopicPage({})
    }
}