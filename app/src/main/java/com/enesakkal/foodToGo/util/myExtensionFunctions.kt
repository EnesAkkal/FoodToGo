package com.enesakkal.foodToGo.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/*

        when we remove our app from our device, both of functions will run -> readDatabase and requestApiData called!
        We need to call observe only once so we created this file.

        this is an extension function for a live data and this extension function will basically observe livedata object
        only once and not every time

        inside readDataBase function under recipesFragment class, we changed .observe to .observeOnce


 */

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}