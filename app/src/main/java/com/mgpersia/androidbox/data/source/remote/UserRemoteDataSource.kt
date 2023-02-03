package com.mgpersia.androidbox.data.source.remote

import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.source.UserDataSource
import com.mgpersia.androidbox.service.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserRemoteDataSource(private var apiService: ApiService) : UserDataSource {

    override fun getAllNotifications(): Single<GetAllNotificationInformation> =
        apiService.getAllNotifications()

    override fun updateNotifications(
        id: String,
        updateNotificationFilmInformation: UpdateNotificationFilmInformation
    ): Single<Notification> =
        apiService.updateNotifications(id, updateNotificationFilmInformation)

    override fun addNotifications(addNotificationFilmInformation: AddNotificationFilmInformation): Single<GetResultAddNotificationInformation> =
        apiService.addNotifications(addNotificationFilmInformation)

    override fun getUserProfile(): Single<User> = apiService.getUserProfile()

    override fun getAllDevices(): Single<GetAllDevicesInformation> = apiService.getAllDevices()

    override fun uploadProfileAvatar(information: AvatarUserUpdateInformation): Completable =
        apiService.uploadProfileAvatar(
            MultipartBody.Part.createFormData(
                "avatar", information.avatar.name, RequestBody.create(
                    "multipart/form-data\\".toMediaTypeOrNull(), information.avatar
                )
            )
        )

    override fun updateUsetInformation(information: UpdateUserInformation): Single<User> =
        apiService.updateUsetInformation(information)

    override fun getAllTickets(): Single<GetAllTicketsInformation> =
        apiService.getAllTickets()

    override fun getAllDepartments(): Single<GetAllDepartmentsInformation> =
        apiService.getAllDepartments()

    override fun addTicket(
        information: AddTicketInformation,
        file: File?
    ): Single<Ticket> {
        if (file != null)
            return apiService.addTicket(
                information, MultipartBody.Part.createFormData(
                    "attachment", file!!.name, RequestBody.create(
                        "multipart/form-data\\".toMediaTypeOrNull(), file
                    )
                )
            )
        else
            return apiService.addTicket(information)
    }

    override fun getAllFollowing(): Single<GetAllFollowingInformation> =
        apiService.getAllFollowing()

    override fun followActor(followActorInformation: FollowActorInformation): Single<GetResultFollowActorInformation> =
        apiService.followActor(followActorInformation)

    override fun unFollowActor(followActorInformation: FollowActorInformation): Completable =
        apiService.unFollowActor(followActorInformation)

    override fun deleteDevice(deleteDeviceInformation: DeleteDeviceInformation): Completable =
        apiService.deleteDevice(deleteDeviceInformation)

    override fun getWatchlistFavorite(): Single<GetWatchlistFavoriteInformation> =
        apiService.getWatchlistFavorite()

    override fun getWatchlistBookmark(): Single<GetWatchlistFavoriteInformation> =
        apiService.getWatchlistBookmark()

    override fun getAnswerTicket(id: String): Single<GetAnswerTicketInformation> =
        apiService.getAnswerTicket(id)

    override fun addCommentFilm(text: String, filmId: String): Single<Comment> {
        val map: MutableMap<String, RequestBody> = mutableMapOf()
        map["text"] = RequestBody.create("text/plain".toMediaTypeOrNull(), text)
        map["film_id"] = RequestBody.create("text/plain".toMediaTypeOrNull(), filmId)

        return apiService.addCommentFilm(map)
    }

    override fun addBookMarkWatchlist(addBookMarkInformation: AddBookMarkInformation): Single<FavoriteFilmList> =
        apiService.addBookMarkWatchlist(addBookMarkInformation)

    override fun addFavoriteWatchlist(addFavoriteInformation: AddFavoriteInformation): Single<FavoriteFilmList> =
        apiService.addFavoriteWatchlist(addFavoriteInformation)

    override fun deleteBookMarkWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable =
        apiService.deleteBookMarkWatchlist(removeWatchListInformation)

    override fun deleteFavoriteWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable =
        apiService.deleteFavoriteWatchlist(removeWatchListInformation)

    override fun addComment(addCommentInformation: AddCommentInformation): Single<AnswerTicket> =
        apiService.addComment(addCommentInformation)

    override fun getResolution(id: String): Single<List<Resolution>> = apiService.getResolution(id)


}