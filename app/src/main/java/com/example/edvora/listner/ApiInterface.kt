package com.example.edvora.listner

import com.example.edvora.pojo.rideDetail.RideDetailResponse
import com.example.edvora.pojo.userDetail.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    // UserDetails
    @GET("user")
    fun userDetail(): Call<UserDetailResponse>
    //Ride Details
    @GET("rides")
    fun ridesDetail(): Call<RideDetailResponse>
}