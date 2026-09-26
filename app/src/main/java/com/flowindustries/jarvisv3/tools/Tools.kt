package com.flowindustries.jarvisv3.tools

import com.flowindustries.jarvisv3.data.MemoryStore
import kotlinx.serialization.json.*
import java.text.SimpleDateFormat
import java.util.*

interface JarvisTool {
    val name: String
    val description: String
    val parameters: JsonObject

    suspend fun execute(args: JsonObject): String
}

class DateTimeTool : JarvisTool {
    override val name = "date_time"
    override val description = "Get the current local date and time."

    override val parameters = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {}
    }

    override suspend fun execute(args: JsonObject): String =
        SimpleDateFormat(
            "EEEE, dd MMMM yyyy HH:mm:ss",
            Locale.getDefault()
        ).format(Date())
}

class RememberTool(
    private val store: MemoryStore
) : JarvisTool {

    override val name = "remember"
    override val description = "Remember a user fact."

    override val parameters = buildJsonObject {
        put("type", "object")

        putJsonObject("properties") {
            putJsonObject("key") {
                put("type", "string")
            }

            putJsonObject("value") {
                put("type", "string")
            }
        }

        putJsonArray("required") {
            add("key")
            add("value")
        }
    }

    override suspend fun execute(args: JsonObject): String =
        store.remember(
            args["key"]?.jsonPrimitive?.content.orEmpty(),
            args["value"]?.jsonPrimitive?.content.orEmpty()
        )
}

class RecallTool(
    private val store: MemoryStore
) : JarvisTool {

    override val name = "recall"
    override val description = "Recall a remembered user fact."

    override val parameters = buildJsonObject {
        put("type", "object")

        putJsonObject("properties") {
            putJsonObject("key") {
                put("type", "string")
            }
        }

        putJsonArray("required") {
            add("key")
        }
    }

    override suspend fun execute(args: JsonObject): String =
        store.recall(
            args["key"]?.jsonPrimitive?.content.orEmpty()
        ) ?: "No memory found."
}

class ForgetTool(
    private val store: MemoryStore
) : JarvisTool {

    override val name = "forget"
    override val description = "Forget a stored user fact."

    override val parameters = buildJsonObject {
        put("type", "object")

        putJsonObject("properties") {
            putJsonObject("key") {
                put("type", "string")
            }
        }

        putJsonArray("required") {
            add("key")
        }
    }

    override suspend fun execute(args: JsonObject): String =
        if (
            store.forget(
                args["key"]?.jsonPrimitive?.content.orEmpty()
            )
        ) {
            "Forgot it."
        } else {
            "No matching memory found."
        }
}

class ToolRegistry(
    private val tools: List<JarvisTool>
) {

    private val byName = tools.associateBy { it.name }

    fun schemas() = buildJsonArray {
        tools.forEach { tool ->
            add(
                buildJsonObject {
                    put("type", "function")
                    put("name", tool.name)
                    put("description", tool.description)
                    put("parameters", tool.parameters)
                }
            )
        }
    }

    suspend fun execute(
        name: String,
        args: JsonObject
    ): String =
        byName[name]?.execute(args)
            ?: "Unknown tool: $name"
}
