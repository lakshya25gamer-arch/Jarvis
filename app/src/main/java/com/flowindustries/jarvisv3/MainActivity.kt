package com.flowindustries.jarvisv3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flowindustries.jarvisv3.model.ChatMessage
import com.flowindustries.jarvisv3.ui.JarvisViewModel

class MainActivity:ComponentActivity(){override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{JarvisApp()}}}
@Composable fun JarvisApp(vm:JarvisViewModel=viewModel()){
 var apiKey by remember{mutableStateOf("")};var input by remember{mutableStateOf("")};var tab by remember{mutableIntStateOf(0)};val messages by vm.messages.collectAsState();val status by vm.status.collectAsState()
 MaterialTheme{Scaffold{pad->Column(Modifier.fillMaxSize().padding(pad).padding(16.dp)){Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){Image(painterResource(com.flowindustries.jarvisv3.R.drawable.jarvis_logo),null,Modifier.size(72.dp));Spacer(Modifier.width(12.dp));Column{Text("JARVIS V3",style=MaterialTheme.typography.headlineSmall);Text(status,style=MaterialTheme.typography.bodySmall)}}}
 TabRow(selectedTabIndex=tab){Tab(tab==0,{tab=0},text={Text("Chat")});Tab(tab==1,{tab=1},text={Text("Realtime")});Tab(tab==2,{tab=2},text={Text("Settings")})}
 when(tab){0->Column(Modifier.fillMaxSize()){LazyColumn(Modifier.weight(1f),contentPadding=PaddingValues(vertical=12.dp)){items(messages){m->Surface(shape=RoundedCornerShape(14.dp),tonalElevation=2.dp,modifier=Modifier.fillMaxWidth().padding(vertical=4.dp)){Text((if(m.role==ChatMessage.Role.USER)"You: " else "JARVIS: ")+m.text,Modifier.padding(12.dp))}}};Row{OutlinedTextField(input,{input=it},Modifier.weight(1f),placeholder={Text("Ask JARVIS...")});Spacer(Modifier.width(8.dp));Button({vm.send(input);input=""}){Text("Send")}}}
1->Column(Modifier.fillMaxSize(),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){Text("Realtime audio mode");Text("Audio transport is the next Android phase.",Modifier.padding(12.dp));Button({vm.connect(apiKey)}){Text("Connect")}}
2->Column(Modifier.fillMaxWidth()){Text("Settings",style=MaterialTheme.typography.headlineSmall);OutlinedTextField(apiKey,{apiKey=it},Modifier.fillMaxWidth(),label={Text("OpenAI API Key")});Spacer(Modifier.height(12.dp));Button({vm.connect(apiKey)}){Text("Connect JARVIS")};Spacer(Modifier.height(20.dp));Text("This Android implementation keeps the V3 chat/realtime architecture while replacing desktop-only tools with controlled mobile tools.")}}
 }}}
