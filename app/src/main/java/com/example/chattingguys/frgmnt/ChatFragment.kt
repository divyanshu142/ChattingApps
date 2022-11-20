package com.example.chattingguys.frgmnt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingguys.Adopter.ChatAdaptor
import com.example.chattingguys.R
import com.example.chattingguys.modelclass.modalclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*

class ChatFragment : Fragment() {
private lateinit var recyclerView: RecyclerView
private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
private lateinit var database : FirebaseDatabase
lateinit var userlist : ArrayList<modalclass>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        database = FirebaseDatabase.getInstance()
        userlist = ArrayList()

        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userlist.clear()


                    for (snapshot1 in snapshot.children){
                        val user = snapshot1.getValue(modalclass::class.java)
                        if (user!!.uid != FirebaseAuth.getInstance().uid){
                            userlist.add(user)
                        }
                    }
                    recyclerView = chatList
                    recyclerView.adapter = ChatAdaptor(requireContext(), userlist)

                }


                override fun onCancelled(error: DatabaseError) {

                }
            })
        return inflater.inflate(R.layout.fragment_chat, container, false)

    }

}