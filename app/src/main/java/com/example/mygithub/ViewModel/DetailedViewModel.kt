package com.example.mygithub.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.example.mygithub.Model.DataSource.UsersDataSource
import com.example.mygithub.Model.PoJo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedViewModel(private val mApplication: Application) : AndroidViewModel(mApplication) {
    val userMutableLiveData: MutableLiveData<User>

    val userLiveData: LiveData<User>
        get() = userMutableLiveData

    init {
        userMutableLiveData = MutableLiveData<User>()
    }

    fun setUserName(userName: String) {
        fetchUser(userName)
    }

    private fun fetchUser(userName: String) {
        UsersDataSource.gitHubService()
            .getUser(userName)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful()) {
                        userMutableLiveData.postValue(response.body())
                    } else {
                        Toast.makeText(mApplication, "Bad response from server", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(mApplication, "Internal error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
