package com.raywenderlich.android.droidwiki.dagger.modules

import com.raywenderlich.android.droidwiki.network.Homepage
import com.raywenderlich.android.droidwiki.network.Wiki
import com.raywenderlich.android.droidwiki.ui.homepage.HomepagePresenter
import com.raywenderlich.android.droidwiki.ui.homepage.HomepagePresenterImpl
import com.raywenderlich.android.droidwiki.ui.search.EntryPresenter
import com.raywenderlich.android.droidwiki.ui.search.EntryPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yochio on 2018/03/01.
 */

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideHomepagePresenter(homepage: Homepage): HomepagePresenter = HomepagePresenterImpl(homepage)

    @Provides
    @Singleton
    fun provideEntryPresenter(wiki: Wiki): EntryPresenter = EntryPresenterImpl(wiki)
}