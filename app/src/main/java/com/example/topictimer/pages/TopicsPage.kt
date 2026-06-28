package com.example.topictimer.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.topictimer.AppViewModel
import com.example.topictimer.database.Topic
import com.example.topictimer.exampleTopics
import com.example.topictimer.ui.theme.TopicTimerTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopicsPage(onBack: () -> Unit, topicSetId: Int, appViewModel: AppViewModel? = null) {
    val topics: List<Topic> =
        if (appViewModel != null) {
            appViewModel.getTopicsForSet(topicSetId).collectAsState(initial = emptyList()).value
        } else
            exampleTopics.map { topic -> Topic(description = topic, setId = topicSetId) }

    Surface(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        Column (modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                FilledTonalIconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            LazyColumn {
                items(topics) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(item.description, modifier = Modifier.fillMaxWidth(0.8F))
                            IconButton(onClick = { appViewModel?.removeTopic(item.id) }) {Icon(Icons.Filled.Delete, contentDescription = "Delete topic")}
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@PreviewLightDark
@Composable
fun TopicsPagePreview() {
    TopicTimerTheme {
        TopicsPage({}, topicSetId = 1)
    }
}