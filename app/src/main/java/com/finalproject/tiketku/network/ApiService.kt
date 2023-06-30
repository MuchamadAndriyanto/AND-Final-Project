package com.finalproject.tiketku.network

import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.model.ResponseLogin
import com.finalproject.tiketku.model.ResponseResetPassword
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.model.caripenerbangan.ResponseCariPenerbangan
import com.finalproject.tiketku.model.detail.Detail
import com.finalproject.tiketku.model.detailrountrippergi.DetailRoundtripPergiResponse
import com.finalproject.tiketku.model.favorit.ResponseFavoriteDestination
import com.finalproject.tiketku.model.notif.NotifResponse
import com.finalproject.tiketku.model.onetrip.OneTripResponse
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.model.order.ResponseOrder
import com.finalproject.tiketku.model.payment.DataPost
import com.finalproject.tiketku.model.payment.ResponsePayment
import com.finalproject.tiketku.model.profile.UpdateProfilePost
import com.finalproject.tiketku.model.profile.ResponseProfile
import com.finalproject.tiketku.model.rincianCO.ResponseRincianOrder
import com.finalproject.tiketku.model.riwayat.ResponseRiwayat
import com.finalproject.tiketku.model.roundtrip.RoundTripResponse
import com.finalproject.tiketku.model.search.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getAllUsers(): Call<List<ResponseUsersItem>>

    @POST("login")
    fun postLogin(@Body request: DataLoginUserItem ): Call<ResponseLogin>

    @POST("register")
    fun registerUser(@Body request: DataPostUsersItem): Call<ResponseUsersItem>

    @POST("forgotPassword")
    fun postPassword(@Body request: DataPassword) : Call<DataPassword>

    @POST("reset-password")
    fun postResetPassword(@Body request: DataResetPassword) : Call<ResponseResetPassword>

    @GET("get-city")
    fun getSearch(
        @Query("query") kota : String
    ):Call<SearchResponse>

    @PUT("editusers")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfilePost
    ): Call<ResponseProfile>

    @GET("home")
    fun getFavorite(): Call<ResponseFavoriteDestination>

    @GET("notification")
    fun getNotif(): Call<NotifResponse>

    @GET("select-ticket/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): Call<Detail>

    @GET("home")
    fun getCariPenerbangan(): Call<ResponseCariPenerbangan>

    @POST("order")
    fun postOrderBiodata(
        @Header("Authorization") token: String,
        @Body request: DataOrder
    ): Call<ResponseOrder>

    @GET("get-order/{id}")
    fun getDetailOrder(
        @Header("Authorization") token: String,
        @Path("id") id: Int?
    ): Call<ResponseRincianOrder>

    @GET("get-pemesan")
    fun getKursi(
        @Header("Authorization") token: String
    ): Call<ResponseOrder>

    @GET("get-date-round-trip")
    fun getRoundtrip(
        @Query("tanggalBerangkat") tanggalBerangkat: String,
        @Query("tanggalPulang") tanggalPulang: String
    ): Call<RoundTripResponse>

    @GET("get-date-one-trip")
    fun getOnetrip(
        @Query("tanggalBerangkat") tanggalBerangkat: String
    ): Call<OneTripResponse>

    @GET("history-order")
    fun getRiwayat(
        @Header("Authorization") token: String
    ): Call<ResponseRiwayat>

    @GET("history-order/{id}")
    fun getRiwayatByOrderId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseRiwayat>

    @POST("payment")
    fun postPayment(
        @Header("Authorization") token: String,
        @Body request: DataPost
    ): Call<ResponsePayment>

    @GET("select-ticket/{id}")
    fun getDetailRountrip(
        @Path("id") id: Int
    ): Call<DetailRoundtripPergiResponse>
}