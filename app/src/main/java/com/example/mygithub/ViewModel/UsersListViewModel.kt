package com.example.mygithub.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.Model.Repositories.GithubRepository

class UsersListViewModel(application: Application) : AndroidViewModel(application) {


    private var mUserName: String? = null
    private var mSearchQuery: String? = null
    var liveData: LiveData<PagedList<User>>? = null


    fun setUserName(mUserName: String) {
        this.mUserName = mUserName
        val githubRepository = GithubRepository(GithubRepository.MODE_FOLLOWERS)
        githubRepository.setMyUserName(mUserName)
        liveData = githubRepository.pagedListLiveData
    }

    fun setSearchQuery(searchQuery: String) {
        mSearchQuery = searchQuery
        val githubRepository = GithubRepository(GithubRepository.MODE_SEARCH)
        githubRepository.setMySearchQuery(searchQuery)

        liveData = githubRepository.pagedListLiveData
    }


}
