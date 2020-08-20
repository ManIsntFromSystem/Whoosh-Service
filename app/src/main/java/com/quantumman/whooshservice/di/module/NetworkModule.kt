package com.quantumman.whooshservice.di.module

import com.quantumman.whooshservice.BuildConfig
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.data.remote.service.ScooterService
import com.quantumman.whooshservice.di.ApiKey
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @ApiKey
    fun provideApiKey(manager: DataManager): String {
        return manager.getPreferencesRepository().getPrefApiKey() ?: ""
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): ScooterService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ScooterService::class.java)

    @Provides
    @Singleton
    fun getOkHttpInstance(): OkHttpClient {
//        val apiKey = "zJouBcMNMLaG5WhE6LyWMav1vMuFON896ucKSjIm"
//        val interceptor: Interceptor = object : Interceptor {
//            override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request()
//                    .newBuilder()
//                    .addHeader("x-api-key", apiKey)
//                    .build())
//        }

        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
//            .addInterceptor(interceptor)
            .build()
    }
}