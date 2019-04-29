package com.example.mygithub.View

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.makeText
import com.bumptech.glide.Glide
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_page_content.*
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Handler
import android.view.View
import com.example.mygithub.R
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.ViewModel.UsersViewModel


class MainActivity : AppCompatActivity(), UsersAdapter.InteractionCallbacks {

    internal var backPressedOnce = false

    companion object {
        val INTENT_EXTRA_USER_NAME = "INTENT_EXTRA_USER_NAME"
        val INTENT_EXTRA_SEARCH_QUERY = "INTENT_EXTRA_SEARCH_QUERY"
    }


    override fun onUserCardClick(user: User?) {
        Toast.makeText(this, "User: ${user!!.login}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_USER_NAME, user!!.login)
        startActivity(intent)
    }

    private var mAdapter: UsersAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)


        mAdapter = UsersAdapter(this, Glide.with(this))
        rvUsers.adapter = mAdapter

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Made By: Paras Varshney (in Kotlin)", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        svMainPage.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                makeText(applicationContext, "searched: $query", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, UsersListActivity::class.java)
                intent.putExtra(INTENT_EXTRA_SEARCH_QUERY, query)
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


        val usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel.pagedListLiveData.observe(this,
            Observer<PagedList<User>> { users ->
                mAdapter!!.submitList(users)
                progressViewMainPage.setVisibility(View.GONE)
            })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu.findItem(R.id.action_search)
        svMainPage.setMenuItem(searchItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_search -> {
                makeText(this, "", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
            if (svMainPage.isSearchOpen) {
            svMainPage.closeSearch()
        } else {

            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            this.backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ backPressedOnce = false }, 2000)
        }
    }
}
