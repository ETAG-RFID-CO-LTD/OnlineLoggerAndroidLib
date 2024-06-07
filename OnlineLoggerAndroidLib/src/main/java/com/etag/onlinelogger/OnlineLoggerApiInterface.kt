package com.etag.onlinelogger

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnlineLoggerApiInterface {

    @POST("api/Log/Info")
    suspend fun postInfo(@Body logRequest: LogRequest): LogResponse

    @POST("api/Log/Error")
    suspend fun postError(@Body logRequest: LogRequest): LogResponse

    @GET("api/Log/download-log")
    suspend fun downloadLog(@Query("projectName") projectName: String): LogResponse

    @DELETE("api/Log/delete-log-file")
    suspend fun deleteLogFile(@Query("projectName") projectName: String): LogResponse
}