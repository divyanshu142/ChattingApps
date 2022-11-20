package com.example.chattingguys.Activitys

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingguys.Adopter.MassgaeAdaptor
import com.example.chattingguys.R
import com.example.chattingguys.R.*
import com.example.chattingguys.R.id.*
import com.example.chattingguys.modelclass.massagemodal
import com.example.chattingguys.modelclass.modalclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_profile_activty.*
import kotlinx.android.synthetic.main.chat_user_layout.*
import kotlinx.android.synthetic.main.chat_user_layout.view.*
import java.util.*
import java.util.jar.Attributes
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    

    private lateinit var database : FirebaseDatabase

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<MassgaeAdaptor.receveviewholder>

    private lateinit var senderui : String
    private lateinit var receverui : String

    private lateinit var senderroom: String
    private lateinit var receverroom: String

    private lateinit var list : ArrayList<massagemodal>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_chat)

        supportActionBar!!.hide()

        var name : String? = intent.getStringExtra("name")
        var image : String? = intent.getStringExtra("image")



        username.setText(name)
        Picasso.get().load(image).placeholder(R.drawable.emptyimages).into(userimages)

        backbtn.setOnClickListener{
            startActivity(Intent(this@ChatActivity, MainActivity::class.java))
        }

        database = FirebaseDatabase.getInstance()
        list = ArrayList()

        recyclerView = chatAlist

        senderui = FirebaseAuth.getInstance().uid.toString()
        receverui = intent.getStringExtra("uid")!!

        senderroom = senderui + receverui
        receverroom = receverui + senderui

        sendbtn.setOnClickListener {
            if(sendtext.text.isEmpty()){
                Toast.makeText(this, "Please Enter Message", Toast.LENGTH_SHORT).show()
            }else{
                var massage = massagemodal(sendtext.text.toString(), senderui, Date().time)
                 var rendomkey = database.reference.push().key

                recyclerView.adapter?.let { it1 -> recyclerView.smoothScrollToPosition(it1.itemCount) }
                database.reference.child("chats")
                    .child(senderroom).child("massage").child(rendomkey!!).setValue(massage).addOnSuccessListener {

                        database.reference.child("chats")
                            .child(receverroom).child("massage").child(rendomkey!!).setValue(massage).addOnSuccessListener {

                                sendtext.text = null
                                Toast.makeText(this, "Message Send",Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }

        database.reference.child("chats")
            .child(senderroom).child("massage").addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for (snepshort1 in snapshot.children){
                        val data = snepshort1.getValue(massagemodal::class.java)
                        list.add(data!!)
                    }

                    recyclerView.scrollToPosition(list.size - 1)
                   recyclerView.adapter = MassgaeAdaptor(this@ChatActivity, list)
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(this@ChatActivity, "Error : $error", Toast.LENGTH_SHORT).show()

                }


            })

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

