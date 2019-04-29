package com.example.mygithub.Model.DataSource

import android.arch.paging.PageKeyedDataSource
import com.example.mygithub.Model.DataSource.UsersDataSource.Companion.gitHubService
import com.example.mygithub.Model.PoJo.User
import java.io.IOException

class FollowersDataSource(internal var userName: String) : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, User>
    ) {
        try {
            val userResponse = gitHubService().getFollowers(userName, 1).execute()
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body()!!, null, 2)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, User>
    ) {

    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, User>
    ) {
        try {
            val userResponse = gitHubService().getFollowers(userName, params.key).execute()
            callback.onResult(userResponse.body()!!, params.key + 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
