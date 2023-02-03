package com.mgpersia.androidbox.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgpersia.androidbox.common.ArianaFilmCompletableObserver
import com.mgpersia.androidbox.common.ArianaFilmSingleObserver
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.data.repository.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LocalShareViewModel(private val localRepository: LocalRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

     val getFilmsLiveData = MutableLiveData<List<LocalFilmDownload>>()
     val downloadFilmsLiveData = MutableLiveData<ArrayList<LocalFilmDownload>>()

    val errorHandlingLiveData = MutableLiveData<Throwable>()

    fun addFilm(localFilmDownload: LocalFilmDownload) {
        localRepository.addFilm(localFilmDownload)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {
                    getAllFilm()
                    downloadFilmsLiveData.value!!.remove(searchDownload(localFilmDownload.idFilm!!))
                }
            })
    }

    fun addDownloadFilm(localFilmDownload: LocalFilmDownload) {
        if (downloadFilmsLiveData.value == null)
            downloadFilmsLiveData.value = ArrayList()

        downloadFilmsLiveData.value!!.add(localFilmDownload)
    }

    fun getAllFilm() {
        if (UserContainer.user != null)
            UserContainer.user!!.email?.let {
                localRepository.getAllFilm(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ArianaFilmSingleObserver<List<LocalFilmDownload>?>(
                        compositeDisposable,
                        errorHandlingLiveData
                    ) {
                        override fun onSuccess(t: List<LocalFilmDownload>) {
                            getFilmsLiveData.value = t
                        }
                    })
            }
    }

    fun searchDownload(id: Int): LocalFilmDownload? {
        if (downloadFilmsLiveData.value != null)
            downloadFilmsLiveData.value!!.forEach { item ->
                if (item.idFilm == id)
                    return item
            }

        return null
    }


    fun searchFilm(id: Int): Boolean {
        if (getFilmsLiveData.value != null)
            getFilmsLiveData.value!!.forEach { item ->
                if (item.idFilm == id)
                    return true
            }

        return false
    }


}