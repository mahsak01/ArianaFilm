package com.mgpersia.androidbox.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgpersia.androidbox.common.ArianaFilmSingleObserver
import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.Country
import com.mgpersia.androidbox.data.model.Film
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    val categoryGenreLiveData = MutableLiveData<GetAllGenreInformation>()

    val categoryTagLiveData = MutableLiveData<GetAllGenreInformation>()

    val countriesLiveData = MutableLiveData<List<Country>>()

    val countriesFilmsLiveData = MutableLiveData<List<Country>>()

    val getOptLiveData = MutableLiveData<GetOptInformation>()

    val checkOptLiveData = MutableLiveData<GetCheckOptInformation>()

    val registerUserInformationLiveData = MutableLiveData<GetRegisterUserResultInformation>()

    val getLiveLiveData = MutableLiveData<GetLiveInformation>()

    val getFilmDetailInformation = MutableLiveData<GetFilmDetailInformation>()

    val getActorDetailInformation = MutableLiveData<GetActorDetailInformation>()

    val getBannerHomeLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getNewFilmLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getNewSeriesLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getNewlySeriesLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getCommingSoonLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getPopularActorLiveData = MutableLiveData<List<ActorFilmDetailInformation>>()

    val getPopularDirectorLiveData = MutableLiveData<List<ActorFilmDetailInformation>>()

    val getAllActorLiveData = MutableLiveData<GetAllActorInformation>()

    val getAllDirectorLiveData = MutableLiveData<GetAllActorInformation>()

    val getPopularFilmLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getPopularMovieLiveData = MutableLiveData<GetAllImageFilmHomeInformation>()

    val getAllCommentLiveData = MutableLiveData<GetAllCommentOfFilmInformation>()

    val getAgeInformationLiveData = MutableLiveData<GetAgeLimitInformation>()

    val getFilterFilmInformationLiveData = MutableLiveData<GetFilterInformation>()

    val getFilterSerialInformationLiveData = MutableLiveData<GetFilterInformation>()

    val errorHandlingLiveData = MutableLiveData<Throwable>()

    val checkLocationLiveData = MutableLiveData<CheckLocationInformation>()

    fun checkLocationInformation() {

        mainRepository.getLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<CheckLocationInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: CheckLocationInformation) {
                    checkLocationLiveData.value = t
                }
            })


    }


    fun getFilterSerialInformation(
        dubbed: String,
        type: String,
        subtitle: String,
        age: String,
        toyear: String,
        fromyear: String,
        tag: String,
        genre: String,
        country: String,
        page: String,
        page_size: String,
        text: String
    ) {

        mainRepository.getSearch(
            dubbed,
            type,
            subtitle,
            age,
            toyear,
            fromyear,
            tag,
            genre,
            country,
            page,
            page_size,
            text
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetFilterInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetFilterInformation) {
                    if (page == "1")
                        getFilterSerialInformationLiveData.value = t
                    else {
                        var list = ArrayList<Film>()
                        getFilterSerialInformationLiveData.value!!.results?.forEach { item ->
                            list.add(
                                item
                            )
                        }
                        t.results?.forEach { item -> list.add(item) }

                        t.results = list
                        getFilterSerialInformationLiveData.value = t
                    }
                }


            })

    }

    fun getFilterFilmInformation(
        dubbed: String,
        type: String,
        subtitle: String,
        age: String,
        toyear: String,
        fromyear: String,
        tag: String,
        genre: String,
        country: String,
        page: String,
        page_size: String,
        text: String
    ) {

        mainRepository.getSearch(
            dubbed,
            type,
            subtitle,
            age,
            toyear,
            fromyear,
            tag,
            genre,
            country,
            page,
            page_size,
            text
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetFilterInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetFilterInformation) {
                    if (page == "1")
                        getFilterFilmInformationLiveData.value = t
                    else {
                        var list = ArrayList<Film>()
                        getFilterFilmInformationLiveData.value!!.results?.forEach { item ->
                            list.add(
                                item
                            )
                        }
                        t.results?.forEach { item -> list.add(item) }

                        t.results = list
                        getFilterFilmInformationLiveData.value = t
                    }
                }


            })

    }

    fun getAgeInformation() {
        mainRepository.getAge()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAgeLimitInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAgeLimitInformation) {
                    getAgeInformationLiveData.value = t
                }


            })
    }

    fun checkOpt(
        opt: String,
        phone: String,
        idCountry: String,
        macAddress: String,
        deviceName: String
    ) {
        mainRepository.checkOpt(
            CheckOptInformation(
                phone,
                opt,
                macAddress,
                "2",
                idCountry,
                deviceName
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetCheckOptInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetCheckOptInformation) {
                    checkOptLiveData.value = t
                }


            })
    }

    fun getAllCommentOfFilm(id: String) {
        mainRepository.getAllComment(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllCommentOfFilmInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllCommentOfFilmInformation) {
                    getAllCommentLiveData.value = t
                }


            })
    }

    fun getAllCategoryGenre() {
        mainRepository.getAllGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllGenreInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllGenreInformation) {
                    categoryGenreLiveData.value = t
                }


            })
    }

    fun getAllCategoryTag() {
        mainRepository.getAllTag()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllGenreInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllGenreInformation) {
                    categoryTagLiveData.value = t
                }


            })
    }

    fun getAllCountries() {
        mainRepository.getAllCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<List<Country>>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: List<Country>) {
                    countriesLiveData.value = t
                }


            })
    }

    fun getAllCountriesFilms() {
        mainRepository.getAllCountriesFilms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<List<Country>>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: List<Country>) {
                    countriesFilmsLiveData.value = t
                }


            })
    }

    fun getOpt(phone: String, countryCode: Int) {
        mainRepository.getOpt(phone, countryCode.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetOptInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetOptInformation) {
                    getOptLiveData.value = t
                }
            })
    }

    fun getLive() {
        mainRepository.getLives()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetLiveInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetLiveInformation) {
                    getLiveLiveData.value = t
                }
            })
    }

    fun getFilmDetail(id: String) {
        mainRepository.getFilmDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetFilmDetailInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetFilmDetailInformation) {
                    getFilmDetailInformation.value = t
                }
            })
    }

    fun getActorDetail(id: String) {
        mainRepository.getActorDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetActorDetailInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetActorDetailInformation) {
                    getActorDetailInformation.value = t
                }
            })
    }


    fun getBannerHome() {
        mainRepository.getBannerHome()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getBannerHomeLiveData.value = t
                }
            })
    }

    fun getNewFilm() {
        mainRepository.getNewFilm()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getNewFilmLiveData.value = t
                }
            })
    }

    fun getNewSeries() {
        mainRepository.getNewSeries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getNewSeriesLiveData.value = t
                }
            })
    }

    fun getNewlySeries() {
        mainRepository.getNewlySeries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getNewlySeriesLiveData.value = t
                }
            })
    }

    fun getPopularFilm() {
        mainRepository.getPopularFilm()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getPopularFilmLiveData.value = t
                }
            })
    }

    fun getPopularMovie() {
        mainRepository.getPopularMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getPopularMovieLiveData.value = t
                }
            })
    }

    fun getCommingSoon() {
        mainRepository.getCommingSoon()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllImageFilmHomeInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllImageFilmHomeInformation) {
                    getCommingSoonLiveData.value = t
                }
            })
    }


    fun registerUser(registerUserInformation: RegisterUserInformation) {
        mainRepository.registerUser(
            registerUserInformation
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetRegisterUserResultInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetRegisterUserResultInformation) {
                    registerUserInformationLiveData.value = t
                }
            })
    }

    fun getPopularActor() {
        mainRepository.getPopularActor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<List<ActorFilmDetailInformation>>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: List<ActorFilmDetailInformation>) {
                    getPopularActorLiveData.value = t
                }
            })
    }

    fun getPopularDirector() {
        mainRepository.getPopularDirector()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<List<ActorFilmDetailInformation>>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: List<ActorFilmDetailInformation>) {
                    getPopularDirectorLiveData.value = t
                }
            })
    }

    fun getAllActor() {
        mainRepository.getAllActor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllActorInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllActorInformation) {
                    getAllActorLiveData.value = t
                }
            })
    }

    fun getAllDirector() {
        mainRepository.getAllDirector()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllActorInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllActorInformation) {
                    getAllDirectorLiveData.value = t
                }
            })
    }

}