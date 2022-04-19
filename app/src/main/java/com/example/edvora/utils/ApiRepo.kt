package com.example.edvora.utils


import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.edvora.R
import com.example.edvora.listner.ApiListener

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepo {
    companion object {
        fun <T> callRetrofit(
            call: Call<T>,
            apiListener: ApiListener,
            url: String,
            context: Context,
        ) {
//            appCompatActivity.blockInput()
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>?, response: Response<T>?) {

//                    appCompatActivity.unBlockInput()
                    if (response!!.isSuccessful) {
                        response.body()?.let { apiListener.success(url, it!!) }
                        Log.d("ApiRepo","${response.body()}")

                    } else {
                        Log.d("ApiRepo","${response.errorBody()!!.charStream().readText()}")
                        apiListener.error(context.getString(R.string.server_error))
                    }
                }

                override fun onFailure(call: Call<T>?, t: Throwable?) {
                    t?.message?.let {
                        apiListener.failure(it)
                    }
//                    appCompatActivity.unBlockInput()
                    Log.d("ApiRepo","${t?.message}")
                    Log.d("Failure", "${t?.message}")
                }

            })
        }

    }


}