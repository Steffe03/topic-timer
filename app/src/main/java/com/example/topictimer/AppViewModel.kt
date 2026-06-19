package com.example.topictimer

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val topics: List<String> = loadTopics().shuffled()
    private var topicNum1 = 0
    private var topicNum2 = 1

    var topic1 by mutableStateOf(topics[topicNum1])
        private set

    var topic2 by mutableStateOf(topics[topicNum2])
        private set

    private fun loadTopics(): List<String> {
        val context = getApplication<Application>().applicationContext
        val jsonString = context.assets.open("topics/topics1.json").use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).readText()
        }
        val jsonArray = JSONArray(jsonString)

        return List(jsonArray.length()) { i -> jsonArray.getString(i) }
    }

    fun nextTopics() {
        if (topicNum2 < topics.size - 2) {  // Check if there are enough topics left. Else start from the beginning.
            topicNum1 += 2
            topicNum2 += 2
        } else {
            topicNum1 = 0
            topicNum2 = 1
        }
        topic1 = topics[topicNum1]
        topic2 = topics[topicNum2]
    }
}