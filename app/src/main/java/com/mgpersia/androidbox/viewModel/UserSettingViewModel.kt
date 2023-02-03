package com.mgpersia.androidbox.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgpersia.androidbox.common.ArianaFilmCompletableObserver
import com.mgpersia.androidbox.common.ArianaFilmSingleObserver
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import java.io.File

class UserSettingViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val allDevicesInformationLiveData = MutableLiveData<GetAllDevicesInformation>()

    val uploadAvatarLiveData = MutableLiveData<Boolean>()

    val getUserLiveData = MutableLiveData<User>()

    val allTicketsInformationLiveData = MutableLiveData<GetAllTicketsInformation>()

    val allDepartmentsInformationLiveData = MutableLiveData<GetAllDepartmentsInformation>()

    val addTicketLiveData = MutableLiveData<Ticket>()

    val getAllFollowingLiveData = MutableLiveData<GetAllFollowingInformation>()

    val deleteDeviceInformationLiveData = MutableLiveData<String>()

    val getWatchlistFavoriteLiveData = MutableLiveData<GetWatchlistFavoriteInformation>()

    val getWatchListBookmarkLiveData = MutableLiveData<GetWatchlistFavoriteInformation>()

    val getAnswerTicketLiveData = MutableLiveData<GetAnswerTicketInformation>()

    val addCommentFilmLiveData = MutableLiveData<Comment>()

    val addBookMarkWatchlistLiveData = MutableLiveData<FavoriteFilmList>()

    val addFavoriteWatchlistLiveData = MutableLiveData<FavoriteFilmList>()

    val deleteBookMarkWatchlistLiveData = MutableLiveData<Boolean>()

    val deleteFavoriteWatchlistLiveData = MutableLiveData<Boolean>()

    val followActorLiveData = MutableLiveData<GetResultFollowActorInformation>()

    val unFollowActorLiveData = MutableLiveData<Boolean>()

    val errorHandlingLiveData = MutableLiveData<Throwable>()

    val allNotificationsLiveData = MutableLiveData<GetAllNotificationInformation>()

    val addNotificationsLiveData = MutableLiveData<GetResultAddNotificationInformation>()

    val updateNotificationsLiveData = MutableLiveData<Notification>()

    val addCommentInformationLiveData = MutableLiveData<AnswerTicket>()

    val resolutionInformationLiveData = MutableLiveData<List<Resolution>>()

    fun getAllResolution(idFilm: String) {
        userRepository.getResolution(idFilm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<List<Resolution>>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: List<Resolution>) {
                    resolutionInformationLiveData.value = t
                }
            })
    }

    fun addCommentTicket(addCommentInformation: AddCommentInformation) {
        userRepository.addComment(addCommentInformation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<AnswerTicket>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: AnswerTicket) {
                    addCommentInformationLiveData.value = t
                }
            })
    }

    fun getAllNotification() {
        userRepository.getAllNotifications()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllNotificationInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllNotificationInformation) {
                    allNotificationsLiveData.value = t
                }


            })
    }

    fun addNotifications(filmId: String) {
        userRepository.addNotifications(AddNotificationFilmInformation(filmId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetResultAddNotificationInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetResultAddNotificationInformation) {
                    addNotificationsLiveData.value = t
                }


            })
    }

    fun updateNotifications(id: String, status: String) {
        userRepository.updateNotifications(id, UpdateNotificationFilmInformation(status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<Notification>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: Notification) {
                    updateNotificationsLiveData.value = t
                }
            })
    }

    fun unFollowActorLiveData(actorId: String) {
        userRepository.unFollowActor(FollowActorInformation(actorId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {
                    unFollowActorLiveData.value = true
                }

            })
    }

    fun followActorLiveData(actorId: String) {
        userRepository.followActor(FollowActorInformation(actorId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetResultFollowActorInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetResultFollowActorInformation) {
                    followActorLiveData.value = t
                }
            })
    }

    fun deleteBookMarkWatchlist(filmId: String) {
        userRepository.deleteBookMarkWatchlist(RemoveWatchListInformation(filmId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {
                    deleteBookMarkWatchlistLiveData.value = true
                }

            })
    }

    fun deleteFavoriteWatchlist(filmId: String) {
        userRepository.deleteBookMarkWatchlist(RemoveWatchListInformation(filmId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {
                    deleteFavoriteWatchlistLiveData.value = true
                }
            })
    }


    fun addBookMarkWatchlist(filmId: String) {
        userRepository.addBookMarkWatchlist(AddBookMarkInformation("new", true, filmId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<FavoriteFilmList>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: FavoriteFilmList) {
                    addBookMarkWatchlistLiveData.value = t
                }
            })
    }

    fun addFavoriteWatchlist(filmId: String) {
        userRepository.addFavoriteWatchlist(AddFavoriteInformation("new", true, filmId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<FavoriteFilmList>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: FavoriteFilmList) {
                    addFavoriteWatchlistLiveData.value = t
                }
            })
    }

    fun addCommentFilm(text: String, filmId: String) {
        userRepository.addCommentFilm(text, filmId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<Comment>(compositeDisposable, errorHandlingLiveData) {
                override fun onSuccess(t: Comment) {
                    addCommentFilmLiveData.value = t
                }
            })
    }


    fun getAnswerTicket(id: String) {
        userRepository.getAnswerTicket(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAnswerTicketInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAnswerTicketInformation) {
                    getAnswerTicketLiveData.value = t
                }
            })
    }

    fun getWatchlistFavorite() {
        userRepository.getWatchlistFavorite()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetWatchlistFavoriteInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetWatchlistFavoriteInformation) {
                    getWatchlistFavoriteLiveData.value = t
                }
            })
    }

    fun getWatchListBookmark() {
        userRepository.getWatchlistBookmark()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetWatchlistFavoriteInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetWatchlistFavoriteInformation) {
                    getWatchListBookmarkLiveData.value = t
                }
            })
    }

    fun getUserProfile() {
        userRepository.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<User>(compositeDisposable, errorHandlingLiveData) {
                override fun onSuccess(t: User) {
                    getUserLiveData.value = t
                    UserContainer.update(t)
                }


            })
    }

    fun getAllDevices() {
        userRepository.getAllDevices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllDevicesInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllDevicesInformation) {
                    allDevicesInformationLiveData.value = t
                }


            })
    }

    fun uploadAvatarProfile(file: File) {
        userRepository.uploadProfileAvatar(AvatarUserUpdateInformation(file))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {

                    uploadAvatarLiveData.value = true
                }
            })
    }

    fun updateUserInformation(information: UpdateUserInformation) {
        userRepository.updateUserInformation(information)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<User>(compositeDisposable, errorHandlingLiveData) {
                override fun onSuccess(t: User) {
                    getUserLiveData.value = t
                    UserContainer.update(t)
                }


            })
    }

    fun getAllTicket() {
        userRepository.getAllTickets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllTicketsInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllTicketsInformation) {
                    allTicketsInformationLiveData.value = t
                }


            })
    }

    fun getAllDepartments() {
        userRepository.getAllDepartments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllDepartmentsInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllDepartmentsInformation) {
                    allDepartmentsInformationLiveData.value = t
                }


            })
    }

    fun addTicket(information: AddTicketInformation, file: File?) {
        userRepository.addTicket(information, file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<Ticket>(compositeDisposable, errorHandlingLiveData) {
                override fun onSuccess(t: Ticket) {
                    addTicketLiveData.value = t
                }


            })
    }

    fun getAllFollowing() {
        userRepository.getAllFollowing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmSingleObserver<GetAllFollowingInformation>(
                    compositeDisposable,
                    errorHandlingLiveData
                ) {
                override fun onSuccess(t: GetAllFollowingInformation) {
                    getAllFollowingLiveData.value = t
                }


            })
    }

    fun deleteDevice(deleteDeviceInformation: DeleteDeviceInformation) {
        userRepository.deleteDevice(deleteDeviceInformation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                ArianaFilmCompletableObserver(compositeDisposable, errorHandlingLiveData) {
                override fun onComplete() {
                    deleteDeviceInformationLiveData.value = "success"

                }


            })
    }


}