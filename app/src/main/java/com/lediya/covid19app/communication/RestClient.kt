package com.lediya.covid19app.communication

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {

    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl("https://covid19-india-adhikansh.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()
        builder.client(client).build()
    }
    private val helpRetrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl("https://covid-19india-api.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()
        builder.client(client).build()
    }
    fun <T> createService(tClass: Class<T>?): T {
        return retrofit.create(tClass)
    }
    fun <T> createHelpService(tClass: Class<T>?): T {
        return helpRetrofit.create(tClass)
    }
}