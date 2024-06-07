package com.etag.onlinelogger

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object OnlineLoggerClient {
    private const val BASE_URL = "https://18.139.63.32/Client_Logger/"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val trustManager = @SuppressLint("CustomX509TrustManager") object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(
            chain: Array<out java.security.cert.X509Certificate>?, authType: String?
        ) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(
            chain: Array<out java.security.cert.X509Certificate>?, authType: String?
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = emptyArray()
    }
    private val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, arrayOf<TrustManager>(trustManager), java.security.SecureRandom())
    }


    private val apiKeyInterceptor = Interceptor { chain ->
        val currentRequest = chain.request().newBuilder()
        currentRequest.addHeader("api_key", "logger_ninja")
        val newRequest = currentRequest.build()
        chain.proceed(newRequest)
    }

    private val myHttpClient = OkHttpClient().newBuilder().hostnameVerifier { _, _ -> true }
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .protocols(listOf(Protocol.HTTP_1_1)).addInterceptor(loggingInterceptor)
        .addInterceptor(apiKeyInterceptor).build()

    private val instance: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(myHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: OnlineLoggerApiInterface = instance.create(OnlineLoggerApiInterface::class.java)
}