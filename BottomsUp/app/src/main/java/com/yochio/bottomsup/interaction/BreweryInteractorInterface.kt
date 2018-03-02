package com.yochio.bottomsup.interaction

import com.yochio.bottomsup.api.responses.BeerResponse
import retrofit2.Callback


/**
 * Created by yochio on 2018/03/02.
 */

interface BreweryInteractorInterface {

    fun getBeers(page: Int, callback: Callback<BeerResponse>)
}