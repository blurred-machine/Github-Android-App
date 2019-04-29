package com.example.mygithub.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.R
import com.example.mygithub.ViewModel.UsersListViewModel
import kotlinx.android.synthetic.main.activity_users_list.*
import kotlinx.android.synthetic.main.layout_users_grid.*

class UsersListActivity : AppCompatActivity(), UsersAdapter.InteractionCallbacks {

    override fun onUserCardClick(user: User?) {
        Toast.makeText(this, "User: ${user!!.login}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MainActivity.INTENT_EXTRA_USER_NAME, user!!.login)
        startActivity(intent)    }

    private var mAdapter: UsersAdapter? = null
    private var mProgressView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        setSupportActionBar(toolbarUserList)
        mProgressView = findViewById(R.id.progressView)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = UsersAdapter(this, Glide.with(this))
        usersListRecyclerView.setAdapter(mAdapter)

        val viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java!!)
        if (intent.hasExtra(MainActivity.INTENT_EXTRA_USER_NAME)) {
            viewModel.setUserName(intent.getStringExtra(MainActivity.INTENT_EXTRA_USER_NAME))
        } else if (intent.hasExtra(MainActivity.INTENT_EXTRA_SEARCH_QUERY)) {
            val query = intent.getStringExtra(MainActivity.INTENT_EXTRA_SEARCH_QUERY)
            supportActionBar!!.setTitle(query)
            viewModel.setSearchQuery(query)
        }
        viewModel
            .liveData!!
            .observe(this, object : Observer<PagedList<User>> {
            override fun onChanged(users: PagedList<User>?) {
                mAdapter!!.submitList(users)
                mProgressView!!.visibility = View.GONE
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
