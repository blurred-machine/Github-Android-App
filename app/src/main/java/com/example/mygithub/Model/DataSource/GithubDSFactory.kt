package com.example.mygithub.Model.DataSource

import android.arch.paging.DataSource
import com.example.mygithub.Model.PoJo.User

class GithubDSFactory : DataSource.Factory<Int, User> {
    internal var githubDataSource: DataSource<Int, User>

    constructor(userName: String) {
        githubDataSource = FollowersDataSource(userName)
    }

    constructor() {
        githubDataSource = UsersDataSource()
    }

    constructor(mode: String, query: String) {
        githubDataSource = SearchDataSource(query)
    }

    override fun create(): DataSource<Int, User> {
        return githubDataSource
    }
}
