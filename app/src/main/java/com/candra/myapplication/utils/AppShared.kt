package com.candra.myapplication.utils

import com.candra.myapplication.BuildConfig
import com.candra.myapplication.data.remote.ApiServices
import com.candra.myapplication.data.remote.DataManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("SameParameterValue")
class AppShared {
    companion object {
        fun dataManager() : DataManager {
            if (this.dataManager == null) {
                dataManager = DataManager(getServiceInstance(BuildConfig.BASE_URL, ApiServices::class.java))
            }
            return dataManager!!
        }

        private var dataManager : DataManager? = null

        private fun <T> getServiceInstance(url : String, serviceClass: Class<T>) : T {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .connectTimeout(25, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()


            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(serviceClass)
        }
    }
}