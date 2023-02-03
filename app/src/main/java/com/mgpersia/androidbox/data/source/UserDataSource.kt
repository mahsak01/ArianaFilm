package com.mgpersia.androidbox.data.source

import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.information.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Path
import java.io.File

interface UserDataSource {


    fun getAllNotifications(): Single<GetAllNotificationInformation>

    fun updateNotifications(
        id: String,
        updateNotificationFilmInformation: UpdateNotificationFilmInformation
    ): Single<Notification>

    fun addNotifications(addNotificationFilmInformation: AddNotificationFilmInformation): Single<GetResultAddNotificationInformation>

    fun getUserProfile(): Single<User>

    fun getAllDevices(): Single<GetAllDevicesInformation>

    fun uploadProfileAvatar(information: AvatarUserUpdateInformation): Completable

    fun updateUsetInformation(information: UpdateUserInformation): Single<User>

    fun getAllTickets(): Single<GetAllTicketsInformation>

    fun getAllDepartments(): Single<GetAllDepartmentsInformation>

    fun addTicket(information: AddTicketInformation, file: File?): Single<Ticket>

    fun getAllFollowing(): Single<GetAllFollowingInformation>

    fun followActor(followActorInformation: FollowActorInformation): Single<GetResultFollowActorInformation>

    fun unFollowActor(followActorInformation: FollowActorInformation): Completable


    fun deleteDevice(deleteDeviceInformation: DeleteDeviceInformation): Completable

    fun getWatchlistFavorite(): Single<GetWatchlistFavoriteInformation>

    fun getWatchlistBookmark(): Single<GetWatchlistFavoriteInformation>

    fun getAnswerTicket(id: String): Single<GetAnswerTicketInformation>

    fun addCommentFilm(text: String, filmId: String): Single<Comment>

    fun addBookMarkWatchlist(addBookMarkInformation: AddBookMarkInformation): Single<FavoriteFilmList>

    fun addFavoriteWatchlist(addFavoriteInformation: AddFavoriteInformation): Single<FavoriteFilmList>

    fun deleteBookMarkWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable

    fun deleteFavoriteWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable

    fun addComment(addCommentInformation: AddCommentInformation): Single<AnswerTicket>

    fun getResolution(id: String): Single<List<Resolution>>

}