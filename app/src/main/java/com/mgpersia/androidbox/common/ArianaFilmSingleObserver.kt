package com.mgpersia.androidbox.common

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class ArianaFilmSingleObserver<T>(
    val compositeDisposable: CompositeDisposable,
    val errorHandlingLiveData: MutableLiveData<Throwable>
) :
    SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        if (compositeDisposable.size() == 0)
            compositeDisposable.add(d)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onError(e: Throwable) {
        if (errorHandlingLiveData.value==null)
        errorHandlingLiveData.value = e
    }
}