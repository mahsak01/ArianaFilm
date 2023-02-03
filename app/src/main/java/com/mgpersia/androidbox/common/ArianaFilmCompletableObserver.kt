package com.mgpersia.androidbox.common

import androidx.lifecycle.MutableLiveData
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ArianaFilmCompletableObserver(
    private val compositeDisposable: CompositeDisposable,
    private val errorHandlingLiveData: MutableLiveData<Throwable>
) :
    CompletableObserver {
    override fun onError(e: Throwable) {
        if (errorHandlingLiveData.value == null)
            errorHandlingLiveData.value = e
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }
}