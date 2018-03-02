package com.yochio.bottomsup.di

import com.yochio.bottomsup.App
import com.yochio.bottomsup.di.modules.InteractorModule
import com.yochio.bottomsup.interaction.BreweryInteractorInterface
import dagger.Component

/**
 * Created by yochio on 2018/03/01.
 */

@Component(modules = [(InteractorModule::class)])
@ApplicationScope
interface AppComponent {
    fun inject(app: App)

    fun breweryInteractor(): BreweryInteractorInterface
}

