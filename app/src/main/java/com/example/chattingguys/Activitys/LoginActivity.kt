package com.example.chattingguys.Activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chattingguys.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setvideos.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dishuvideos));
        setvideos.start();

        setvideos.setOnPreparedListener { mp ->
            mp.start()

            mp.isLooping = true

        }

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
             startActivity( Intent(this, MainActivity::class.java))
            finish()
        }

        verificationbtn.setOnClickListener {
            if (phonefield.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter your Number!!", Toast.LENGTH_LONG).show()
            }else{
                val  intent = Intent(this, OtpActivity::class.java)
                     intent.putExtra("number", phonefield.text!!.toString())
                startActivity(intent)
            }

        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

    }
}