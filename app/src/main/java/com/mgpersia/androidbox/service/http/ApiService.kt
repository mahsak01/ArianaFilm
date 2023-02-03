package com.mgpersia.androidbox.service.http

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.information.*
import io.reactivex.Completable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("https://api.ipgeolocation.io/ipgeo?apiKey=c30e5f0bbec94a74bc083bbf0b8425db")
    fun getLocation(): Single<CheckLocationInformation>

    @GET("/film/{id}/files")
    fun getResolution(@Path("id") id: String): Single<List<Resolution>>

    @POST("ticket/comment/")
    fun addComment(@Body addCommentInformation: AddCommentInformation): Single<AnswerTicket>

    @GET("notifications/list/")
    fun getAllNotifications(): Single<GetAllNotificationInformation>

    @PUT("notifications/update/{id}/")
    fun updateNotifications(
        @Path("id") id: String,
        @Body updateNotificationFilmInformation: UpdateNotificationFilmInformation
    ): Single<Notification>

    @GET("notifications/access/")
    fun addNotifications(@Body addNotificationFilmInformation: AddNotificationFilmInformation): Single<GetResultAddNotificationInformation>


    @GET("user/profile/")
    fun getUserProfile(): Single<User>

    @GET("user/device_list/")
    fun getAllDevices(): Single<GetAllDevicesInformation>

    @HTTP(method = "DELETE", path = "user/remove_device_none_jwt/", hasBody = true)
    fun deleteDevice(@Body deleteDeviceInformation: DeleteDeviceInformation): Completable

    @Multipart
    @PUT("user/edit/avatar/")
    fun uploadProfileAvatar(@Part file: MultipartBody.Part): Completable

    @PUT("user/profile/")
    fun updateUsetInformation(@Body information: UpdateUserInformation): Single<User>

    @GET("ticket/list/")
    fun getAllTickets(): Single<GetAllTicketsInformation>

    @GET("ticket/departments/")
    fun getAllDepartments(): Single<GetAllDepartmentsInformation>

    @Multipart
    @POST("ticket/create/")
    fun addTicket(
        @Body information: AddTicketInformation,
        @Part file: MultipartBody.Part?
    ): Single<Ticket>

    @POST("ticket/create/")
    fun addTicket(@Body information: AddTicketInformation): Single<Ticket>

    @GET("actor/follow/")
    fun getAllFollowing(): Single<GetAllFollowingInformation>

    @POST("actor/follow/")
    fun followActor(@Body followActorInformation: FollowActorInformation): Single<GetResultFollowActorInformation>

    @HTTP(method = "DELETE", path = "actor/unfollow/", hasBody = true)
    fun unFollowActor(@Body followActorInformation: FollowActorInformation): Completable

    @GET("user/countries/")
    fun getAllCountries(): Single<List<Country>>

    @GET("film/countries/")
    fun getAllCountriesFilms(): Single<List<Country>>

    @GET("user/auth/otp/")
    fun getOpt(
        @Query("phone") phone: String,
        @Query("country") country: String
    ): Single<GetOptInformation>

    @POST("user/auth/otp/")
    fun checkOpt(@Body information: CheckOptInformation): Single<GetCheckOptInformation>

    @POST("user/auth/register/")
    fun registerUser(@Body registerUserInformation: RegisterUserInformation): Single<GetRegisterUserResultInformation>

    @GET("film/get_all_genre/")
    fun getAllGenre(): Single<GetAllGenreInformation>

    @GET("film/get_all_tag/")
    fun getAllTag(): Single<GetAllGenreInformation>

    @GET("live/")
    fun getLives(): Single<GetLiveInformation>

    @GET("film/film_detail/{id}")
    fun getFilmDetail(@Path("id") id: String): Single<GetFilmDetailInformation>

    @GET("actor/get_actor/{id}/")
    fun getActorDetail(@Path("id") id: String): Single<GetActorDetailInformation>

    @GET("film/sliders/")
    fun getBannerHome(): Single<GetAllImageFilmHomeInformation>

    @GET("film/news/")
    fun getNewFilm(): Single<GetAllImageFilmHomeInformation>

    @GET("film/news/series/")
    fun getNewSeries(): Single<GetAllImageFilmHomeInformation>

    @GET("film/series/newly/")
    fun getNewlySeries(): Single<GetAllImageFilmHomeInformation>

    @GET("film/all/?comming_soon")
    fun getCommingSoon(): Single<GetAllImageFilmHomeInformation>

    @GET("actor/popular/")
    fun getPopularActor(): Single<List<ActorFilmDetailInformation>>

    @GET("actor/popular/director/")
    fun getPopularDirector(): Single<List<ActorFilmDetailInformation>>


    @GET("actor/")
    fun getAllActor(): Single<GetAllActorInformation>

    @GET("actor/directors/")
    fun getAllDirector(): Single<GetAllActorInformation>

    @GET("film/popular/")
    fun getPopularFilm(): Single<GetAllImageFilmHomeInformation>


    @GET("film/popular/series/")
    fun getPopularMovie(): Single<GetAllImageFilmHomeInformation>


    @GET("film/get_comment/{id}")
    fun getAllComment(@Path("id") id: String): Single<GetAllCommentOfFilmInformation>

    @GET("film/get_watchlist_favorite/")
    fun getWatchlistFavorite(): Single<GetWatchlistFavoriteInformation>

    @GET("film/get_watchlist_bookmark/")
    fun getWatchlistBookmark(): Single<GetWatchlistFavoriteInformation>

    @GET("ticket/{id}/comment/")
    fun getAnswerTicket(@Path("id") id: String): Single<GetAnswerTicketInformation>

    @Multipart
    @POST("film/add_comment/")
    fun addCommentFilm(@PartMap partMap: MutableMap<String, RequestBody>): Single<Comment>

    @POST("film/add_watchlist_bookmark/")
    fun addBookMarkWatchlist(@Body addBookMarkInformation: AddBookMarkInformation): Single<FavoriteFilmList>

    @POST("film/add_watchlist_favorite/")
    fun addFavoriteWatchlist(@Body addFavoriteInformation: AddFavoriteInformation): Single<FavoriteFilmList>

    @HTTP(method = "DELETE", path = "film/remove_watchlist_bookmark_item/", hasBody = true)
    fun deleteBookMarkWatchlist(@Body removeWatchListInformation: RemoveWatchListInformation): Completable

    @HTTP(method = "DELETE", path = "film/remove_watchlist_favorite_item/", hasBody = true)
    fun deleteFavoriteWatchlist(@Body removeWatchListInformation: RemoveWatchListInformation): Completable

    @GET("film/age_limits/")
    fun getAge(): Single<GetAgeLimitInformation>

    @GET("film/all/")
    fun getSearch(
        @Query("dubbed") dubbed: String,
        @Query("type") type: String,
        @Query("subtitle") subtitle: String,
        @Query("age") age: String,
        @Query("toyear") toyear: String,
        @Query("fromyear") fromyear: String,
        @Query("tag") tag: String,
        @Query("genre") genre: String,
        @Query("country") country: String,
        @Query("page") page: String,
        @Query("page_size") page_size: String,
        @Query("text") text: String,
    ): Single<GetFilterInformation>

}

fun createApiServiceInstance(): ApiService {

    //TODO
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            if (TokenContainer.token != null)
                newRequestBuilder.addHeader(
                    "Authorization",
                    "Bearer ${TokenContainer.token}"
                )
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://95.216.26.66:3080/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    return retrofit.create(ApiService::class.java)
}