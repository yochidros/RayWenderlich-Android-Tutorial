package com.raywenderlich.android.droidwiki.dagger

import com.raywenderlich.android.droidwiki.dagger.modules.NetworkModule
import com.raywenderlich.android.droidwiki.dagger.modules.PresenterModule
import com.raywenderlich.android.droidwiki.dagger.modules.WikiModule
import com.raywenderlich.android.droidwiki.ui.homepage.HomepageActivity
import com.raywenderlich.android.droidwiki.ui.search.SearchActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yochio on 2018/03/01.
 */


@Singleton
@Component(modules = [
    AppModule::class,
    PresenterModule::class,
    NetworkModule::class,
    WikiModule::class
])
interface AppComponent {
    fun injectHome(target: HomepageActivity)

    fun injectEntry(target: SearchActivity)
}
