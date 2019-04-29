package com.example.mygithub.Model.Repositories

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.mygithub.Model.DataSource.GithubDSFactory
import com.example.mygithub.Model.PoJo.User

class GithubRepository {

    companion object {
        val MODE_HOME = "mode-home"
        val MODE_FOLLOWERS = "mode-followers"
        val MODE_SEARCH = "mode-search"
    }

    var pagedListLiveData: LiveData<PagedList<User>>? = null
    private var mode: String = ""
     var userName:String = ""
     var searchQuery:String = ""
    private var githubDataSourceFactory: GithubDSFactory? = null
    private var pagedListConfig: PagedList.Config? = null

    constructor(mode: String){
        this.mode = mode
        GithubRepository(mode)
    }

    fun GithubRepository(mode: String){
        pagedListConfig = PagedList.Config.Builder()
            .setPageSize(50)
            .setPrefetchDistance(20).build()

        if (mode == MODE_HOME) {
            githubDataSourceFactory = GithubDSFactory()
            pagedListLiveData = LivePagedListBuilder<Int, User>(githubDataSourceFactory!!, pagedListConfig!!).build()

        }
    }

    fun setMyUserName(userName: String) {
        this.userName = userName
        getFollowers()
    }

    fun setMySearchQuery(searchQuery: String) {
        this.searchQuery = searchQuery
        searchUsers()
    }

    private fun getFollowers() {
        githubDataSourceFactory = GithubDSFactory(userName)
        pagedListLiveData = LivePagedListBuilder<Int, User>(githubDataSourceFactory!!, pagedListConfig!!).build()
    }

    private fun searchUsers() {
        githubDataSourceFactory = GithubDSFactory(MODE_SEARCH, searchQuery)
        pagedListLiveData = LivePagedListBuilder<Int, User>(githubDataSourceFactory!!, pagedListConfig!!).build()
    }
}