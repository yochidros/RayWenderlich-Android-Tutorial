package com.raywenderlich.android.droidwiki.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yochio on 2018/03/01.
 */

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app
}