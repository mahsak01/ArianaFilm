package com.mgpersia.androidbox.data.implement

import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.information.*
import com.mgpersia.androidbox.data.repository.UserRepository
import com.mgpersia.androidbox.data.source.UserDataSource
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class UserRepositoryImplement(private val userRemoteDataSource: UserDataSource) : UserRepository {

    override fun getResolution(id: String): Single<List<Resolution>> =
        userRemoteDataSource.getResolution(id)

    override fun getAllNotifications(): Single<GetAllNotificationInformation> =
        userRemoteDataSource.getAllNotifications()

    override fun updateNotifications(
        id: String,
        updateNotificationFilmInformation: UpdateNotificationFilmInformation
    ): Single<Notification> =
        userRemoteDataSource.updateNotifications(id, updateNotificationFilmInformation)

    override fun addNotifications(addNotificationFilmInformation: AddNotificationFilmInformation): Single<GetResultAddNotificationInformation> =
        userRemoteDataSource.addNotifications(addNotificationFilmInformation)

    override fun addComment(addCommentInformation: AddCommentInformation): Single<AnswerTicket> =
        userRemoteDataSource.addComment(addCommentInformation)

    override fun getUserProfile(): Single<User> = userRemoteDataSource.getUserProfile()

    override fun getAllDevices(): Single<GetAllDevicesInformation> =
        userRemoteDataSource.getAllDevices()

    override fun uploadProfileAvatar(information: AvatarUserUpdateInformation): Completable =
        userRemoteDataSource.uploadProfileAvatar(information)

    override fun updateUserInformation(information: UpdateUserInformation): Single<User> =
        userRemoteDataSource.updateUsetInformation(information)

    override fun getAllTickets(): Single<GetAllTicketsInformation> =
        userRemoteDataSource.getAllTickets()

    override fun getAllDepartments(): Single<GetAllDepartmentsInformation> =
        userRemoteDataSource.getAllDepartments()

    override fun addTicket(information: AddTicketInformation, file: File?): Single<Ticket> =
        userRemoteDataSource.addTicket(information, file)

    override fun getAllFollowing(): Single<GetAllFollowingInformation> =
        userRemoteDataSource.getAllFollowing()


    override fun unFollowActor(followActorInformation: FollowActorInformation): Completable =
        userRemoteDataSource.unFollowActor(followActorInformation)

    override fun followActor(followActorInformation: FollowActorInformation): Single<GetResultFollowActorInformation> =
        userRemoteDataSource.followActor(followActorInformation)

    override fun deleteDevice(deleteDeviceInformation: DeleteDeviceInformation): Completable =
        userRemoteDataSource.deleteDevice(deleteDeviceInformation)

    override fun getWatchlistFavorite(): Single<GetWatchlistFavoriteInformation> =
        userRemoteDataSource.getWatchlistFavorite()

    override fun getWatchlistBookmark(): Single<GetWatchlistFavoriteInformation> =
        userRemoteDataSource.getWatchlistBookmark()

    override fun getAnswerTicket(id: String): Single<GetAnswerTicketInformation> =
        userRemoteDataSource.getAnswerTicket(id)

    override fun addCommentFilm(text: String, filmId: String): Single<Comment> =
        userRemoteDataSource.addCommentFilm(text, filmId)

    override fun addBookMarkWatchlist(addBookMarkInformation: AddBookMarkInformation): Single<FavoriteFilmList> =
        userRemoteDataSource.addBookMarkWatchlist(addBookMarkInformation)

    override fun addFavoriteWatchlist(addFavoriteInformation: AddFavoriteInformation): Single<FavoriteFilmList> =
        userRemoteDataSource.addFavoriteWatchlist(addFavoriteInformation)

    override fun deleteBookMarkWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable =
        userRemoteDataSource.deleteBookMarkWatchlist(removeWatchListInformation)

    override fun deleteFavoriteWatchlist(removeWatchListInformation: RemoveWatchListInformation): Completable =
        userRemoteDataSource.deleteFavoriteWatchlist(removeWatchListInformation)

}