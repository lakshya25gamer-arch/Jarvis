package com.flowindustries.jarvisv3.data

import com.flowindustries.jarvisv3.model.JarvisSettings
import com.flowindustries.jarvisv3.tools.ToolRegistry
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import okhttp3.*
import java.util.concurrent.TimeUnit

class RealtimeClient(private val apiKey:String, private val settings:JarvisSettings, private val tools:ToolRegistry, private val onAssistant:(String)->Unit, private val onStatus:(String)->Unit) {
 private val client=OkHttpClient.Builder().readTimeout(0,TimeUnit.MILLISECONDS).build(); private var ws:WebSocket?=null
 fun connect(){ val req=Request.Builder().url("wss://api.openai.com/v1/realtime?model=${settings.model}").header("Authorization","Bearer $apiKey").header("OpenAI-Beta","realtime=v1").build(); ws=client.newWebSocket(req,object:WebSocketListener(){override fun onOpen(w:WebSocket,response:Response){onStatus("Connected"); val session=buildJsonObject{put("type","session.update");putJsonObject("session"){put("instructions",settings.instructions);put("voice",settings.voice);put("modalities",buildJsonArray{add("text")});if(settings.functionCalling)put("tools",tools.schemas())}};w.send(session.toString())};override fun onMessage(w:WebSocket,text:String){handle(Json.parseToJsonElement(text).jsonObject)};override fun onFailure(w:WebSocket,t:Throwable,r:Response?){onStatus("Error: ${t.message}")};override fun onClosed(w:WebSocket,c:Int,r:String){onStatus("Disconnected")}})}
 fun sendText(text:String){val item=buildJsonObject{put("type","conversation.item.create");putJsonObject("item"){put("type","message");put("role","user");putJsonArray("content"){add(buildJsonObject{put("type","input_text");put("text",text)})}}};ws?.send(item.toString());ws?.send(buildJsonObject{put("type","response.create");putJsonObject("response"){putJsonArray("modalities"){add("text")}}}.toString())}
 fun close(){ws?.close(1000,"closed");ws=null}
 private fun handle(e:JsonObject){when(e["type"]?.jsonPrimitive?.content){"response.text.delta"->e["delta"]?.jsonPrimitive?.content?.let(onAssistant);"response.done"->{};"error"->onStatus("API error")}}
}
