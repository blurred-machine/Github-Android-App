package com.example.mygithub.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.Model.Repositories.GithubRepository

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    val pagedListLiveData: LiveData<PagedList<User>>

    init {
        pagedListLiveData = GithubRepository(GithubRepository.MODE_HOME).pagedListLiveData!!
    }
}




