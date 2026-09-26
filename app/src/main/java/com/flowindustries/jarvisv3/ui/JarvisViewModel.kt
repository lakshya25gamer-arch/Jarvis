package com.flowindustries.jarvisv3.ui

import androidx.lifecycle.ViewModel
import com.flowindustries.jarvisv3.data.*
import com.flowindustries.jarvisv3.model.*
import com.flowindustries.jarvisv3.tools.*
import kotlinx.coroutines.flow.*

class JarvisViewModel : ViewModel() {

    private val memory = InMemoryStore()

    private val registry = ToolRegistry(
        listOf(
            DateTimeTool(),
            RememberTool(memory),
            RecallTool(memory),
            ForgetTool(memory)
        )
    )

    private val _messages =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages = _messages.asStateFlow()

    private val _status =
        MutableStateFlow("Not connected")

    val status = _status.asStateFlow()

    private var client: RealtimeClient? = null

    fun connect(apiKey: String) {

        if (apiKey.isBlank()) {
            _status.value = "Add API key in Settings"
            return
        }

        client?.close()

        client = RealtimeClient(
            apiKey,
            JarvisSettings(),
            registry,
            { delta ->
                appendAssistant(delta)
            },
            { status ->
                _status.value = status
            }
        )

        client?.connect()
    }

    fun send(text: String) {

        if (text.isBlank()) return

        _messages.update {
            it + ChatMessage(
                ChatMessage.Role.USER,
                text
            )
        }

        client?.sendText(text) ?: run {
            _status.value = "Not connected"
        }
    }

    private fun appendAssistant(text: String) {

        _messages.update {

            val list = it.toMutableList()

            if (
                list.lastOrNull()?.role ==
                ChatMessage.Role.ASSISTANT
            ) {

                list[list.lastIndex] =
                    list.last().copy(
                        text = list.last().text + text
                    )

            } else {

                list += ChatMessage(
                    ChatMessage.Role.ASSISTANT,
                    text
                )
            }

            list
        }
    }

    override fun onCleared() {
        client?.close()
    }
}
