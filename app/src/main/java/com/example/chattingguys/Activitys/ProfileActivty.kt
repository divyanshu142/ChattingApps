package com.example.chattingguys.Activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chattingguys.R
import com.example.chattingguys.modelclass.modalclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_activty.*
import java.util.*

class ProfileActivty : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private lateinit var seletedimg : Uri
    private lateinit var diloge : AlertDialog.Builder
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_activty)

      diloge = AlertDialog.Builder(this)
          .setMessage("Updating Profile...")
          .setCancelable(false )

        progressBar = progress
        progressBar.max = 100
        progressBar.progress = 20

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        profilimg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }


        continubtn.setOnClickListener {
            progressBar.showContextMenu()
            if(profilname.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter Your Name", Toast.LENGTH_SHORT).show()
            }else if(profilimg == null){
                Toast.makeText(this,"Please Select Your Image", Toast.LENGTH_SHORT).show()
            }else{
                uploaddata();

            }

        }

    }

    private fun uploaddata() {
         val refrece = storage.reference.child("profile").child(Date().time.toString())
        refrece.putFile(seletedimg).addOnCompleteListener {
            if(it.isSuccessful){
                refrece.downloadUrl.addOnSuccessListener { task ->
                    uplodeInfo(task.toString())
                }
            }
        }
    }

    private fun uplodeInfo(imgurl: String) {
        val user = modalclass(auth.uid.toString(), profilname.text.toString(), auth.currentUser!!.phoneNumber.toString(), imgurl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Successfully Uplode", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data != null){
                seletedimg = data.data!!
                profilimg.setImageURI(seletedimg)

            }
        }
    }

}