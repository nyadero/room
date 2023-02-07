package com.bronyst.room.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bronyst.room.R
import com.bronyst.room.data.User

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var usersList = emptyList<User>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val firstName = itemView.findViewById<TextView>(R.id.txt_firstname)
        val lastName = itemView.findViewById<TextView>(R.id.txt_lastname)
        val age = itemView.findViewById<TextView>(R.id.txt_age)
        val id = itemView.findViewById<TextView>(R.id.txt_id)
        val userLayout = itemView.findViewById<ConstraintLayout>(R.id.user_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]
        holder.firstName.text = user.firstName
        holder.lastName.text = user.lastName
        holder.age.text = user.age
        holder.id.text = user.id.toString()

        holder.userLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment2(user)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun setData(user: List<User>){
        this.usersList = user
        notifyDataSetChanged()
    }
}