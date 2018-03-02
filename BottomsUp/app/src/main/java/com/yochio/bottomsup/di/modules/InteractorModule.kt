package com.yochio.bottomsup.di.modules

import com.yochio.bottomsup.api.BreweryApiService
import com.yochio.bottomsup.interaction.BreweryInteractor
import com.yochio.bottomsup.interaction.BreweryInteractorInterface
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by yochio on 2018/03/02.
 */

@Module(includes = [(NetworkingModule::class)])
class InteractorModule {

    @Provides
    fun provideBreweryInteractor(apiService: BreweryApiService): BreweryInteractorInterface =
            BreweryInteractor(apiService)
}