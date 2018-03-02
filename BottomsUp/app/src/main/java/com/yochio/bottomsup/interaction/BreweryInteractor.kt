package com.yochio.bottomsup.interaction

import com.yochio.bottomsup.api.BreweryApiService
import com.yochio.bottomsup.api.responses.BeerResponse
import com.yochio.bottomsup.util.AppConst
import retrofit2.Callback

/**
 * Created by yochio on 2018/03/02.
 */

class BreweryInteractor(private val apiService: BreweryApiService) : BreweryInteractorInterface {

    override fun getBeers(page: Int, callback: Callback<BeerResponse>) {
        apiService.getBeers(page, AppConst.API_KEY).enqueue(callback)
    }
}