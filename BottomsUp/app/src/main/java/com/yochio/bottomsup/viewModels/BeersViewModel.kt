package com.yochio.bottomsup.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.yochio.bottomsup.App
import com.yochio.bottomsup.api.responses.BeerResponse
import com.yochio.bottomsup.models.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by yochio on 2018/03/01.
 */


class BeersViewModel : ViewModel() {
    val errorData = MutableLiveData<Boolean>()
    val loadingData = MutableLiveData<Boolean>()

    val pageData = MutableLiveData<Int>()
    val beerData = MutableLiveData<List<Beer>>()

    private val interactor by lazy {
        App.component.breweryInteractor()
    }

    fun getBeers() {
        interactor.getBeers(pageData.value ?: 1, beersCallback())
    }

    fun onRefresh() {
        pageData.value = 1
        getBeers()
    }

    private fun beersCallback() = object : Callback<BeerResponse> {
        override fun onFailure(call: Call<BeerResponse>?, t: Throwable?) {
            loadingData.value = false
            errorData.value = true
        }

        override fun onResponse(call: Call<BeerResponse>?, response: Response<BeerResponse>?) {
            loadingData.value = false
            errorData.value = false

            response?.body()?.run {
                updateData(this)
            }
        }
    }

    private fun updateData(data: BeerResponse) {
        pageData.value = data.currentPage + 1
        beerData.value = data.beers
    }
}