package com.example.mygithub.View

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.mygithub.R
import com.example.mygithub.Model.PoJo.User
import com.example.mygithub.View.UsersAdapter.ViewHolder

class UsersAdapter : PagedListAdapter<User, ViewHolder> {

    private val mInteractionCallbacks: InteractionCallbacks
    private val requestManagerGlide: RequestManager

    constructor(
        interactionCallbacks: MainActivity,
        requestManager: RequestManager
    ) : super(diffUtilItemCallback) {
        mInteractionCallbacks = interactionCallbacks
        requestManagerGlide = requestManager
    }

    constructor(
        interactionCallbacks: UsersListActivity,
        requestManager: RequestManager
    ) : super(diffUtilItemCallback) {
        mInteractionCallbacks = interactionCallbacks
        requestManagerGlide = requestManager
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.main_page_single_user_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.userName.text = getItem(position)!!.login
        requestManagerGlide.load(getItem(position)!!.avatar_url).into(viewHolder.avatar)
        viewHolder.mView.setOnClickListener {
            mInteractionCallbacks.onUserCardClick(getItem(viewHolder.adapterPosition))
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var avatar: ImageView
        var userName: TextView

        init {
            avatar = mView.findViewById(R.id.ivUserCard)
            userName = mView.findViewById(R.id.tvUserCard)
        }
    }

    interface InteractionCallbacks {
        fun onUserCardClick(user: User?)
    }

    companion object {

        private val diffUtilItemCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(user: User, t1: User): Boolean {
                return user.login.equals(t1.login)
            }

            override fun areContentsTheSame(user: User, t1: User): Boolean {
                return user.login.equals(t1.login)
            }
        }
    }
}