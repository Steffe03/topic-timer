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
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.topictimer.AppViewModel
import com.example.topictimer.ui.theme.TopicTimerTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.example.topictimer.exampleTopicSets
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import com.example.topictimer.database.TopicSetWithCount
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopicPage(onBack: () -> Unit, appViewModel: AppViewModel? = null) {
    val topicSets: List<TopicSetWithCount> =
        if (appViewModel != null) {
            appViewModel.getAllTopicSets().collectAsState(initial = emptyList()).value
        } else {
            exampleTopicSets
        }

    var currentSetId by remember { mutableIntStateOf(1) }

    if (appViewModel != null) {
        currentSetId = appViewModel.getCurrentSetId()
    }

    Surface(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Box (
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
                    items(topicSets) { item ->
                        Card(
                            onClick = {
                                appViewModel?.setTopicSet(item.topicSet.id)
                                currentSetId = item.topicSet.id
                            },
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Text(item.topicSet.name)
                                if (item.topicSet.id == currentSetId) {
                                    Icon(Icons.Outlined.Check, contentDescription = "Current set", tint = Color.Green, modifier = Modifier.padding(start = 8.dp))
                                }
                                Text("(${item.topicCount} topics)", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                            }
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
fun TopicPagePreview() {
    TopicTimerTheme {
        TopicPage({})
    }
}