package com.example.assment.repository

import androidx.lifecycle.MutableLiveData
import com.example.assment.model.PostData
import com.example.assment.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    val postData = MutableLiveData<PostData?>()

    fun fetchPostData(): MutableLiveData<PostData?> {
        val call: Call<PostData> = RetrofitHelper.getPostApi().getPost()

        call.enqueue(object : Callback<PostData> {
            override fun onResponse(call: Call<PostData>, response: Response<PostData>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    postData.postValue(data)
                }
            }

            override fun onFailure(call: Call<PostData>, t: Throwable) {
                postData.value = null
            }
        })
        return postData
    }
}