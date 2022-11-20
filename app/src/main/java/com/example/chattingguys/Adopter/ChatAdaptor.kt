package com.example.chattingguys.Adopter

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chattingguys.Activitys.ChatActivity
import com.example.chattingguys.R
import com.example.chattingguys.modelclass.modalclass
import kotlinx.android.synthetic.main.chat_user_layout.view.*




class ChatAdaptor ( var context : Context, var list : ArrayList<modalclass>) : RecyclerView.Adapter<ChatAdaptor.ChatViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder{
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_user_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = list[position]
        Glide.with(context)
            .load(user.imageUrl)
            .into(holder.imageview)
        holder.textview.text = user.name
        holder.Cardview.setOnClickListener {
            var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("uid", user.uid)
                intent.putExtra("name", user.name)
                intent.putExtra("image", user.imageUrl)
                context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var imageview : ImageView = view.imageciral
        var textview : TextView = view.textView5
        var Cardview : CardView = view.cardview
    }

}
