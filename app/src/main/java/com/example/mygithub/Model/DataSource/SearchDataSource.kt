package com.example.mygithub.Model.DataSource

import android.arch.paging.PageKeyedDataSource
import com.example.mygithub.Model.DataSource.UsersDataSource.Companion.gitHubService
import com.example.mygithub.Model.PoJo.User
import java.io.IOException

class SearchDataSource(internal var query: String) : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, User>
    ) {
        try {
            val userResponse = gitHubService().getUsersBySearch(query, 1).execute()
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body()!!.getUsersList()!!, null, 2)
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
            val userResponse = gitHubService().getUsersBySearch(query, params.key).execute()
            if (userResponse.body() != null) {
                callback.onResult(userResponse.body()!!.getUsersList()!!, params.key + 1)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
