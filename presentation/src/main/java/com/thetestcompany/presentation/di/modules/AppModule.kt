package com.thetestcompany.presentation.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context) {
    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }
}