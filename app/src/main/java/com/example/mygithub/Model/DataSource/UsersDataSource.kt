package com.example.mygithub.Model.DataSource

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.mygithub.Model.Repositories.GithubAPI
import com.example.mygithub.Model.PoJo.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class UsersDataSource: ItemKeyedDataSource<Int, User>() {

    val PAGE_SIZE = 50
    companion object {
        val BASE_URL = "https://api.github.com/"
        fun gitHubService(): GithubAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubAPI::class.java)
        }
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<User>) {
        try {
            val userResponse = gitHubService().getAllUsers(1, PAGE_SIZE).execute()
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body()!!)
            } else {
                Log.e("sfdsdfsd", userResponse.errorBody()!!.string())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        try {
            val userResponse = gitHubService().getAllUsers(params.key, PAGE_SIZE).execute()
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body()!!)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<User>) {
    }

    override fun getKey(item: User): Int {
        return item.id
    }
}