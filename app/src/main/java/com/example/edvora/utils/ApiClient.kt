package com.example.edvora.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient{
    companion object {
        private var retrofit: Retrofit? = null

        const val BASE_URL: String = "https://assessment.api.vweb.app/"
        fun getClient(): Retrofit? {
            if(retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}