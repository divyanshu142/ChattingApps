package com.example.chattingguys.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chattingguys.Adopter.ViewPagerAdopter
import com.example.chattingguys.R
import com.example.chattingguys.frgmnt.CallFragment
import com.example.chattingguys.frgmnt.ChatFragment
import com.example.chattingguys.frgmnt.StatusFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentarraylist = ArrayList<Fragment>()
        fragmentarraylist.add(ChatFragment())
        fragmentarraylist.add(StatusFragment())
        fragmentarraylist.add(CallFragment())

        val adaptor = ViewPagerAdopter(this, supportFragmentManager, fragmentarraylist)
        viewpager.adapter = adaptor
        tabs.setupWithViewPager(viewpager)

        statuss.setOnClickListener{
            startActivity(Intent(this, ChatActivity::class.java))
        }


        var actionBar = getSupportActionBar()
        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }


    // this event will enable the back
    // function to the button on press
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}