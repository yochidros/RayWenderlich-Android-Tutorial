package com.raywenderlich.android.droidwiki.dagger.modules

import com.raywenderlich.android.droidwiki.network.Homepage
import com.raywenderlich.android.droidwiki.network.Wiki
import com.raywenderlich.android.droidwiki.network.WikiApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yochio on 2018/03/01.
 */


@Module
class WikiModule {
    @Provides
    @Singleton
    fun provideHomepage(api: WikiApi) = Homepage(api)

    @Provides
    @Singleton
    fun provideWiki(api: WikiApi) = Wiki(api)
}