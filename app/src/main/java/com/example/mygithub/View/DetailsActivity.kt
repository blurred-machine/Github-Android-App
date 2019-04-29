package com.example.mygithub.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.R
import com.example.mygithub.ViewModel.DetailedViewModel
import kotlinx.android.synthetic.main.user_details_content.*

class DetailsActivity : AppCompatActivity(), UsersAdapter.InteractionCallbacks {
    override fun onUserCardClick(user: User?) {

    }

    private var mUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        if (intent.hasExtra(MainActivity.INTENT_EXTRA_USER_NAME)) {
            val detailsViewModel = ViewModelProviders.of(this).get(DetailedViewModel::class.java!!)

            detailsViewModel.setUserName(
                intent.getStringExtra(MainActivity.INTENT_EXTRA_USER_NAME)
            )
            detailsViewModel.userMutableLiveData.observe(this,
                Observer<User> { user ->
                    if (user!!.login != null) {
                        mUser = user
                        displayData()
                        progressView.setVisibility(View.GONE)
                    }
                })
        } else {
            finish()
        }

    }


    private fun displayData() {
        Glide.with(this).load(mUser!!.avatar_url).into(imageViewUserDetails)
        textViewDisplayName.setText(mUser!!.name)
        textViewUserName.setText(mUser!!.login)
        textViewFollowerCount.setText(mUser!!.followers.toString())
        textViewFollowingCount.setText(mUser!!.following.toString())

        if (mUser!!.company != null) {
            textViewCompany.setText(mUser!!.company)
        } else {
            imageViewCompany.setVisibility(View.GONE)
            textViewCompany.setVisibility(View.GONE)
        }

        if (mUser!!.email != null) {
            textViewEmail.setText(mUser!!.email)
        } else {
            imageViewEmail.visibility = View.GONE
            textViewEmail.visibility = View.GONE
        }


        if (mUser!!.bio != null) {
            textViewBio.setText(mUser!!.bio)
        } else {
            imageViewBio.setVisibility(View.GONE)
            textViewBio.setVisibility(View.GONE)
        }

        if(mUser!!.location != null){
            textViewLocation.setText(mUser!!.location)
        }else{
            imageViewLocation.visibility = View.GONE
            textViewLocation.visibility = View.GONE
        }

    }

    fun openInGithub(view: View) {
        val url = Uri.parse(mUser!!.html_url)
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, url)
    }

    fun viewFollowers(view: View) {
        val intent = Intent(this, UsersListActivity::class.java)
        intent.putExtra(MainActivity.INTENT_EXTRA_USER_NAME, mUser!!.login)
        startActivity(intent)
    }

    fun viewFollowing(view: View) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
    }
}
