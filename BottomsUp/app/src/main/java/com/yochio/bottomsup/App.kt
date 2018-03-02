package com.yochio.bottomsup

import android.app.Application
import com.yochio.bottomsup.di.AppComponent
import com.yochio.bottomsup.di.DaggerAppComponent

/**
 * Created by yochio on 2018/03/01.
 */

class App : Application() {

    companion object {
        lateinit var instance: App
            private set

        val component: AppComponent by lazy {
            DaggerAppComponent.builder().build()
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        component.inject(this)
    }
}