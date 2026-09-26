package com.flowindustries.jarvisv3.model

data class ChatMessage(val role: Role, val text: String) { enum class Role { USER, ASSISTANT } }
data class JarvisSettings(val model: String = "gpt-realtime", val voice: String = "echo", val instructions: String = "You are JARVIS, a helpful personal AI assistant.", val vad: Boolean = true, val functionCalling: Boolean = true, val includeDate: Boolean = true, val includeTime: Boolean = true)
