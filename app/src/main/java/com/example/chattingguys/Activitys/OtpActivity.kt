package com.example.chattingguys.Activitys

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chattingguys.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var Verificationid : String
    private lateinit var diloge : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)



        setvideos.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dishuvideos));
        setvideos.start();

        setvideos.setOnPreparedListener { mp ->
            mp.start()

            mp.isLooping = true

        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        auth = FirebaseAuth.getInstance()
        val bilder = AlertDialog.Builder(this)
        bilder.setMessage("please Wait...")
        bilder.setTitle("Loading")
        bilder.setCancelable(false)
        diloge = bilder.create()
        diloge.show()

        val PhoneNumber = "+91"+ intent.getStringExtra("number")
        val Option = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(PhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    diloge.dismiss()
                    Toast.makeText(this@OtpActivity, "Process Failed ${p0}", Toast.LENGTH_SHORT).show()

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    diloge.dismiss()
                    Verificationid = p0

                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(Option)

        verificationbtn.setOnClickListener {

            if (phonefield.text!!.isEmpty()) {
            Toast.makeText(this, "Please Enter The Number", Toast.LENGTH_SHORT).show()
            }else{
                diloge.show()
                val credential = PhoneAuthProvider.getCredential(Verificationid, phonefield.text!!.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            diloge.dismiss()
                            startActivity(Intent(this, ProfileActivty::class.java))
                            finish()
                        }else{
                            diloge.dismiss()
                            Toast.makeText(this, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }



    }
}