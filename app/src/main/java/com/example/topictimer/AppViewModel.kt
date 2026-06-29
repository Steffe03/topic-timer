package com.example.topictimer

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.topictimer.database.AppDatabase
import com.example.topictimer.database.Topic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application, viewModelScope).topicDao()

    private val currentSetId = MutableStateFlow(0)

    private var topics: List<Topic> = emptyList()
    private var topicNum1 = 0
    private var topicNum2 = 1

    var topic1 by mutableStateOf("")
        private set

    var topic2 by mutableStateOf("")
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    private val topicsFlow = currentSetId
        .flatMapLatest { setId -> dao.getTopicsForSet(setId) }

    init {
        viewModelScope.launch {
            currentSetId.value = dao.getInitialTopicSetId().filterNotNull().first()
        }

        viewModelScope.launch {
            topicsFlow.collect { newTopics ->
                topics = newTopics
                topicNum1 = 0
                topicNum2 = 1
                updateDisplayedTopics()
            }
        }
    }

    private fun updateDisplayedTopics() {
        if (topics.size > topicNum2) {
            topic1 = topics[topicNum1].description
            topic2 = topics[topicNum2].description
        }
    }

    fun getNextTopics() {
        if (topicNum2 < topics.size - 2) {  // Check if there are enough topics left. Else start from the beginning.
            topicNum1 += 2
            topicNum2 += 2
        } else {
            topicNum1 = 0
            topicNum2 = 1
        }
        updateDisplayedTopics()
    }

    fun setTopicSet(setId: Int) {
        currentSetId.value = setId
    }

    fun getAllTopicSets() = dao.getAllTopicSets()

    fun getCurrentSetId() = currentSetId.value

    fun removeTopicSet(setId: Int) {
        viewModelScope.launch {
            dao.removeTopicSet(setId)
            currentSetId.value = dao.getInitialTopicSetId().filterNotNull().first()
        }
    }

    fun getTopicsForSet(setId: Int) = dao.getTopicsForSet(setId)

    fun removeTopic(topicId: Int) {
        viewModelScope.launch {
            dao.removeTopic(topicId)
        }
    }
}