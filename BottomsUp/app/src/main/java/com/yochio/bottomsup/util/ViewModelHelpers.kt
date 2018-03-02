package com.yochio.bottomsup.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

/**
 * Created by yochio on 2018/03/02.
 */

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(): T =
        ViewModelProviders.of(this)[T::class.java]

inline fun <T> LiveData<T>.subscribe(lifecycle: LifecycleOwner, crossinline onChanged: (T) -> Unit) {
    observe(lifecycle, Observer { it?.run(onChanged) })
}