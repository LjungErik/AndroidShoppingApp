package com.thetestcompany.presentation.di.modules

import com.thetestcompany.data.networkservice.StoreApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        var clientBuilder = OkHttpClient.Builder()

        return Retrofit.Builder()
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideStoreApi(retrofit: Retrofit): StoreApi {
        return retrofit.create(StoreApi::class.java)
    }
}