package com.example.justotestapp.ui.userlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.justotestapp.R
import com.example.justotestapp.data.model.User
import com.example.justotestapp.data.model.UserEntity
import com.example.justotestapp.data.model.UserList

class UserAdapter(
    private val itemClickListener: OnUserClickListener) :
    ListAdapter<UserEntity, UserAdapter.UserViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<UserEntity>(){
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }

    interface OnUserClickListener {
        fun onUserClick(data: UserEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         val data = getItem(position)
         val name = data.first
         holder.photoUser.load(data.thumbnail)
         holder.nameUser.text = name

         holder.itemView.setOnClickListener {
             itemClickListener.onUserClick(data)
         }
     }

    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
         val photoUser: ImageView = view.findViewById(R.id.photo_user)
         val nameUser: TextView = view.findViewById(R.id.name_user)
    }
}