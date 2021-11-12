package com.example.justotestapp.ui.userlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.justotestapp.R
import com.example.justotestapp.data.model.DataUser

class UserAdapter(
    private val dataSet: List<DataUser>,
    private val cellClickListener: CellClickListener
): RecyclerView.Adapter<UserAdapter.UserViewModel>() {

    interface CellClickListener {
        fun onCellClickListener(data: DataUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_user_item, parent, false)
        return UserViewModel(view)
    }

     override fun onBindViewHolder(holder: UserViewModel, position: Int) {
         val data = dataSet[position]
         val name = data.results[0].name.first
         val lastname = data.results[0].name.last
         holder.photoUser.load(data.results[0].picture.thumbnail)
         holder.nameUser.text = name
         holder.lastNameUser.text = lastname

         holder.itemView.setOnClickListener {
             cellClickListener.onCellClickListener(data)
         }
     }


     class UserViewModel(view: View): RecyclerView.ViewHolder(view){
         val photoUser: ImageView = view.findViewById(R.id.photo_user)
         val nameUser: TextView = view.findViewById(R.id.name_user)
         val lastNameUser: TextView = view.findViewById(R.id.lastname_user)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}