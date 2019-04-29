
package com.example.mygithub.Model.Repositories


import com.example.mygithub.Model.PoJo.SearchedList
import com.example.mygithub.Model.PoJo.User
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @retrofit2.http.GET("users")
    fun getAllUsers(
        @Query("since") since:Int,
        @Query("per_page") perPage:Int
    ):Call<List<User>>

    annotation class GET(val value: String)


    @retrofit2.http.GET("users/{user_name}")
    abstract fun getUser(
        @Path("user_name") userName: String
    ): Call<User>


    @retrofit2.http.GET("users/{user_name}/followers")
    abstract fun getFollowers(
        @Path("user_name") userName: String,
        @Query("page") pageNumber: Int
    ): Call<List<User>>


    @retrofit2.http.GET("search/users")
    abstract fun getUsersBySearch(
        @Query("q") query: String,
        @Query("page") pageNumber: Int
    ): Call<SearchedList>
}

annotation class Path(val value: String)

annotation class Query(val value: String)

