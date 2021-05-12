package com.galaxy.foundation.logger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventLogger {

    private val LOGGER: Logger = LogManager.getLogger(EventLogger::class.java.getName())

    val gson: Gson = GsonBuilder().setExclusionStrategies(BlackListLogExclusion()  ).create()

    fun log(level: Level, message:String?, vararg args: Any?){
        val logmap: MutableMap<String, Any> = HashMap()

        if(message !=null) logmap["message"] = message
        println(args.size)
        for (obj in args){
            logmap["" + obj?.javaClass?.name] = gson.toJson(obj)
        }
        LOGGER.log(level,gson?.toJson(logmap)?.toString()?:"")
    }
    fun logMessage(logEntry: LogEntry ){
       log(logEntry.level, logEntry.message, *logEntry.params)
    }
}
