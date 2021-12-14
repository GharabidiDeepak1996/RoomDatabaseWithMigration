package com.example.roomdatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class UserDataAdapter(var mcontext: Context,
                      var mlist: List<UserData>): RecyclerView.Adapter<UserDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val v = LayoutInflater.from(mcontext).inflate(R.layout.list_item, parent, false)
        return UserDataViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        holder.mname.text= mlist[position].name
       holder.mgender.text= mlist[position].Gender
    }

    override fun getItemCount(): Int {
        return mlist.size
    }
}

class UserDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val mname=itemView.findViewById<TextView>(R.id.nameTextView)
    val mgender=itemView.findViewById<TextView>(R.id.genderTextView)
}