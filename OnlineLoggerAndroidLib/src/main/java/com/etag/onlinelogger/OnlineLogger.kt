package com.etag.onlinelogger

class OnlineLogger(private val apiInterface: OnlineLoggerApiInterface) {


    companion object {
        private var instance: OnlineLogger? = null
        lateinit var mProjectName: String
        fun getInstance(apiInterface: OnlineLoggerApiInterface, projectName: String): OnlineLogger {
            if (instance == null) {
                mProjectName = projectName
                instance = OnlineLogger(apiInterface)
            }
            return instance!!
        }
    }

    suspend fun logInfo(msg: String) {
        val logRequest = LogRequest(mProjectName, msg)
        try {
            val response = apiInterface.postInfo(logRequest)
            println("Log info posted successfully: ${response.message}")
        } catch (e: Exception) {
            println("Failed to post log info: ${e.message}")
        }
    }

    suspend fun logError(msg: String) {
        val logRequest = LogRequest(mProjectName, msg)
        try {
            val response = apiInterface.postError(logRequest)
            println("Log error posted successfully: ${response.message}")
        } catch (e: Exception) {
            println("Failed to post log error: ${e.message}")
        }
    }

    suspend fun downloadLogFile() {
        try {
            val response = apiInterface.downloadLog(mProjectName)
            println("Log file download initiated: ${response.message}")
        } catch (e: Exception) {
            println("Failed to initiate log file download: ${e.message}")
        }
    }

    suspend fun deleteLogFile() {
        try {
            val response = apiInterface.deleteLogFile(mProjectName)
            println("Log file deleted successfully: ${response.message}")
        } catch (e: Exception) {
            println("Failed to delete log file: ${e.message}")
        }
    }
}