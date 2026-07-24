package com.example.topictimer

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.topictimer.database.AppDatabase
import com.example.topictimer.database.Topic
import com.example.topictimer.database.TopicSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.Schema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application, viewModelScope).topicDao()
    private val securityManager = SecurityManager(application)

    private val currentSetId = MutableStateFlow(0)

    private var topics: List<Topic> = emptyList()
    private var topicNum1 = 0
    private var topicNum2 = 1

    var topic1 by mutableStateOf("")
        private set

    var topic2 by mutableStateOf("")
        private set

    val apiKey: StateFlow<String?> = securityManager.apiKeyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

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

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            securityManager.saveApiKey(apiKey)
        }
    }

    fun clearApiKey() {
        viewModelScope.launch {
            securityManager.clearApiKey()
        }
    }

    suspend fun askGemini(prompt: String, setName: String): Unit = withContext(Dispatchers.IO) {
        val key = securityManager.apiKeyFlow.first()
            ?: throw IllegalStateException("API key has not been set. Please check your settings.")

        val client = Client.builder().apiKey(key).build()

        val schema = Schema.builder()
            .type("ARRAY")
            .items(Schema.builder().type("STRING").build())
            .build()

        val config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(schema)
            .build()

        val response = client.models.generateContent(
            "gemini-3.1-flash-lite",
            prompt,
            config
        )

        val json = response.text() ?: "[]"
        val newTopics = Json.decodeFromString<List<String>>(json)
        val setId = dao.insertTopicSet(TopicSet(name = setName)).toInt()

        dao.insertAll(newTopics.map { description -> Topic(description = description, setId = setId) })
    }
}