package com.example.chattingguys.Adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingguys.R
import com.example.chattingguys.modelclass.massagemodal
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.receverlayout.view.*
import kotlinx.android.synthetic.main.senderlayout.view.*

class MassgaeAdaptor(var context : Context, var list : ArrayList<massagemodal>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   var ITEM_SEND = 1
   var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

      return if (ITEM_SEND == viewType)
             sendviewholder(LayoutInflater.from(context).inflate(R.layout.senderlayout, parent,false))
        else
             receveviewholder(LayoutInflater.from(context).inflate(R.layout.receverlayout, parent,false))

    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid == list[position].senderid) ITEM_SEND else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var massage = list[position]
        if(holder.itemViewType == ITEM_SEND){
            val viewholder = holder as sendviewholder
            viewholder.stextview.text = massage.massage
        }else{
            val viewholder = holder as receveviewholder
            viewholder.rtextview2.text = massage.massage
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class sendviewholder(view : View) : RecyclerView.ViewHolder(view){
        var stextview : TextView = view.sender
    }

    inner class receveviewholder(view : View) : RecyclerView.ViewHolder(view){
        var rtextview2 : TextView = view.recever
    }

}