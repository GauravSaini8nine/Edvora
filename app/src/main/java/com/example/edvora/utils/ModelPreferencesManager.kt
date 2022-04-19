package com.example.edvora.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.edvora.pojo.rideDetail.RideDetailResponse
import com.example.edvora.pojo.rideDetail.RideDetailResponseItem
import com.example.edvora.pojo.userDetail.UserDetailResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object ModelPreferencesManager {
    lateinit var preferences: SharedPreferences

    //Name of Shared Preference file
    private const val PREFERENCES_FILE_NAME = "CUSTOMER_PREFERENCES"


    fun with(application: Application) {
        preferences = application.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }


    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type “T” is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }


    fun putNearDetail(rideModel: ArrayList<RideDetailResponseItem>) {
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(rideModel)
        editor.putString(PREF_NEAREST_DATA, json)
        editor.apply()
    }

    fun getNearResponse(): ArrayList<RideDetailResponseItem>? {
        val gson = Gson()
        val json = preferences.getString(PREF_NEAREST_DATA, null)
        val type = object : TypeToken<ArrayList<RideDetailResponseItem>>() {}.type
        return gson.fromJson<ArrayList<RideDetailResponseItem>>(json, type)
    }

    fun putUpcomingDetail(rideModel: ArrayList<RideDetailResponseItem>) {
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(rideModel)
        editor.putString(PREF_UPCOMING_DATA, json)
        editor.apply()
    }

    fun getUpcomingDetail(): ArrayList<RideDetailResponseItem>? {
        val gson = Gson()
        val json = preferences.getString(PREF_UPCOMING_DATA, null)
        val type = object : TypeToken<ArrayList<RideDetailResponseItem>>() {}.type
        return gson.fromJson<ArrayList<RideDetailResponseItem>>(json, type)
    }

    fun putPastDetail(rideModel: ArrayList<RideDetailResponseItem>) {
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(rideModel)
        editor.putString(PREF_PAST_DATA, json)
        editor.apply()
    }

    fun getPastDetail():ArrayList<RideDetailResponseItem>? {
        val gson = Gson()
        val json = preferences.getString(PREF_PAST_DATA, null)
        val type = object : TypeToken<ArrayList<RideDetailResponseItem>>() {}.type
        return gson.fromJson<ArrayList<RideDetailResponseItem>>(json, type)
    }

}