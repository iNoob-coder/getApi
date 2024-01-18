package com.example.assment.retrofit

import com.example.assment.model.PostData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    fun getPost(): Call<PostData>
}