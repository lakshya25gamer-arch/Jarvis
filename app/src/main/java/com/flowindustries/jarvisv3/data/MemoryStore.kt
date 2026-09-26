package com.flowindustries.jarvisv3.data

interface MemoryStore { fun remember(key: String, value: String): String; fun recall(key: String): String?; fun forget(key: String): Boolean }
class InMemoryStore(private val maxEntries: Int = 30): MemoryStore {
    private val map = linkedMapOf<String,String>()
    override fun remember(key:String,value:String):String { if (map.size>=maxEntries && !map.containsKey(key)) map.remove(map.keys.first()); map[key]=value; return "Remembered $key." }
    override fun recall(key:String):String?=map[key]
    override fun forget(key:String):Boolean=map.remove(key)!=null
}
