package com.example.topictimer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

val topics = tappleTopics.shuffled()
var topicNum1 = 0
var topicNum2 = 1

class AppViewModel : ViewModel() {
    var topic1 by mutableStateOf(topics[topicNum1])
        private set

    var topic2 by mutableStateOf(topics[topicNum2])
        private set

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